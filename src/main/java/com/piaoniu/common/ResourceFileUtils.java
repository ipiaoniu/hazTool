package com.piaoniu.common;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

public class ResourceFileUtils extends FileUtils {

    public static void eachLine(String resourcePath, LineHandler lineHandler) throws IOException {
        InputStream is = ResourceFileUtils.class.getClassLoader().getResourceAsStream(resourcePath);
        echoLine0(is, lineHandler);
    }

    public static String readFile(String resourcePath) throws IOException {
        InputStream is = ResourceFileUtils.class.getClassLoader().getResourceAsStream(resourcePath);
        return IOUtils.toString(is);
    }

    public static byte[] readBytes(String resourcePath) throws IOException {
        InputStream is = ResourceFileUtils.class.getClassLoader().getResourceAsStream(resourcePath);
        return IOUtils.toByteArray(is);
    }
}
