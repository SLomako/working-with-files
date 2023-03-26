package ru.lomakosv;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.lomakosv.utils.ZipFileChatGpt;

import java.io.File;

public class TestBase {

    ZipFileChatGpt zipFile = new ZipFileChatGpt();

    protected static final String PATH_TO_UNPACKED_FILES = System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "test" + File.separator
            + "resources" + File.separator + "unpacked-zip-files" + File.separator;
    protected static final ClassLoader PATH_TO_ZIP_FILE = ZipFileChatGpt.class.getClassLoader();

    @BeforeEach
    void unzip() {
        zipFile.unzipFiles(PATH_TO_UNPACKED_FILES, PATH_TO_ZIP_FILE);
    }

    @AfterEach
    void delete() {
        zipFile.delete(PATH_TO_UNPACKED_FILES);
    }
}
