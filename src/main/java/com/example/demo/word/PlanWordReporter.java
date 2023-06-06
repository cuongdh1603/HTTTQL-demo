package com.example.demo.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.example.demo.dto.PlanData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

public class PlanWordReporter {
    
    public static ByteArrayInputStream generateWord(HttpSession session) throws IOException, InvalidFormatException{
        try(XWPFDocument doc = new XWPFDocument()){
            System.out.println(session.getAttribute("startDate"));
            System.out.println(session.getAttribute("searchMonth"));
            System.out.println(session.getAttribute("searchYear"));
            System.out.println((String) session.getAttribute("planData"));

            Integer startDate = (Integer) session.getAttribute("startDate");
            Integer searchMonth = (Integer) session.getAttribute("searchMonth");
            Integer searchYear = (Integer) session.getAttribute("searchYear");
            List<PlanData> planDatas = convertJsonToPlanDataList((String) session.getAttribute("planData"));
            if(planDatas==null){
                System.out.println("Không thành công");
            }
            XWPFParagraph paragraph = doc.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun run = paragraph.createRun();
            run.setFontSize(13);
            run.setFontFamily("Times New Roman");
            run.setText("CÔNG TY CỔ PHẦN ĐẦU TƯ\t\t\t\t\tCỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");

            XWPFParagraph paragraph2 = doc.createParagraph();
            paragraph2.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run2 = paragraph2.createRun();
            run2.setFontSize(13);
            run2.setFontFamily("Times New Roman");
            run2.setText("               QUỐC TẾ AIRTEL                             Độc lập – Tự do – Hạnh phúc");
            run2.addBreak();

            XWPFParagraph paragraph3 = doc.createParagraph();
            paragraph3.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run3 = paragraph3.createRun();
            run3.setFontSize(13);
            run3.setFontFamily("Times New Roman");
            run3.setItalic(true);
            run3.setText("Hà nội, ngày … tháng … năm …");
            run3.addBreak();

            XWPFParagraph paragraph4 = doc.createParagraph();
            paragraph4.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run4 = paragraph4.createRun();
            run4.setFontSize(13);
            run4.setFontFamily("Times New Roman");
            run4.setBold(true);
            run4.setText("BÁO CÁO");

            XWPFParagraph paragraph5 = doc.createParagraph();
            paragraph5.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run5 = paragraph5.createRun();
            run5.setFontSize(13);
            run5.setFontFamily("Times New Roman");
            run5.setItalic(true);
            run5.setText("(V/v: Gói mạng bán chạy – Tháng …năm …)");
            
            XWPFParagraph paragraph6 = doc.createParagraph();
            paragraph6.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run6 = paragraph6.createRun();
            run6.setFontSize(13);
            run6.setFontFamily("Times New Roman");
            run6.setText("Kính gửi: Ban giám đốc Công ty Cổ phần Đầu tư Quốc tế Airtel;");

            XWPFParagraph paragraph7 = doc.createParagraph();
            paragraph7.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run7 = paragraph7.createRun();
            run7.setFontSize(13);
            run7.setFontFamily("Times New Roman");
            run7.setText("Phòng quản lý gửi bảng thống kê lượng gói mạng đã bán trong tháng "+searchMonth+" năm "+searchYear+" như sau:");

            XWPFParagraph paragraph8 = doc.createParagraph();
            paragraph8.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run8 = paragraph8.createRun();
            run8.setFontSize(13);
            run8.setFontFamily("Times New Roman");
            run8.setBold(true);
            run8.setText("   I.	Số gói mạng đã bán kể từ Ngày "+startDate+" tháng "+searchMonth+" năm "+searchYear);
            //---------------------------------------- Bảng số liệu --------------------------------------------
            int rowNum = (planDatas==null?1:planDatas.size()+1);
            XWPFTable table = doc.createTable(rowNum/*Chỗ này sẽ customize theo số gói mạng*/,2);

            table.setWidth(9000);

            XWPFTableCell titleCell1 = table.getRow(0).getCell(0);
            XWPFParagraph titleParagraph1 = titleCell1.addParagraph();
            titleParagraph1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun1 = titleParagraph1.createRun();
            titleRun1.setFontSize(13);
            titleRun1.setFontFamily("Times New Roman");
            titleRun1.setText("Gói mạng");

            XWPFTableCell titleCell2 = table.getRow(0).getCell(1);
            XWPFParagraph titleParagraph2 = titleCell2.addParagraph();
            titleParagraph2.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun2 = titleParagraph2.createRun();
            titleRun2.setFontSize(13);
            titleRun2.setFontFamily("Times New Roman");
            titleRun2.setText("Số lượt đăng ký");

            if(planDatas!=null){
                for(int i=0;i<planDatas.size();i++){
                    XWPFTableCell planNameCell = table.getRow(i+1).getCell(0);
                    XWPFParagraph planNameParagraph = planNameCell.addParagraph();
                    planNameParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun planNameRun = planNameParagraph.createRun();
                    planNameRun.setFontSize(13);
                    planNameRun.setFontFamily("Times New Roman");
                    planNameRun.setText(planDatas.get(i).getName());

                    XWPFTableCell amountCell = table.getRow(i+1).getCell(1);
                    XWPFParagraph amountParagraph = amountCell.addParagraph();
                    amountParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun amountRun = amountParagraph.createRun();
                    amountRun.setFontSize(13);
                    amountRun.setFontFamily("Times New Roman");
                    amountRun.setText(String.valueOf(planDatas.get(i).getAmount()));
                }
                XWPFParagraph paragraph9 = doc.createParagraph();
                paragraph9.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun run9 = paragraph9.createRun();
                run9.addBreak();
                run9.setFontSize(13);
                run9.setFontFamily("Times New Roman");
                run9.setText("   -	Gói mạng "+planDatas.get(0).getName()+" được đăng ký nhiều nhất với "+planDatas.get(0).getAmount()+" lượt, gói mạng "+planDatas.get(planDatas.size()-1).getName()+" được đăng ký ít nhất với "+planDatas.get(planDatas.size()-1).getAmount()+" lượt");
                run9.addBreak();
                run9.addBreak();
            }
            
            XWPFParagraph paragraph10 = doc.createParagraph();
            paragraph10.setAlignment(ParagraphAlignment.RIGHT);
            paragraph10.setSpacingAfterLines(100);
            XWPFRun run10 = paragraph10.createRun();
            run10.setFontSize(13);
            run10.setFontFamily("Times New Roman");
            run10.setItalic(true);
            run10.setText("Hà nội, ngày … tháng … năm …  ");

            XWPFParagraph paragraph11 = doc.createParagraph();
            paragraph11.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run11 = paragraph11.createRun();
            run11.setFontSize(13);
            run11.setFontFamily("Times New Roman");
            run11.setText("Giám đốc Công ty\t\t");

            XWPFParagraph paragraph12 = doc.createParagraph();
            paragraph12.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run12 = paragraph12.createRun();
            run12.setFontSize(10);
            run12.setFontFamily("Times New Roman");
            run12.setItalic(true);
            run12.setText("(ký ghi rõ họ và tên)\t\t");

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            doc.write(b);
            // b.close();
            doc.close();
            return new ByteArrayInputStream(b.toByteArray());
        }
    }
    public static List<PlanData> convertJsonToPlanDataList(String jsonData){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Integer> map = objectMapper.readValue(jsonData, new TypeReference<Map<String, Integer>>() {});
            List<PlanData> planDatas = new ArrayList<>();
            for(Map.Entry<String, Integer> e : map.entrySet()){
                PlanData planData = new PlanData(e.getKey(), e.getValue());
                planDatas.add(planData);
            }
            for(PlanData planData : planDatas){
                System.out.println(planData.getName()+" : "+planData.getAmount());
            }
            return planDatas;
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
