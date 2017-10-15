package com.piaoniu.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class FileUtils {

    public static void eachLine(String filePath, LineHandler lineHandler) throws IOException {
        InputStream is = new FileInputStream(filePath);
        echoLine0(is, lineHandler);
    }

    protected static void echoLine0(InputStream is,LineHandler lineHandler) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                lineHandler.handleLine(line);
            }
        }
    }
}
