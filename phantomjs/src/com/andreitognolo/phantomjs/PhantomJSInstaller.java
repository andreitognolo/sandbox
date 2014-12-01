package com.andreitognolo.phantomjs;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;

import de.schlichtherle.truezip.file.TFile;

public class PhantomJSInstaller {

	public static class Spec {
		private String plataform;
		private String arch;
		private String pack;
		private String path;
		private String executable;

		public Spec(String plataform, String arch, String pack, String path,
				String executable) {
			this.plataform = plataform;
			this.arch = arch;
			this.pack = pack;
			this.path = path;
			this.executable = executable;
		}

		public String getName() {
			return arch == null ? plataform : "" + plataform + "-" + arch;
		}

	}

	private String version;

	private String baseUrl = "https://phantomjs.googlecode.com/files/";

	private File dest;

	private Map<String, Spec> specs = new HashMap<String, Spec>();

	private String executablePath;

	public PhantomJSInstaller(String version, String pathDest) {
		this.version = version;
		this.dest = new File(pathDest);
		
		addSpec("linux", "i686", "tar.bz2", "bin/phantomjs", "phantomjs");
		addSpec("linux", "x86_64", "tar.bz2", "bin/phantomjs", "phantomjs");
		addSpec("macosx", null, "zip", "bin/phantomjs", "phantomjs");
		addSpec("windows", null, "zip", "phantomjs.exe", "phantomjs.exe");
	}

	private void addSpec(String plataform, String arch, String pack,
			String path, String executable) {
		Spec spec = new Spec(plataform, arch, pack, path, executable);
		String name = spec.getName();
		specs.put(name, spec);
	}

	public PhantomJSInstaller configure() {
		if (!dest.exists() && !dest.mkdirs()) {
			throw new RuntimeException("unable to create directory: "
					+ dest);
		}

		File file = getFile();
		if (!file.exists()) {
			Spec spec = getSpec();
			download(spec);
			unpack(spec);
			executable(spec);
			file = getFile();
		}
		if (!file.exists()) {
			throw new RuntimeException("phantomjs was not installed");
		}
		executablePath = file.getAbsolutePath();
		System.out.println("Phantomjs: " + file);
		
		return this;
	}
	
	public String getExecutable() {
		if (executablePath == null) {
			throw new RuntimeException("Executable path is not defined. Did you call execute before?");
		}
		return executablePath;
	}

	private void executable(Spec spec) {
		File executable = new File(dest, spec.executable);
		executable.setExecutable(true);
	}

	private void unpack(Spec spec) {
        File packFile = new File(dest, "phantomjs." + spec.pack);
        TFile archive = new TFile(packFile, "phantomjs-" + version + "-"
                        + spec.getName() + "/" + spec.path);
        System.out.println("Unpacking: " + archive);
        try {
			archive.cp(new File(dest, spec.executable));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void download(Spec spec) {
		String url = "" + baseUrl + "phantomjs-" + version + "-"
				+ spec.getName() + "." + spec.pack;
		File packFile = new File(dest, "phantomjs." + spec.pack);
		ReadableByteChannel in = null;
		FileChannel out = null;
		try {
			out = new FileOutputStream(packFile).getChannel();
			System.out.println("Downloading: " + url);
			in = Channels.newChannel(new URL(url).openStream());
			out.transferFrom(in, 0, Long.MAX_VALUE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(in);
			close(out);
		}
	}

	private Spec getSpec() {
		String platform = System.getProperty("os.name").toLowerCase();
		String arch = System.getProperty("os.arch").toLowerCase();
		String name = null;
		if (platform.contains("win")) {
			name = "windows";
		} else if (platform.contains("mac")) {
			name = "macosx";
		} else if (platform.contains("nux")) {
			name = "linux-" + (arch.contains("64") ? "x86_64" : "i686");
		} else {
			throw new IllegalArgumentException("unknown platform: " + platform
					+ " " + arch);
		}
		Spec ret = specs.get(name);
		if (ret == null) {
			throw new IllegalArgumentException("unknown spec: " + name);
		}
		return ret;
	}

	private File getFile() {
		File ret = new File(dest, "phantomjs");
		if (!ret.exists()) {
			ret = new File(dest, "phantomjs.exe");
		}
		return ret;
	}

	private void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}