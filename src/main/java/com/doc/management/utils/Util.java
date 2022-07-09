package com.doc.management.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = formatter.format(localDateTime);
            return formattedDate;
        } catch (Exception e) {
            throw e;
        }
    }
}
