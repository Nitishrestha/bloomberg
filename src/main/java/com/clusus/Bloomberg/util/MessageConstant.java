package com.clusus.Bloomberg.util;

public class MessageConstant {

    private MessageConstant() {
    }

    public static final String DATE_AND_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String UTF_8 = "UTF-8";

    public static final String TYPE = "text/csv";

    public static final String UPLOAD_SUCCESS = "SUCCESSFULLY UPLOADED THE FILE: ";
    public static final String UPLOAD_FAILED = "failed to store csv data: ";
    public static final String INVALID_FILE_FORMAT = "Please upload a csv file!";
    public static final String FILE_PARSE_FAILED = "fail to parse CSV file: ";
    public static final String FILE_SIZE_TOO_LARGE = "File too large! ";

}
