package com.andreitognolo.phantomjs;

import java.io.File;

public class PhantomJS {

	private PhantomJSInstaller installer;

	public String getExecutable() {
		return installer.getExecutable();
	}

	public void configure(PhantomJSInstaller installer) {
		if (this.installer != null) {
			throw new RuntimeException("already configurated: " + installer);
		}
		installer.configure();
		this.installer = installer;
	}

	public String source(String file, Long timeout) {
		return exec(PhantomJSExec.create().source(file).timeout(timeout));
	}

	public String source(String file) {
		return source(file, null);
	}

	public PhantomJSInstaller getInstaller() {
		return installer;
	}

	public String eval(String script, Long timeout) {
		return exec(PhantomJSExec.create().eval(script).timeout(timeout));
	}

	public String eval(String script) {
		return eval(script, null);
	}

	@Override
	public String toString() {
		return "[PhantomJS " + installer + "]";
	}

	public String exec(PhantomJSExec exec) {
		File scriptFile = null;
		try {
			if (exec.getSource() == null) {
				scriptFile = PhantomJSUtil.writeTempFile(exec.getEval(), "utf-8");
				scriptFile.deleteOnExit();
				exec.source(scriptFile.getPath());
			}
			String[] cmds = new String[2 + exec.getArgs().length];
			cmds[0] = installer.getExecutable();
			cmds[1] = exec.getSource();
			System.arraycopy(exec.getArgs(), 0, cmds, 2, exec.getArgs().length);
			StringBuilder out = new StringBuilder();
			StringBuilder err = new StringBuilder();
			ExecUtil.execAndWaitSuccess(cmds, out, err, exec.getTimeout());
			String strerr = PhantomJSUtil.str(err);
			if (strerr != null) {
				System.out.println("Phantom strerr: " + strerr);
			}
			return out.toString();
		} finally {
			if (scriptFile != null) {
				try {
					scriptFile.delete();
				} catch (Exception e) {
					System.out.println("error deleting script: " + scriptFile);
				}
			}
		}
	}

}
