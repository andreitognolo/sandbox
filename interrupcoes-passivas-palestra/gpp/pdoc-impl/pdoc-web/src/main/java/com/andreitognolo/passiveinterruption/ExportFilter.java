package com.andreitognolo.passiveinterruption;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExportFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String exp = ServletUtil.param(req, "exp");
		if (exp == null) {
			chain.doFilter(req, resp);
			return;
		}

		if (!"GET".equals(req.getMethod())) {
			throw new RuntimeException("just get can be use exp: " + req.getMethod());
		}

		export(exp, req, resp);
	}

	private void export(String exp, HttpServletRequest req, HttpServletResponse resp) {
		String hostPort = "localhost:" + req.getLocalPort();
		String uri = req.getRequestURI();
		String fullURL = "http://" + hostPort + uri + params(req);
		PhantomHelper.Content ret = PhantomHelper.me().eval(exp, fullURL, "");
		ServletUtil.writeBytes(resp, ret.getContentType(), ret.getCharset(), ret.getContent());
	}

	@SuppressWarnings("unchecked")
	private String params(HttpServletRequest req) {
		Params params = new Params();
		Map<String, String[]> map = req.getParameterMap();
		for (Entry<String, String[]> entry : map.entrySet()) {
			String k = entry.getKey();
			if (!"exp".equals(k)) {
				String[] values = entry.getValue();
				for (String value : values) {
					params.add(k, value);
				}
			}
		}
		String ret = Util.str(params.toURLParams());
		return ret == null ? "" : "?" + ret;
	}

	public void destroy() {

	}

}