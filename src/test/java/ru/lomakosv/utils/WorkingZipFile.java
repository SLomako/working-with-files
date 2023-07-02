package ru.lomakosv.utils;


import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;


public class WorkingZipFile {

    public void extractThemAll(String pathToUnpackedFiles, ClassLoader pathToZipFile) throws IOException {

        URL zipFIle = pathToZipFile.getResources("file.zip").nextElement();
        String extractionPath = zipFIle.getPath();
        ZipFile filesZip = new ZipFile(extractionPath);

        filesZip.extractAll(pathToUnpackedFiles);
        filesZip.close();
    }

    public void deleteAllFilesFolder(String pathToUnpackedFiles) {

        for (File myFile : Objects.requireNonNull(new File(pathToUnpackedFiles).listFiles()))
            if (myFile.isFile()) {
                myFile.delete();
            }
    }
}
