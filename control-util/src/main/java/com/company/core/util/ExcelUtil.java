package com.company.core.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.core.Enum.ExcelElement;
import com.company.core.Enum.ExcelFormat;
import com.company.core.Enum.ExcelSelect;

public class ExcelUtil<T> {

	Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	public HSSFWorkbook wb;
	String sheet;
	HSSFCellStyle styleBody;
	boolean fieldsNotSup = false;

	public ExcelUtil() {
		wb = new HSSFWorkbook();
		sheet = "sheet1";
		initStyle(wb);
	}

	public ExcelUtil(String sheetName) {
		this();
		if (sheetName != null) {
			sheet = sheetName;
		}
	}

	public void initStyle(HSSFWorkbook wb) {
		styleBody = wb.createCellStyle();
		styleBody.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		styleBody.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		styleBody.setWrapText(true); // 设置是否能够换行，能够换行为true
	}

	public void export(List<T> ts) throws Exception {
		if (ts == null) {
			throw new Exception("export Excel error, data  is null");
		}
		CollectionUtil<T> cu = new CollectionUtil<T>();
		List<List<T>> divideIntoGroups = cu.divideIntoGroups(ts, 65535);
		int index = 1;
		HSSFSheet createSheet;
		for (List<T> list : divideIntoGroups) {
			createSheet = wb.createSheet(sheet + index++);
			exportSheet(list, createSheet);
		}
	}

	private void exportSheet(List<T> list, HSSFSheet createSheet)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		T t = null;
		HSSFRow row = null;
		String[] beanFieldNames = null;
		Map<String, String> excelFieldMap = null;
		int rowIndex = 1;

		for (int i = 1; i <= list.size(); i++) {
			t = list.get(i - 1);
			if (t == null) {
				continue;
			}
			if (beanFieldNames == null || excelFieldMap == null) {
				excelFieldMap = getExcelFieldMap(t);
				beanFieldNames = (String[]) excelFieldMap.keySet().toArray(new String[excelFieldMap.keySet().size()]);
			}
			if (rowIndex == 1) {
				HSSFRow oneRow = createSheet.createRow(0);
				exportRow(oneRow, beanFieldNames, excelFieldMap);
			}
			row = createSheet.createRow(rowIndex++);
			exportRow(t, row, beanFieldNames);
		}
	}

	private void exportRow(HSSFRow row, String[] beanFieldNames, Map<String, String> excelFieldMap) {
		HSSFCell cell = null;
		for (int i = 0; i < beanFieldNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(excelFieldMap.get(beanFieldNames[i]));
		}
	}

	private void exportRow(T t, HSSFRow row, String[] beanFieldNames)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String beanFieldName = null;
		String value = null;
		HSSFCell cell = null;
		for (int j = 0; j < beanFieldNames.length; j++) {
			beanFieldName = beanFieldNames[j];
			value = fomcatValue(t, beanFieldName);
			cell = row.createCell(j);
			cell.setCellValue(value);
			cell.setCellStyle(styleBody);
		}
	}

	private String fomcatValue(T t, String beanFieldName)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String value = BeanUtils.getSimpleProperty(t, beanFieldName);
		try {
			Field field = t.getClass().getDeclaredField(beanFieldName);
			boolean annotationPresent = field.isAnnotationPresent(ExcelFormat.class);
			if(annotationPresent){
				ExcelFormat annotation = field.getAnnotation(ExcelFormat.class);
				String dateFormat = annotation.dateFormat();
				if(dateFormat!= null && !"".equals(dateFormat)){
					value = DateFormatUtils.format(new Date(value), dateFormat);
				}
			}
			boolean annotationPresent2 = field.isAnnotationPresent(ExcelSelect.class);
			if(annotationPresent2){
				ExcelSelect annotation = field.getAnnotation(ExcelSelect.class);
				String[] select = annotation.select();
				if(select!= null && (select.length % 2 == 0)){
					for(int i = 0 ; i<select.length ; i=i+2){
						if(select[i] != null && select[i].equals(value)){
							value = select[i+1];
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("数据格式化异常，使用原数据",e);
		} 
		return value;
	}

	public void flushToRequest(HttpServletResponse response, String fileName) throws Exception {
		if (fileName == null) {
			fileName = "default";
		}
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", String.format("attachment;filename=%s.xls", fileName));
			OutputStream ouputStream = response.getOutputStream();
			exportStream(ouputStream);
		} catch (Exception e) {
			logger.error("导出excel错误:", e);
			throw e;
		}
	}

	public void exportStream(OutputStream o) throws Exception {
		try {
			wb.write(o);
			o.flush();
			o.close();
		} catch (Exception e) {
			logger.error("导出excel错误:", e);
			throw e;
		}
	}

	public Map<String, String> getExcelFieldMap(T t) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> ret = new LinkedHashMap<String, String>();
		Map<Integer, String> order = new HashMap<Integer, String>();
		Field[] fields = t.getClass().getFields();
		if (fieldsNotSup) {
			fields = null;
		}
		Field[] thisFields = t.getClass().getDeclaredFields();
		fields = Union(fields, thisFields);
		boolean annotationPresent;
		for (Field f : fields) {
			if (f == null) {
				continue;
			}
			annotationPresent = f.isAnnotationPresent(ExcelElement.class);// 判断是否有该类型的注解
			if (annotationPresent) {
				ExcelElement test = f.getAnnotation(ExcelElement.class);// 得到该类型的注解
				map.put(f.getName(), test.field());
				order.put(test.index(), f.getName());
			}
		}
		// 排序
		Set<Integer> keySet = order.keySet();
		Integer[] array = keySet.toArray(new Integer[keySet.size()]);
		Arrays.sort(array);
		for (int i = 0; i < array.length; i++) {
			String fieldName = order.get(array[i]);
			String excelFieldName = map.get(fieldName);
			ret.put(fieldName, excelFieldName);
		}
		return ret;
	}

	public Field[] Union(Field[] field1, Field[] field2) {
		Field[] all = null;
		if (field1 != null && field2 != null) {
			all = new Field[field2.length + field1.length];
			System.arraycopy(field1, 0, all, 0, field1.length);
			System.arraycopy(field2, 0, all, field1.length, field2.length);
		} else if (field1 == null && field2 != null) {
			all = field2;
		} else if (field1 != null && field2 == null) {
			all = field1;
		}
		return all;
	}

	/**
	 * 是否导出父类的字段： true 不导 false 导
	 *
	 * @param b
	 */
	public void fieldsNotSuperClass(boolean b) {
		fieldsNotSup = b;
	}

}
