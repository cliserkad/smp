// Copied from https://www.baeldung.com/java-compress-and-uncompress
// No infringement intended
package com.xarql.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper
{
    public static final int BUFFER_MULTIPLIER = 2;
    public static final String ZIP_EXTENSION = ".zip";

    public static void zip(File input) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(input + ZIP_EXTENSION);
            ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            zipFile(input, input.getName(), zipOut);
        } catch(IOException e) {
            throw e;
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if(fileToZip.isDirectory()) {
            if(fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zipOut.closeEntry();
            final File[] children = fileToZip.listFiles();
            if(children != null) {
                for(File childFile : children) {
                    zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
                }
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[Sizes.KILO];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public static void unZip(File file, File destDir) throws IOException {
        destDir.mkdirs();
        byte[] buffer = new byte[BUFFER_MULTIPLIER * Sizes.KILO]; // multiply by 1kb
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if(zipEntry.isDirectory()) {
                newFile = new File(destDir, zipEntry + "/");
                newFile.mkdirs();
            } else {
                FileOutputStream fos = new FileOutputStream(newFile);
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

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if(!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}
