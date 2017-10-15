package com.piaoniu.biz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/3/24
 *         Time: 下午4:15
 */
public abstract class CSVUtils {

	public static byte[] convertSingleColumn(String headlineField, List<String> content)
			throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		convertAndWrite(headlineField, content, byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	private static void convertAndWrite(String headlineField, List<String> content, ByteArrayOutputStream byteArrayOutputStream) {
		try(PrintWriter p = new PrintWriter(byteArrayOutputStream)){
			p.println(headlineField);
			content.forEach(p::println);
		}
	}


}
