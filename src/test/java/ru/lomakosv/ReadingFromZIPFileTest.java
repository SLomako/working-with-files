package ru.lomakosv;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.lomakosv.utils.TrimmedString;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ReadingFromZIPFileTest {

    private static final ClassLoader classLoader = ReadingFromZIPFileTest.class.getClassLoader();
    private static final TrimmedString trimmedString = new TrimmedString();
    private static final String zipPath = "file.zip";

    @Test
    public void testCSV() throws Exception {

        try (InputStream is = classLoader.getResourceAsStream(zipPath)) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".csv")) {
                        InputStreamReader isr = new InputStreamReader((zis), StandardCharsets.UTF_8);
                        CSVReader csvReader = new CSVReaderBuilder(isr).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
                        List<String[]> content = csvReader.readAll();
                        trimmedString.trimmedString(content);
                        Assertions.assertArrayEquals(new String[]{"Aнтон Антонов", "Anton@gmail.com", "МСК", "МСК"}, content.get(0));
                    }
                }
            }
        }
    }

    @Test
    public void testPDF() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(zipPath)) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".pdf")) {
                        PDF pdf = new PDF(zis);
                        Assertions.assertTrue(pdf.text.contains("Отлично"));
                    }
                }
            }
        }
    }

    @Test
    public void testTXT() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(zipPath)) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".txt")) {
                        byte[] bytes = zis.readAllBytes();
                        String fileAsString = new String(bytes, Charset.forName("windows-1251"));
                        Assertions.assertTrue(fileAsString.contains("Отлично"));
                    }
                }
            }
        }
    }

    @Test
    public void testXLSX() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(zipPath)) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith("xlsx")) {
                        XLS xls = new XLS(zis);
                        Assertions.assertTrue(xls.excel.getSheetAt(0).getRow(7)
                                .getCell(3).getStringCellValue().contains("Отлично"));
                    }
                }
            }
        }
    }

    @Test
    public void testDOCX() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(zipPath)) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".docx")) {
                        XWPFDocument document = new XWPFDocument(zis);
                        List<XWPFParagraph> paragraphs = document.getParagraphs();
                        System.out.println(document);
                        Assertions.assertTrue(paragraphs.get(5).getRuns().get(0).getText(0).contains("Отлично"));
                    }

                }
            }
        }
    }
}



