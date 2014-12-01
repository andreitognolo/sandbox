package com.andreitognolo.phantomjs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

public class ExecUtil {

	public static String getOutputAsString(String... args) {
		Process p = null;
		InputStream in = null;
		try {
			p = Runtime.getRuntime().exec(args);
			int code = p.waitFor();
			if (code != 0) {
				throw new RuntimeException("exec error: " + code);
			}
			in = p.getInputStream();
			String ret = PhantomJSUtil.readAll(new InputStreamReader(in, Charset.defaultCharset()));
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			PhantomJSUtil.close(in);
			PhantomJSUtil.close(p);
		}
	}

	public static void execAndWaitSuccess(String cmd, Long timeout) {
		String[] cmds = new String[1];
		cmds[0] = cmd;
		
		StringBuilder out = new StringBuilder();
		StringBuilder err = new StringBuilder();
		execAndWaitSuccess(cmds, out, err, timeout);
	}
	
	public static void execAndWaitSuccess(String[] cmds, StringBuilder out, StringBuilder err, Long timeout) {
		int code = execAndWait(cmds, out, err, timeout);
		if (code != 0) {
			throw new RuntimeException("exec error: " + code);
		}
	}

	public static int execAndWait(String[] cmds, StringBuilder out, StringBuilder err, Long timeout) {
		Process p = null;
		Reader stdout = null;
		Reader stderr = null;
		try {
			System.out.println("Executing timeout: " + timeout + ", command : " + Arrays.toString(cmds));
			
			ProcessBuilder builder = new ProcessBuilder(cmds);
			builder.redirectErrorStream(true);
			builder.redirectOutput(Redirect.INHERIT);
			builder.redirectError(Redirect.INHERIT);
			p = builder.start();
			
			Integer code = null;
			long before = System.currentTimeMillis();
			long max = before + timeout;
			while (code == null) {
				StringWriter writer = new StringWriter();
				IOUtils.copy(p.getInputStream(), writer, "utf-8");
				
				code = code(p);
				long now = System.currentTimeMillis();
				if (now > max) {
					throw new RuntimeException("timeout, max: " + timeout + ", but was: " + (now - before));
				}
				if (code == null) {
					PhantomJSUtil.sleep(10l);
				}
			}
			stdout = new InputStreamReader(p.getInputStream(), "utf-8");
			stderr = new InputStreamReader(p.getInputStream(), "utf-8");
			PhantomJSUtil.copyAll(stdout, out);
			PhantomJSUtil.copyAll(stderr, err);
			return code.intValue();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			PhantomJSUtil.close(stdout);
			PhantomJSUtil.close(stderr);
			PhantomJSUtil.close(p);
		}
	}

	private static Integer code(Process p) {
		try {
			return p.exitValue();
		} catch (IllegalThreadStateException e) {
			return null;
		}
	}
}
