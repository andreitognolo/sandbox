package br.com.brainweb.extranet.util.tomcat;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import br.com.brainweb.extranet.util.phantomjs.ExecUtil;
import de.schlichtherle.truezip.file.TFile;

public class TomcatInstaller {

	private String tomcatFullName;

	private File dest;

	private String executablePath;

	public TomcatInstaller(String pathDest, String tomcatFullName) {
		this.tomcatFullName = tomcatFullName;
		this.dest = new File(pathDest);
	}

	public TomcatInstaller configure() {
		if (!dest.exists() && !dest.mkdirs()) {
			throw new RuntimeException("unable to create directory: " + dest);
		}

		File tomcatDirectory = getFile();
		if (!tomcatDirectory.exists()) {
			tomcatDirectory.mkdir();
			download();
			unpack();
			adjustPermissions();
			tomcatDirectory = getFile();
		}
		if (!tomcatDirectory.exists()) {
			throw new RuntimeException("tomcat was not installed");
		}
		executablePath = tomcatDirectory.getAbsolutePath();
		System.out.println("Tomcat Directory: " + tomcatDirectory);

		return this;
	}

	private void adjustPermissions() {
		new File(dest, tomcatFullName + "/bin/startup.sh").setExecutable(true);
		new File(dest, tomcatFullName + "/bin/catalina.sh").setExecutable(true);
		new File(dest, tomcatFullName + "/bin/shutdown.sh").setExecutable(true);
		new File(dest, tomcatFullName + "/bin/bootstrap.jar").setExecutable(true);
		new File(dest, tomcatFullName + "/bin/bootstrap.jar").setReadable(true);
	}

	public String getExecutable() {
		if (executablePath == null) {
			throw new RuntimeException(
					"Executable path is not defined. Did you call execute before?");
		}
		return executablePath;
	}

	private void download() {
		String url = "http://ftp.unicamp.br/pub/apache/tomcat/tomcat-7/v7.0.55/bin/"
				+ tomcatFullName + ".zip";
		File packFile = new File(dest, tomcatFullName + ".zip");
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

	@SuppressWarnings("deprecation")
	private void unpack() {
		TFile archive = new TFile(new TFile(dest, tomcatFullName + ".zip"));
		System.out.println("Unpacking: " + archive);
		try {
			archive.cp_r(dest);
			TFile.umount();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private File getFile() {
		return new File(dest, tomcatFullName);
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
