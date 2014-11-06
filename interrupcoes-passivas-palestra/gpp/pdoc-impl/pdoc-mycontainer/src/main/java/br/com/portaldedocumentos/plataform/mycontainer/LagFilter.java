package br.com.portaldedocumentos.plataform.mycontainer;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LagFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);

	public void init(FilterConfig cfg) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		filter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	private void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		lag();
		chain.doFilter(request, response);
	}

	private void lag() {
		try {
			Thread.sleep(1000l);
		} catch (InterruptedException e) {
			LOG.error("error", e);
		}
	}

	public void destroy() {

	}

}
