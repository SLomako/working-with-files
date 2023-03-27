package ru.lomakosv;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONTestTest{

    @Test
    void readingAndCheckJSONFiles()  throws Exception  {

        ClassLoader classLoader = JSONTestTest.class.getClassLoader();
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = classLoader.getResourceAsStream("file-json.json")) {
            assert is != null;
            try (InputStreamReader isr = new InputStreamReader(is)) {
                JSONObject jsonObject = mapper.readValue(isr, JSONObject.class);
                Assertions.assertEquals(1971, jsonObject.album.releaseYear.intValue());
                Assertions.assertEquals("Led Zeppelin IV", jsonObject.album.titleAlbum);

            }
        }
    }
}
