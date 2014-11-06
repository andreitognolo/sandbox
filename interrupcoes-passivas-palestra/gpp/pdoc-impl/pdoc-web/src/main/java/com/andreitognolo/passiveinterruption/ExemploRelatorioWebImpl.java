package com.andreitognolo.passiveinterruption;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExemploRelatorioWebImpl extends HttpServlet {
	
	private static final long serialVersionUID = -8928118766792051813L;

	public void teste(HttpServletRequest req, HttpServletResponse resp){
		URL url = ExemploRelatorioWebImpl.class.getResource("ExemploRelatorio.html");
		ServletUtil.writeHtml(resp, Util.read(url, "UTF-8"));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		teste(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		teste(req, resp);
	}
	
	
}
