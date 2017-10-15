package com.piaoniu.biz;

import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/3/24
 *         Time: 下午4:15
 */
public abstract class ExcelUtils {

	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()){
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf((int)cell.getNumericCellValue());
		}
		return "";
	}

	public static void convertAndWrite(List<String> headlineFields, List<List<String>> content,
			OutputStream outputStream)
			throws IOException {
		XSSFWorkbook workbook = getSheets(headlineFields, content);
		workbook.write(outputStream);
	}
	public static void convertAndWrite(List<Pair<List<String>,List<List<String>>>> contents
			,OutputStream outputStream)throws IOException  {
		XSSFWorkbook workbook = getMultiSheets(contents);
		workbook.write(outputStream);
	}

	public static void convertAndWriteSingleColumn(String headlineField, List<String> content,
			OutputStream outputStream)
			throws IOException {
		List<List<String>> content0 = new ArrayList<>();
		content.forEach(line->content0.add(Lists.newArrayList(line)));
		XSSFWorkbook workbook = getSheets(Lists.newArrayList(headlineField), content0);
		workbook.write(outputStream);
	}

	public static byte[] convertSingleColumn(String headlineField, List<String> content)
			throws IOException {
		List<List<String>> content0 = new ArrayList<>();
		content.forEach(line->content0.add(Lists.newArrayList(line)));
		return convert(Lists.newArrayList(headlineField), content0);
	}

	public static byte[] convert(List<String> headlineFields, List<List<String>> content)
			throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		convertAndWrite(headlineFields, content, byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	public static byte[] convert(List<Pair<List<String> ,List<List<String>>>> contents)
			throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		convertAndWrite(contents, byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}


	private static XSSFWorkbook getSheets(List<String> headlineFields, List<List<String>> content) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("sheet");
		workbook.createCellStyle().setAlignment(XSSFCellStyle.ALIGN_CENTER);
		writeRow(0, headlineFields, sheet);
		AtomicInteger rowIndex = new AtomicInteger();
		content.forEach(strings -> writeRow(rowIndex.incrementAndGet(), strings, sheet));
		return workbook;
	}

	private static XSSFWorkbook getMultiSheets(List<Pair<List<String> ,List<List<String>>>> contents) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		workbook.createCellStyle().setAlignment(XSSFCellStyle.ALIGN_CENTER);
		for (int i = 0; i <contents.size() ; i++) {
			Pair<List<String>,List<List<String>>> content = contents.get(i);
			XSSFSheet sheet = workbook.createSheet("sheet"+i);
			writeRow(0, content.getKey(), sheet);
			AtomicInteger rowIndex = new AtomicInteger();
			content.getValue().forEach(strings -> writeRow(rowIndex.incrementAndGet(), strings, sheet));
		}
		return workbook;
	}

	private static void writeRow(int rowNumber, List<String> fields, XSSFSheet sheet) {
		XSSFRow headRow = sheet.createRow(rowNumber);
		AtomicInteger cellIndex = new AtomicInteger();
		fields.forEach(headField -> headRow.createCell(cellIndex.getAndAdd(1)).setCellValue(headField));
	}

	public static List<List<String>> parseExcel(InputStream inputStream){

		Workbook workbook =null;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			log.error("parse file error ", e);
			return Collections.emptyList();
		}
		//默认只处理第一个sheet
		Sheet sheet = workbook.getSheetAt(0);

		//转换到内存数据
		List<List<String>> excelDataMap = new ArrayList<>();

		int rowNum  = sheet.getLastRowNum()+1;

		for(int i=0;i<rowNum;i++){
			Row row = sheet.getRow(i);
			int cellNums =  row.getLastCellNum();
			List<String> tempList = Lists.newArrayList();
			for(int j=0;j<cellNums;j++){

				Cell cell =  row.getCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String result =  cell.getStringCellValue();
				tempList.add(result);
			}
			excelDataMap.add(tempList);
		}
		return excelDataMap;
	}



}
