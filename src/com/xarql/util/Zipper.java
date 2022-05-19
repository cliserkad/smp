// Copied from https://www.baeldung.com/java-compress-and-uncompress
// No infringement intended
package com.xarql.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {

	public static final int BUFFER_MULTIPLIER = 2;
	public static final String ZIP_EXTENSION = ".zip";

	public static File zip(final File input) throws IOException {
		return zip(input, new File(input.getParentFile(), input.getName() + ZIP_EXTENSION));
	}

	public static File zip(final File input, final File output) throws IOException {
		try(var fos = new FileOutputStream(output); var zipOut = new ZipOutputStream(fos)) {
			zipFile(input, input.getName(), zipOut);
		}
		return output;
	}

	private static void zipFile(final File fileToZip, final String fileName, final ZipOutputStream zipOut) throws IOException {
		if(fileToZip.isDirectory()) {
			if(fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
			}
			zipOut.closeEntry();
			final var children = fileToZip.listFiles();
			if(children != null) {
				for(final File childFile : children) {
					zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
				}
			}
			return;
		}
		final var fis = new FileInputStream(fileToZip);
		final var zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		final var bytes = new byte[Sizes.KILO];
		int length;
		while((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}

	public static void unZip(final File file, final File destDir) throws IOException {
		destDir.mkdirs();
		final var buffer = new byte[BUFFER_MULTIPLIER * Sizes.KILO]; // multiply by 1kb
		final var zis = new ZipInputStream(new FileInputStream(file));
		var zipEntry = zis.getNextEntry();
		while(zipEntry != null) {
			var newFile = newFile(destDir, zipEntry);
			if(zipEntry.isDirectory()) {
				newFile = new File(destDir, zipEntry + "/");
				newFile.mkdirs();
			} else {
				final var fos = new FileOutputStream(newFile);
				int len;
				while((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
			}
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
	}

	public static File newFile(final File destinationDir, final ZipEntry zipEntry) throws IOException {
		final var destFile = new File(destinationDir, zipEntry.getName());

		final var destDirPath = destinationDir.getCanonicalPath();
		final var destFilePath = destFile.getCanonicalPath();

		if(!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

}
