package ru.lomakosv;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.lomakosv.utils.TrimmedString;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WorkingWithFilesTest extends TestBase {

    TrimmedString trimmedString = new TrimmedString();

    @Test
    void readingAndCheckTxtFiles() throws Exception {
        File file = new File(PATH_TO_UNPACKED_FILES, "file-txt.txt");
        try (InputStream is = new FileInputStream(file)) {
            byte[] bytes = is.readAllBytes();
            String fileAsString = new String(bytes);
            Assertions.assertTrue(fileAsString.contains("Отлично"));
        }
    }

    @Test
    void readingAndCheckDocxFiles() throws Exception {
        File file = new File(PATH_TO_UNPACKED_FILES, "file-docx.docx");
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            System.out.println(document);
            Assertions.assertTrue(paragraphs.get(5).getRuns().get(0).getText(0).contains("Отлично"));
        }
    }

    @Test
    void readingAndCheckPdfFiles() throws Exception {
        File file = new File(PATH_TO_UNPACKED_FILES, "file-pdf.pdf");
        PDF pdf = new PDF(file);
        Assertions.assertTrue(pdf.text.contains("Отлично"));
    }

    @Test
    void readingAndCheckCsvFiles() throws Exception {
        File file = new File(PATH_TO_UNPACKED_FILES, "file-csv.csv");
        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader((is), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(isr).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build()
        ) {
            List<String[]> content = csvReader.readAll();
            trimmedString.trimmedString(content);
            Assertions.assertArrayEquals(new String[]{"Aнтон Антонов", "Anton@gmail.com", "МСК", "МСК"}, content.get(0));
        }
    }

    @Test
    void readingAndCheckXlsxFiles() {
        File file = new File(PATH_TO_UNPACKED_FILES, "file-xlsx.xlsx");
        XLS xls = new XLS(file);
        Assertions.assertTrue(xls.excel.getSheetAt(0).getRow(7).
                getCell(3).getStringCellValue().contains("Отлично"));
    }
}