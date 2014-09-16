package br.com.brainweb.extranet.util.tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import br.com.brainweb.extranet.util.phantomjs.ExecUtil;

public class Tomcat {

	private static final String TOMCAT_FULL_NAME = "apache-tomcat-7.0.55";
	private static final String DIRETORIO = "target/tomcat";

	public void configure() {
		new TomcatInstaller(DIRETORIO, TOMCAT_FULL_NAME).configure();
	}
	
	public void deploy() throws IOException {
		log("Deploying war");
		
		File webapps = new File(tomcatHome() + "/webapps");
		File destinoDeploy = new File(webapps, "ifood-extranet.war");
		
		if (destinoDeploy.exists()) {
			destinoDeploy.delete();
		}
		
		Path from = new File("target/extranet.war").toPath();
		Path to = destinoDeploy.toPath();
		Files.copy(from, to);
		
		log("Copy " + from + " to " + to);
	}
	
	public void start() {
		StringBuilder out = new StringBuilder();
		StringBuilder err = new StringBuilder();
		String[] cmds = new String[1];
		cmds[0] = tomcatHome() + "/bin/startup.sh";
		
		ExecUtil.execAndWaitSuccess(cmds, out, err, 70000l);
	}

	private void stop() {
		log("Stoping tomcat... ");
		
		StringBuilder out = new StringBuilder();
		StringBuilder err = new StringBuilder();
		String[] cmds = new String[1];
		cmds[0] = tomcatHome() + "/bin/shutdown.sh";
		
		ExecUtil.execAndWait(cmds, out, err, 70000l);
	}

	public String tomcatHome() {
		return DIRETORIO + "/" + TOMCAT_FULL_NAME;
	}
	
	private void waitForPage(String url, int sleepInicialEmSegundos, int timeoutEmSegundos) {
		log("Waiting initial time: " + sleepInicialEmSegundos + " seconds");
		sleep(sleepInicialEmSegundos * 1000);

		long inicio = System.currentTimeMillis();
		
		while (System.currentTimeMillis() - inicio < (timeoutEmSegundos * 1000)) {
			HttpClient httpclient = new DefaultHttpClient();
			try {
				log("Trying: " + url);
				HttpResponse response = httpclient.execute(new HttpGet(url));
				int respCode = response.getStatusLine().getStatusCode();
				log("Response code: " + respCode);
				if (respCode == 200) {
					return;
				}
			} catch (ClientProtocolException e) {
				log("ClientProtocolException: " + e.getMessage());
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
			
			sleep(1000);
		}
		
		throw new RuntimeException("Timeout: " + timeoutEmSegundos + " segundos");
	}
	
	public void log(String message) {
		System.out.println(message);
	}

	private void sleep(long timeoutInicial) {
		try {
			Thread.sleep(timeoutInicial);
		} catch (InterruptedException e1) {
			throw new RuntimeException(e1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		final Tomcat tomcat = new Tomcat();
		tomcat.configure();
		tomcat.deploy();
		// tomcat.stop();
		tomcat.start();
		tomcat.waitForPage("http://localhost:8080/ifood-extranet", 5, 60);
		System.out.println("Started: Up and running!");
	}
}
