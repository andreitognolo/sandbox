package br.com.brainweb.extranet.util.phantomjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class TarBz2Util {

	public static void unpack(String inputPath, String outputPath) {
		String withoutBz2 = inputPath.substring(0, inputPath.length() - 4);
		unpackBz2(inputPath, withoutBz2);
		unTar(withoutBz2, outputPath);
	}
	
	public static void unpackBz2(String inputPath, String outputPath) {
		FileInputStream in;
		try {
			in = new FileInputStream(inputPath);
			FileOutputStream out = new FileOutputStream(outputPath);
			BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
			final byte[] buffer = new byte[1024];
			int n = 0;
			while (-1 != (n = bzIn.read(buffer))) {
			  out.write(buffer, 0, n);
			}
			out.close();
			bzIn.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static List<File> unTar(String inputPath, String outputDirPath) {
		File inputFile = new File(inputPath);
		File outputDir = new File(outputDirPath);
		
		try {
			 final List<File> untaredFiles = new LinkedList<File>();
			    final InputStream is = new FileInputStream(inputFile); 
			    final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
			    TarArchiveEntry entry = null; 
			    while ((entry = (TarArchiveEntry)debInputStream.getNextEntry()) != null) {
			        final File outputFile = new File(outputDir, entry.getName());
			        if (entry.isDirectory()) {
			            if (!outputFile.exists()) {
			                if (!outputFile.mkdirs()) {
			                    throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
			                }
			            }
			        } else {
			            final OutputStream outputFileStream = new FileOutputStream(outputFile); 
			            IOUtils.copy(debInputStream, outputFileStream);
			            outputFileStream.close();
			        }
			        untaredFiles.add(outputFile);
			    }
			    debInputStream.close(); 
			    return untaredFiles;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
