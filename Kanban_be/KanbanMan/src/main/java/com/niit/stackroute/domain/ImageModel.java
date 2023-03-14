package com.niit.stackroute.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ImageModel {

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String fileName;
    private String message;


    public ImageModel(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }
}
