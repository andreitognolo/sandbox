package com.andreitognolo.passiveinterruption;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import phantomjs4java.BasicPhantomJS;
import phantomjs4java.PhantomJS;
import phantomjs4java.PhantomJSExec;

public class PhantomHelper {

	private static final Logger LOG = LoggerFactory.getLogger(PhantomHelper.class);

	public static class Content {
		private byte[] content;
		private String contentType;
		private String charset;

		public String getContentType() {
			return contentType;
		}

		public byte[] getContent() {
			return content;
		}

		public String getCharset() {
			return charset;
		}

		@Override
		public String toString() {
			return "[Content " + contentType + " " + (content == null ? "null" : Integer.toString(content.length)) + " " + charset + "]";
		}

	}

	private static PhantomHelper me = new PhantomHelper();

	private PhantomJS phantom;

	public static PhantomHelper me() {
		return me;
	}

	public void boot() {
		LOG.info("Installing phantomjs");
		phantom = BasicPhantomJS.instance();
		LOG.info("killing all phantomjs");
		phantom.getInstaller().killAll();
		LOG.info("Phantomjs installed: " + phantom);
	}

	public void shutdown() {
		if (phantom != null) {
			LOG.info("killing all phantomjs");
			phantom.getInstaller().killAll();
			LOG.info("Uninstalling phantomjs: " + phantom);
			phantom.getInstaller().uninstall();
		}
	}

	public Content eval(String exp, String url, String token) {
		File file = null;
		try {
			String contentType = null;
			String charset = null;
			if ("png".equals(exp)) {
				contentType = "image/png";
			} else if ("pdf".equals(exp)) {
				contentType = "application/pdf";
			} else {
				throw new RuntimeException("exp not supported: " + exp);
			}
			if (token == null) {
				token = "";
			}
			URL resource = getClass().getResource("phantomjs-export.js");
			String script = Util.read(resource, "utf-8");

			file = File.createTempFile("portal-phantom-shot", "." + exp);
			file.deleteOnExit();

			PhantomJSExec exec = PhantomJSExec.create().eval(script).args(new String[] { url, token, exp, file.getPath() }).timeout(5000l);
			String str = Util.str(phantom.exec(exec));
			if (str != null) {
				throw new RuntimeException("phantom error: " + str);
			}
			Content ret = new Content();
			ret.content = Util.readBytes(file.toURI().toURL());
			ret.contentType = contentType;
			ret.charset = charset;
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (file != null) {
				try {
					file.delete();
				} catch (Exception e) {
					LOG.error("error deleting");
				}
			}
		}
	}
}
