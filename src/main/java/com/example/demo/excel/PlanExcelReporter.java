package com.example.demo.excel;

import java.io.IOException;
import java.util.List;

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

import com.example.demo.dto.PlanData;
import com.example.demo.model.Plan;
import com.example.demo.word.PlanWordReporter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PlanExcelReporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    
	public PlanExcelReporter() {
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
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        font.setFontHeightInPoints((short)11);

        row = sheet.createRow(1);
        font.setFontHeight(16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "012.345.6789", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));
        font.setFontHeightInPoints((short)11);
    }
    private void writeDataTable(HttpSession session, List<Plan> planList){
        Integer searchMonth = (Integer) session.getAttribute("searchMonth");
        Integer searchYear = (Integer) session.getAttribute("searchYear");
        List<PlanData> planDatas = PlanWordReporter.convertJsonToPlanDataList((String) session.getAttribute("planData"));
        if(planDatas==null){
            System.out.println("Không thành công");
        }
            
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

        Row row = sheet.createRow(3);
        createCell(row, 1, "BÁO CÁO GÓI MẠNG BÁN CHẠY", style1);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 6));
        font1.setFontHeightInPoints((short)11);

        row = sheet.createRow(4);
        createCell(row, 1, "Tháng "+searchMonth+" Năm "+searchYear, style1);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 6));
        font1.setFontHeightInPoints((short)11);

        row = sheet.createRow(5);
        createCell(row, 1, "STT", style1);
        createCell(row, 2, "Gói mạng", style1);
        createCell(row,3, "Số lượt đăng ký", style1);
        createCell(row,4, "Đơn giá", style1);
        createCell(row,5, "Số thiết bị mạng xuất kho", style1);
        createCell(row,6, "Doanh thu", style1);
        int rowCount = row.getRowNum()+1;

        CellStyle style2 = workbook.createCellStyle();
        XSSFFont font2 = workbook.createFont();
        font2.setFontHeight(11);
        font2.setFontName("Calibri");
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.LEFT);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        
        // List<Plan> planList = planService.getAllPlans();
        int registeredSum = 0;
        long revenueSum = 0;
        if(planDatas!=null){
            for(int i=0;i<planDatas.size();i++){
                int tmp = 0;
                for(int j = 0;j<planList.size();j++){
                    if(planDatas.get(i).getName().equals(planList.get(j).getName())){
                        tmp = j;
                        break;
                    }
                }
                row = sheet.createRow(rowCount);
                createCell(row, 1, String.valueOf(i+1), style2);
                createCell(row, 2, planDatas.get(i).getName(), style2);
                registeredSum += planDatas.get(i).getAmount();
                createCell(row,3, String.valueOf(planDatas.get(i).getAmount()), style2);
                createCell(row,4, String.valueOf(planList.get(tmp).getPrice()), style2);
                createCell(row,5, "5", style2);
                revenueSum += planDatas.get(i).getAmount()*planList.get(tmp).getPrice();
                createCell(row,6, String.valueOf(planDatas.get(i).getAmount()*planList.get(tmp).getPrice()), style2);
                rowCount += 1;
            }    
        }
        row = sheet.createRow(rowCount);
        createCell(row, 1, "", style2);
        createCell(row, 2, "Tổng", style2);
        createCell(row,3, String.valueOf(registeredSum), style2);
        createCell(row,4, "", style2);
        createCell(row,5, "", style2);
        createCell(row,6, String.valueOf(revenueSum), style2);
        
    }
    private void writeFooterLine(){
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setFontName("Calibri");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(sheet.createRow(20), 5, "Giám đốc Công ty", style);
        font.setItalic(true);
        style.setFont(font);
        createCell(sheet.createRow(19), 5, "Hà nội, ngày … tháng … năm …", style);
        font.setFontHeight(9);
        style.setFont(font);
        createCell(sheet.createRow(21), 5, "(ký ghi rõ họ và tên)", style);
    }
    public void export(HttpServletResponse response, HttpSession session, List<Plan> planList) throws IOException{
        writerHeaderLine();
        writeDataTable(session, planList);
        writeFooterLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
