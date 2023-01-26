package com.clusus.Bloomberg.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.clusus.Bloomberg.util.MessageConstant.DATE_AND_TIME_FORMAT;

public class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static LocalDateTime convertStringToLocalDateTime(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_AND_TIME_FORMAT);
        return LocalDateTime.parse(value, formatter);
    }
}
