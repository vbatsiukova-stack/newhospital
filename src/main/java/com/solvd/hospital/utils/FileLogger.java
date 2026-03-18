package com.solvd.hospital.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {

    private final String fileName;

    public FileLogger(String fileName) {
        this.fileName = fileName;
    }

    public void log(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(message);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("FILE LOG ERROR: " + e.getMessage());
        }
    }
}
