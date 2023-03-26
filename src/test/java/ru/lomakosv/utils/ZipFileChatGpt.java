package ru.lomakosv.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileChatGpt {

    public void unzipFiles(String pathToUnpackedFiles, ClassLoader pathToZipFile) {
        try {
            File destFolder = new File(pathToUnpackedFiles);
            if (!destFolder.exists()) {
                destFolder.mkdir();
            }

            ZipInputStream zipIn = new ZipInputStream(Objects.requireNonNull(pathToZipFile.getResourceAsStream("file.zip")));
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                String filePath = pathToUnpackedFiles + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
            System.out.println("Successfully unzipped to " + pathToUnpackedFiles);
        } catch (IOException e) {
            System.out.println("Error while unzipping" + e.getMessage());
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }


    public void delete(String UNPACKED_FILES) {
        File folder = new File(UNPACKED_FILES);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

    private static void deleteDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
