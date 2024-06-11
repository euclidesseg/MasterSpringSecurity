package com.master.api.spring.security.master.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
// clase para manerjar los errores en la aplicación
public class ApiError implements Serializable{
    
    private String backendMessage;
    private String message;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String url;
    private String method;



    public String getBackendMessage() {
        return backendMessage;
    }
    public void setBackendMessage(String backendMessage) {
        this.backendMessage = backendMessage;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
}

