package com.example.demo.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import jakarta.servlet.http.HttpSession;

public class RevenueWordReporter {
    public static ByteArrayInputStream generateWord(HttpSession session) throws IOException, InvalidFormatException{
        try(XWPFDocument doc = new XWPFDocument()){
            Integer startDate = (Integer) session.getAttribute("startDate");
            Integer searchMonth = (Integer) session.getAttribute("searchMonth");
            Integer searchYear = (Integer) session.getAttribute("searchYear");
            /*
            session.setAttribute("unhandledRevenue", unhandledRevenue);
        session.setAttribute("processingRevenueSum", processingRevenueSum);
        session.setAttribute("gainedRevenueSum", gainedRevenueSum);
             */
            Long unhandledRevenue = (Long)session.getAttribute("unhandledRevenue");
            Long processingRevenueSum = (Long)session.getAttribute("processingRevenueSum");
            Long gainedRevenueSum = (Long)session.getAttribute("gainedRevenueSum");

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
            run5.setText("(V/v: Báo cáo doanh thu – Tháng … năm …)");
            
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
            run7.setText("Phòng tài chính gửi bảng thống kê lượng gói mạng đã bán trong tháng "+searchMonth+" năm "+searchYear+" như sau:");

            XWPFParagraph paragraph8 = doc.createParagraph();
            paragraph8.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run8 = paragraph8.createRun();
            run8.setFontSize(13);
            run8.setFontFamily("Times New Roman");
            run8.setBold(true);
            run8.setText("I.	Tình hình doanh thu của công ty kể từ Ngày "+startDate+" tháng "+searchMonth+" năm "+searchYear);

            XWPFParagraph paragraph14 = doc.createParagraph();
            paragraph14.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run14 = paragraph14.createRun();
            run14.setFontSize(13);
            run14.setFontFamily("Times New Roman");
            run14.setText("-	Tổng doanh thu dự kiến nhận được từ các hợp đồng: "+unhandledRevenue+" đồng");
            
            XWPFParagraph paragraph9 = doc.createParagraph();
            paragraph9.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run9 = paragraph9.createRun();
            run9.setFontSize(13);
            run9.setFontFamily("Times New Roman");
            run9.setText("-	Tổng doanh thu đã nhận từ các hợp đồng: "+gainedRevenueSum+" đồng");

            XWPFParagraph paragraph10 = doc.createParagraph();
            paragraph10.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run10 = paragraph10.createRun();
            run10.setFontSize(13);
            run10.setFontFamily("Times New Roman");
            run10.setText("-	Tổng doanh thu chưa nhận từ các hợp đông: "+processingRevenueSum+" đồng");

            XWPFParagraph paragraph11 = doc.createParagraph();
            paragraph11.setAlignment(ParagraphAlignment.RIGHT);
            paragraph11.setSpacingAfterLines(100);
            XWPFRun run11 = paragraph11.createRun();
            run11.setFontSize(13);
            run11.setFontFamily("Times New Roman");
            run11.setItalic(true);
            run11.setText("Hà nội, ngày … tháng … năm …  ");

            XWPFParagraph paragraph12 = doc.createParagraph();
            paragraph12.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run12 = paragraph12.createRun();
            run12.setFontSize(13);
            run12.setFontFamily("Times New Roman");
            run12.setText("Giám đốc Công ty\t\t");

            XWPFParagraph paragraph13 = doc.createParagraph();
            paragraph13.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run13 = paragraph13.createRun();
            run13.setFontSize(10);
            run13.setFontFamily("Times New Roman");
            run13.setItalic(true);
            run13.setText("(ký ghi rõ họ và tên)\t\t");

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            doc.write(b);
            // b.close();
            doc.close();
            return new ByteArrayInputStream(b.toByteArray());
        }
    }
}
