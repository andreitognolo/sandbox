package br.com.portaldedocumentos.plataform.mycontainer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.LogManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.mycontainer.commons.io.IOUtil;
import com.googlecode.mycontainer.datasource.DataSourceDeployer;
import com.googlecode.mycontainer.jta.MyTransactionManagerDeployer;
import com.googlecode.mycontainer.kernel.ShutdownCommand;
import com.googlecode.mycontainer.kernel.boot.ContainerBuilder;
import com.googlecode.mycontainer.mail.MailDeployer;
import com.googlecode.mycontainer.mail.MailInfoBuilder;
import com.googlecode.mycontainer.web.ContextWebServer;
import com.googlecode.mycontainer.web.FilterDesc;
import com.googlecode.mycontainer.web.jetty.JettyServerDeployer;

public class MycontainerHelper {

	private static final Logger LOG = LoggerFactory.getLogger(MycontainerHelper.class);

	private ContainerBuilder builder;

	private JettyServerDeployer webServer;

	private String webPath = "../pdoc-web/src/main/webapp/";

	private String logging = "logging.properties";

	public void setLogging(String logging) {
		this.logging = logging;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public void bootMycontainer() {
		try {
			System.setProperty("java.naming.factory.initial", "com.googlecode.mycontainer.kernel.naming.MyContainerContextFactory");
			
			if (System.getProperty("hibernate") == null) {
				System.setProperty("raidenjpa", "true");
			}
			
			readLogConfig();

			builder = new ContainerBuilder(new InitialContext());
			builder.deployVMShutdownHook();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	private void readLogConfig() {
		URL url = getClass().getResource(logging);
		if (url == null) {
			throw new RuntimeException("logging config not found: " + logging);
		}
		InputStream in = null;
		try {
			in = url.openStream();
			LogManager.getLogManager().readConfiguration(in);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.close(in);
		}
	}

	public void bootEmail() {
		MailDeployer mail = builder.createDeployer(MailDeployer.class);
		mail.setName("java:/mail/PortalSession");
		MailInfoBuilder mailInfo = mail.getInfo();
		mailInfo.setAuthenticator(new MyContainerMailAutenticator("build-continua@dextra-sw.com", "2275N5"));
		Properties properties = mailInfo.getProperties();
		properties.put("mail.debug", "false");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtps.port", "587");
		properties.put("mail.smtp.from", "build-continua@dextra-sw.com");
		properties.put("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		mail.deploy();
	}

	public void bootWeb() {
		webServer = builder.createDeployer(JettyServerDeployer.class);
		webServer.setName("WebServer");
		ContextWebServer webContext = webServer.createContextWebServer();
		webContext.setContext("/");
		webContext.setResources(webPath);
		webContext.getFilters().add(new FilterDesc(LogFilter.class, "/s/*"));
		webContext.getFilters().add(new FilterDesc(LogFilter.class, "/w/*"));
		webContext.getFilters().add(new FilterDesc(LogFilter.class, "/_ah/*"));
		webContext.getFilters().add(new FilterDesc(LogFilter.class, "/remote_api"));
		webContext.getFilters().add(new FilterDesc(LogFilter.class, "/remote_api/*"));
		webContext.getFilters().add(new FilterDesc(new Filter() {

			public void init(FilterConfig filterConfig) throws ServletException {
			}

			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				webHook((HttpServletRequest) request, (HttpServletResponse) response, chain);
			}

			public void destroy() {

			}
		}, "/*"));
		webServer.deploy();
	}

	public int bind(int port) {
		try {
			SelectChannelConnector connector = new SelectChannelConnector();
			connector.setPort(port);
			connector.setMaxIdleTime(30000);
			Server jetty = webServer.getServer();
			jetty.addConnector(connector);
			connector.start();
			return connector.getLocalPort();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void waitFor() {
		builder.waitFor();
	}

	public void shutdown() {
		try {
			ShutdownCommand shutdown = new ShutdownCommand();
			shutdown.setContext(new InitialContext());
			shutdown.shutdown();
		} catch (Exception e) {
			LOG.error("error", e);
		}
	}

	public synchronized void unbindPort(int port) {
		try {
			Server jetty = webServer.getServer();
			Connector[] connectors = jetty.getConnectors();
			for (Connector connector : connectors) {
				if (connector.getLocalPort() == port) {
					connector.stop();
					jetty.removeConnector(connector);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void webHook(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	public void bootDB() {
		builder.createDeployer(MyTransactionManagerDeployer.class).setName("TransactionManager").deploy();

		DataSourceDeployer ds = builder.createDeployer(DataSourceDeployer.class);
		ds.setName("jdbc/GPPDS");
		ds.setDriver("org.hsqldb.jdbcDriver");
		ds.setUrl("jdbc:hsqldb:mem:.");
		ds.setUser("sa");
		ds.deploy();

	}

	public JettyServerDeployer getWebServer() {
		return webServer;
	}

	public ContainerBuilder getBuilder() {
		return builder;
	}
}
