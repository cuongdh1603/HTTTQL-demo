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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

public class ContractWordReporter {
    public static ByteArrayInputStream generateWord(HttpSession session) throws IOException, InvalidFormatException{
        try(XWPFDocument doc = new XWPFDocument()){
            Integer startDate = (Integer) session.getAttribute("startDate");
            Integer searchMonth = (Integer) session.getAttribute("searchMonth");
            Integer searchYear = (Integer) session.getAttribute("searchYear");
            List<Float> values = getRatioListFromJson((String) session.getAttribute("contractData"));
            if(values==null){
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
            run5.setText("(V/v: Báo cáo trạng thái của các hợp đồng – Tháng … năm …)");
            
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
            run7.setText("Phòng tổng hợp gửi thống kê trạng thái hợp đồng của tháng "+searchMonth+" năm "+searchYear+" như sau:");

            XWPFParagraph paragraph8 = doc.createParagraph();
            paragraph8.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run8 = paragraph8.createRun();
            run8.setFontSize(13);
            run8.setFontFamily("Times New Roman");
            run8.setBold(true);
            run8.setText("   I.	Số hợp đồng phòng quản lý phụ trách kể từ Ngày "+startDate+" tháng "+searchMonth+" năm "+searchYear);
            
            XWPFParagraph paragraph9 = doc.createParagraph();
            paragraph9.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run9 = paragraph9.createRun();
            run9.setFontSize(13);
            run9.setFontFamily("Times New Roman");
            run9.setText("   -	Tỉ lệ hợp đồng chưa xử lý: "+values.get(0)+" %\n");

            XWPFParagraph paragraph10 = doc.createParagraph();
            paragraph10.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run10 = paragraph10.createRun();
            run10.setFontSize(13);
            run10.setFontFamily("Times New Roman");
            run10.setText("   -	Tỉ lệ hợp đồng đã phân công: "+values.get(1)+" %\n");

            XWPFParagraph paragraph11 = doc.createParagraph();
            paragraph11.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run11 = paragraph11.createRun();
            run11.setFontSize(13);
            run11.setFontFamily("Times New Roman");
            run11.setText("   -	Tỉ lệ hợp đồng đã xác nhận phân công: "+values.get(2)+" %\n");
            run11.addBreak();

            XWPFParagraph paragraph12 = doc.createParagraph();
            paragraph12.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run12 = paragraph12.createRun();
            run12.setFontSize(13);
            run12.setFontFamily("Times New Roman");
            run12.setBold(true);
            run12.setText("   II.	Số hợp đồng phòng tài chính phụ trách kể từ Ngày "+startDate+" tháng "+searchMonth+" năm "+searchYear);
            

            XWPFParagraph paragraph13 = doc.createParagraph();
            paragraph13.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run13 = paragraph13.createRun();
            run13.setFontSize(13);
            run13.setFontFamily("Times New Roman");
            run13.setText("   -	Tỉ lệ hợp đồng đã lắp đặt: "+values.get(3)+" %\n");

            XWPFParagraph paragraph14 = doc.createParagraph();
            paragraph14.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run14 = paragraph14.createRun();
            run14.setFontSize(13);
            run14.setFontFamily("Times New Roman");
            run14.setText("   -	Tỉ lệ hợp đồng đã thanh toán: "+values.get(4)+" %\n");

            XWPFParagraph paragraph15 = doc.createParagraph();
            paragraph15.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run15 = paragraph15.createRun();
            run15.setFontSize(13);
            run15.setFontFamily("Times New Roman");
            run15.setText("   -	Tỉ lệ hợp đồng nợ xấu: … %\n");
            run15.addBreak();

            XWPFParagraph paragraph16 = doc.createParagraph();
            paragraph16.setAlignment(ParagraphAlignment.RIGHT);
            paragraph16.setSpacingAfterLines(100);
            XWPFRun run16 = paragraph16.createRun();
            run16.setFontSize(13);
            run16.setFontFamily("Times New Roman");
            run16.setItalic(true);
            run16.setText("Hà nội, ngày … tháng … năm …  ");

            XWPFParagraph paragraph17 = doc.createParagraph();
            paragraph17.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run17 = paragraph17.createRun();
            run17.setFontSize(13);
            run17.setFontFamily("Times New Roman");
            run17.setText("Giám đốc Công ty\t\t");

            XWPFParagraph paragraph18 = doc.createParagraph();
            paragraph18.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run18 = paragraph18.createRun();
            run18.setFontSize(10);
            run18.setFontFamily("Times New Roman");
            run18.setItalic(true);
            run18.setText("(ký ghi rõ họ và tên)\t\t");

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            doc.write(b);
            // b.close();
            doc.close();
            return new ByteArrayInputStream(b.toByteArray());
        }

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
