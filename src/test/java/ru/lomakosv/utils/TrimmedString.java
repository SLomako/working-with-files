package ru.lomakosv.utils;

import java.util.List;

public class TrimmedString {

    public void trimmedString(List<String[]> content){
        for (String[] array : content) {
            for (int i = 0; i < array.length; i++) {
                String trimmedString = array[i].trim();
                array[i] = trimmedString;
            }
        }
    }
}
