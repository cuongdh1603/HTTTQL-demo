package com.example.demo.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ContractExcelReporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    
	public ContractExcelReporter() {
		workbook = new XSSFWorkbook();
		
	}
    private void createNewCell (Row row, int columnCount, Object value, CellStyle style){
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
    private static Cell createCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        if (style != null) {
            cell.setCellStyle(style);
        }
        return cell;
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
        createNewCell(row, 0, "CÔNG TY CỔ PHẦN ĐẦU TƯ QUỐC TẾ AIRTEL", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        font.setFontHeightInPoints((short)11);

        row = sheet.createRow(1);
        font.setFontHeight(16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createNewCell(row, 0, "012.345.6789", style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        font.setFontHeightInPoints((short)11);
    }
    private void writeDataTable(HttpSession session){
        Integer startDate = (Integer) session.getAttribute("startDate");
        Integer searchMonth = (Integer) session.getAttribute("searchMonth");
        Integer searchYear = (Integer) session.getAttribute("searchYear");
        List<Float> values = getRatioListFromJson((String) session.getAttribute("contractData"));
        if(values==null){
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
        Cell cell1 = createCell(row, 1, "BÁO CÁO TÌNH HÌNH QUẢN LÝ HỢP ĐỒNG", null);
        Cell cell2 = createCell(row, 2, "", null);
        Cell cell3 = createCell(row, 3, "", null);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));
        cell1.setCellStyle(style1);
        cell2.setCellStyle(style1);
        cell3.setCellStyle(style1);
        font1.setFontHeightInPoints((short)11);

        row = sheet.createRow(4);
        Cell cell4 = createCell(row, 1, "Tháng "+searchMonth+" Năm "+searchYear, null);
        Cell cell5 = createCell(row, 2, "", null);
        Cell cell6 = createCell(row, 3, "", null);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 3));
        cell4.setCellStyle(style1);
        cell5.setCellStyle(style1);
        cell6.setCellStyle(style1);
        row = sheet.createRow(5);
        Cell cell7 = createCell(row, 1, "Nội dung", null);
        Cell cell8 = createCell(row, 2, "", null);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
        cell7.setCellStyle(style1);
        cell8.setCellStyle(style1);
        createNewCell(row, 3, "Số lượng hợp đồng", style1);

        
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
        createNewCell(row, 1, "I. Số hợp đồng phòng quản lý phụ trách kể từ Ngày "+startDate+" tháng "+searchMonth+" năm "+searchYear, null);
        sheet.addMergedRegion(new CellRangeAddress(6, 8, 1, 1));
        Cell mergedCell1 = sheet.getRow(6).getCell(1);
        mergedCell1.setCellStyle(style2);

        createNewCell(row, 2, "Hợp đồng chưa xử lý", style2);
        createNewCell(row, 3, String.valueOf(values.get(0))+" %\n", style2);
        row = sheet.createRow(7);
        createNewCell(row, 2, "Hợp đồng đã phân công", style2);
        createNewCell(row, 3, String.valueOf(values.get(1))+" %\n", style2);
        row = sheet.createRow(8);
        createNewCell(row, 2, "Hợp đồng đã xác nhận phân công", style2);
        createNewCell(row, 3, String.valueOf(values.get(2))+" %\n", style2);

        row = sheet.createRow(9);
        createNewCell(row, 1, "II. Số hợp đồng phòng tài chính phụ trách kể từ Ngày … tháng … năm …", null);
        sheet.addMergedRegion(new CellRangeAddress(9, 11, 1, 1));
        Cell mergedCell2 = sheet.getRow(9).getCell(1);
        mergedCell2.setCellStyle(style2);

        createNewCell(row, 2, "Hợp đồng chưa thanh toán", style2);
        createNewCell(row, 3, String.valueOf(values.get(3))+" %\n", style2);
        row = sheet.createRow(10);
        createNewCell(row, 2, "Hợp đồng đã thanh toán", style2);
        createNewCell(row, 3, String.valueOf(values.get(4))+" %\n", style2);
        row = sheet.createRow(11);
        createNewCell(row, 2, "Hợp đồng nợ xấu", style2);
        createNewCell(row, 3, "0 %", style2);
    }
    private void writeFooterLine(){
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setFontName("Calibri");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(sheet.createRow(15), 3, "Giám đốc Công ty", style);
        font.setItalic(true);
        style.setFont(font);
        createCell(sheet.createRow(14), 3, "Hà nội, ngày … tháng … năm …", style);
        font.setFontHeight(9);
        style.setFont(font);
        createCell(sheet.createRow(16), 3, "(ký ghi rõ họ và tên)", style);
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
    private static List<Float> getRatioListFromJson(String jsonData){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Float> map = objectMapper.readValue(jsonData, new TypeReference<Map<String, Float>>() {});
            List<Float> values = new ArrayList<>();
            for(Map.Entry<String, Float> e : map.entrySet()){
                values.add(e.getValue());
            }
            for(Float v : values){
                System.out.println(v);
            }
            return values;
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
