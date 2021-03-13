package com.example.yourdairy;

import android.widget.EditText;

import java.io.Serializable;
import java.util.Date;

public class FileObj implements Serializable {

    private String fileName;

    private String fileData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
}
