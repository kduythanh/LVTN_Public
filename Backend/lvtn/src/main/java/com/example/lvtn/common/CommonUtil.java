package com.example.lvtn.common;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;

@Component
public class CommonUtil {

    public String generateRandomPassword(int length) {
        final String CHARACTERS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public String convertBooleanToString(Boolean value) {
        return value ? "x" : "";
    }

    public Boolean convertStringToBoolean(String value) {
        if (value == null || value.isEmpty()) return false;
        return value.equalsIgnoreCase("x");
    }

    public String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                    } else {
                        // chuyển số sang chuỗi, tránh lỗi "101.0"
                        BigDecimal num = BigDecimal.valueOf(cell.getNumericCellValue());
                        return num.stripTrailingZeros().toPlainString();
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    // Lấy giá trị đã tính của công thức (thay vì chuỗi công thức)
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue evaluated = evaluator.evaluate(cell);
                    if (evaluated == null) return null;
                    return switch (evaluated.getCellType()) {
                        case STRING -> evaluated.getStringValue().trim();
                        case NUMERIC ->
                                BigDecimal.valueOf(evaluated.getNumberValue()).stripTrailingZeros().toPlainString();
                        case BOOLEAN -> String.valueOf(evaluated.getBooleanValue());
                        default -> null;
                    };
                case BLANK:
                default:
                    return null;
            }
        } catch (Exception e) {
            return null; // Tránh ném lỗi nếu kiểu không khớp
        }
    }

    public LocalDate getCellValueAsDate(Cell cell) {
        if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        return null;
    }

    public Integer getCellValueAsInt(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return null;
    }

    public BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        }
        return null;
    }

    public void setCellValueAsDecimal(Row row, int columnIndex, BigDecimal value, Workbook workbook) {
        Cell cell = row.createCell(columnIndex);
        if (value != null) {
            cell.setCellValue(value.doubleValue());
            CellStyle style = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat("0.00"));
            cell.setCellStyle(style);
        } else {
            cell.setBlank();
        }
    }


    public void validateDiem(BigDecimal diem, String mon) {
        if (diem == null) return;
        if (diem.compareTo(BigDecimal.ZERO) < 0 || diem.compareTo(BigDecimal.TEN) > 0) {
            throw new IllegalArgumentException("Điểm " + mon + " phải từ 0.00 đến 10.00!");
        }
    }

    public CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Arial");
        style.setFont(font);

        return style;
    }


}
