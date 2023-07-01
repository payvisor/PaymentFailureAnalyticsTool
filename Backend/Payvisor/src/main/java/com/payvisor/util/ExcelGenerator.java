package com.payvisor.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.payvisor.model.PaymentFailuresData;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ExcelGenerator {

    private List <PaymentFailuresData> paymentFailuresData;
    private SXSSFWorkbook workbook;
    private SXSSFSheet sheet;

    public ExcelGenerator(List <PaymentFailuresData> paymentFailuresData) {
        this.paymentFailuresData = paymentFailuresData;
        workbook = new SXSSFWorkbook();
        workbook.setCompressTempFiles(true);
    }
    
    private void writeHeader() {
        sheet = (SXSSFSheet) workbook.createSheet("Payment Failures Data");
        sheet.setRandomAccessWindowSize(100);
        sheet.trackAllColumnsForAutoSizing();
        
        Row row = sheet.createRow(0);
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        
        createCell(row, 0, "Transaction ID", style);
        createCell(row, 1, "First Name", style);
        createCell(row, 2, "Last Name", style);
        createCell(row, 3, "Email ID", style);
        createCell(row, 4, "Country", style);
        createCell(row, 5, "Amount", style);
        createCell(row, 6, "Currency", style);
        createCell(row, 7, "Payment Method", style);
        createCell(row, 8, "Payment Location", style);
        createCell(row, 9, "Failure Reason", style);
        createCell(row, 10, "Bucket Name", style);
        createCell(row, 11, "Transaction Date & Time", style);
        
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        if (!paymentFailuresData.isEmpty()) {
            for (PaymentFailuresData record : paymentFailuresData) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                createCell(row, columnCount++, record.getTransactionID(), style);
                createCell(row, columnCount++, record.getFirstName(), style);
                createCell(row, columnCount++, record.getLastName(), style);
                createCell(row, columnCount++, record.getEmailID(), style);
                createCell(row, columnCount++, record.getCountry(), style);
                createCell(row, columnCount++, Float.toString(record.getAmount()), style);
                createCell(row, columnCount++, record.getCurrency(), style);
                createCell(row, columnCount++, record.getPaymentMethod(), style);
                createCell(row, columnCount++, record.getPaymentLocation(), style);
                createCell(row, columnCount++, record.getFailureReason(), style);
                createCell(row, columnCount++, record.getBucketName(), style);
                createCell(row, columnCount++, record.getTransactionDateTime(), style);
            }
        }

        for (int i = 0; i < 12; i++) {
            sheet.autoSizeColumn(i,true);
        }
    }

    
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (valueOfCell == null) {
            cell.setCellValue("");
        } else if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Date) {
            cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(valueOfCell));
        } else if (valueOfCell instanceof Float) {
            cell.setCellValue((Double) valueOfCell);
        } else if (valueOfCell instanceof Boolean) {
            cell.setCellValue((Boolean) valueOfCell);
        } else {
            cell.setCellValue("");
        }
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        cell.setCellStyle(style);
    }
    
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}