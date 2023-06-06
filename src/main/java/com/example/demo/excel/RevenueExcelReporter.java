package com.example.demo.excel;

import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RevenueExcelReporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    
	public RevenueExcelReporter() {
		workbook = new XSSFWorkbook();
		
	}
    private void createCell (Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof Long){
            cell.setCellValue((Long) value);
        }
        else if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }
        else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);

        }
        cell.setCellStyle(style);
    }
    private void writerHeaderLine(){
        sheet = workbook.createSheet("RevenueReport");

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "CÔNG TY CỔ PHẦN ĐẦU TƯ QUỐC TẾ AIRTEL", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        font.setFontHeightInPoints((short)11);

        row = sheet.createRow(1);
        font.setFontHeight(16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "012.345.6789", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        font.setFontHeightInPoints((short)11);
    }
    private void writeDataTable(HttpSession session){
        Integer searchMonth = (Integer) session.getAttribute("searchMonth");
        Integer searchYear = (Integer) session.getAttribute("searchYear");

        Long unhandledRevenue = (Long)session.getAttribute("unhandledRevenue");
        Long processingRevenueSum = (Long)session.getAttribute("processingRevenueSum");
        Long gainedRevenueSum = (Long)session.getAttribute("gainedRevenueSum");

        CellStyle style1 = workbook.createCellStyle();
        XSSFFont font1 = workbook.createFont();
        font1.setFontHeight(16);
        font1.setFontName("Calibri");
        style1.setFont(font1);
        style1.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);

        createCell(sheet.createRow(3), 1, "BÁO CÁO DOANH THU", style1);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));
        font1.setFontHeightInPoints((short)11);

        createCell(sheet.createRow(4), 1, "Tháng "+searchMonth+" Năm "+searchYear, style1);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 3));
        font1.setFontHeightInPoints((short)11);

        Row row = sheet.createRow(5);
        createCell(row, 1, "Nội dung", style1);
        createCell(row, 2, "Doanh thu", style1);
        createCell(row,3, "Đơn vị", style1);

        CellStyle style2 = workbook.createCellStyle();
        XSSFFont font2 = workbook.createFont();
        font2.setFontHeight(11);
        font2.setFontName("Times New Roman");
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.LEFT);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        row = sheet.createRow(6);
        createCell(row, 1, "Tổng doanh thu dự kiến nhận được từ các hợp đồng", style2);
        createCell(row, 2, String.valueOf(unhandledRevenue), style2);
        createCell(row,3, "VNĐ", style2);
        row = sheet.createRow(7);
        createCell(row, 1, "Tổng doanh thu chưa nhận từ các hợp đồng", style2);
        createCell(row, 2, String.valueOf(processingRevenueSum), style2);
        createCell(row,3, "VNĐ", style2);
        row = sheet.createRow(8);
        createCell(row, 1, "Tổng doanh thu đã nhận từ các hợp đồng", style2);
        createCell(row, 2, String.valueOf(gainedRevenueSum), style2);
        createCell(row,3, "VNĐ", style2);

    }
    private void writeFooterLine(){
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setFontName("Calibri");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(sheet.createRow(14), 2, "Giám đốc Công ty", style);
        font.setItalic(true);
        style.setFont(font);
        createCell(sheet.createRow(13), 2, "Hà nội, ngày … tháng … năm …", style);
        font.setFontHeight(9);
        style.setFont(font);
        createCell(sheet.createRow(15), 2, "(ký ghi rõ họ và tên)", style);
    }
    public void export(HttpServletResponse response, HttpSession session) throws IOException{
        writerHeaderLine();
        writeDataTable(session);
        writeFooterLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
