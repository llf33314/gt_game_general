package com.gt.game.core.util;


/**
 * Jun 25, 2012
 */

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel组件
 * 
 * @author Snowolf
 * @version 1.0
 * @since 1.0
 */
public abstract class ExcelHelper {

	/**
	 * Excel 2003
	 */
	private final static String XLS = "xls";
	/**
	 * Excel 2007
	 */
	private final static String XLSX = "xlsx";
	/**
	 * 分隔符
	 */
	private final static String SEPARATOR = "|";

	/**
	 * 由Excel文件的Sheet导出至List
	 * 
	 * @param file
	 * @param sheetNum
	 * @return
	 */
	public static List<List<Object>> exportListFromExcel(File file, int sheetNum)
			throws IOException {
		return exportListFromExcel(new FileInputStream(file),
				FilenameUtils.getExtension(file.getName()), sheetNum);
	}
	
	/**
	 * 由Excel文件的Sheet导出至List
	 * 
	 * @param inputStream
	 * @param sheetNum
	 * @return
	 */
	public static List<List<Object>> exportListFromExcel(InputStream inputStream, int sheetNum,String fileName)
			throws IOException {
		return exportListFromExcel(inputStream,
				fileName, sheetNum);
	}


	/**
	 * 由Excel流的Sheet导出至List
	 * 
	 * @param is
	 * @param extensionName
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> exportListFromExcel(InputStream is,
			String extensionName, int sheetNum) throws IOException {

		Workbook workbook = null;

		if (extensionName.toLowerCase().equals(XLS)) {
			workbook = new HSSFWorkbook(is);
		} else if (extensionName.toLowerCase().equals(XLSX)) {
			workbook = new XSSFWorkbook(is);
		}

		return exportListFromExcel(workbook, sheetNum);
	}

	/**
	 * 由指定的Sheet导出至List
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	private static List<List<Object>> exportListFromExcel(Workbook workbook,
			int sheetNum) {

		Sheet sheet = workbook.getSheetAt(sheetNum);

		// 解析公式结果
		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();

		List<List<Object>> list = new ArrayList<List<Object>>();

		int minRowIx = sheet.getFirstRowNum();
		int maxRowIx = sheet.getLastRowNum();
		DecimalFormat df = new DecimalFormat("0");// 格式化数字
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		Object val = null;
		for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			StringBuilder sb = new StringBuilder();
			List<Object> cells=new ArrayList<Object>();
			short minColIx = row.getFirstCellNum();
			short maxColIx = 10;
			for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
				Cell cell = row.getCell(new Integer(colIx));
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue == null) {
					cells.add("");
					continue;
				}
				// 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
				// 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
				switch (cellValue.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					sb.append(SEPARATOR + cellValue.getBooleanValue());
					cells.add(cellValue.getBooleanValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						val = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						val = df.format(cell.getNumericCellValue());
					} else {
						val = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					cells.add(val);
					break;
				case Cell.CELL_TYPE_STRING:
//					sb.append(SEPARATOR + cellValue.getStringValue());
					cells.add( cellValue.getStringValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_ERROR:
					break;
				default:
					cells.add("");
					break;
				}
			}
			list.add(cells);
		}
		return list;
	}
}

