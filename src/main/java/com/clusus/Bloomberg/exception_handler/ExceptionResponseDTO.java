package com.clusus.Bloomberg.exception_handler;

public class ExceptionResponseDTO {

    private String message;
    private String callerUrl;


    public ExceptionResponseDTO(String message, String callerUrl) {
        this.message = message;
        this.callerUrl = callerUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallerUrl() {
        return callerUrl;
    }

    public void setCallerUrl(String callerUrl) {
        this.callerUrl = callerUrl;
    }
}
