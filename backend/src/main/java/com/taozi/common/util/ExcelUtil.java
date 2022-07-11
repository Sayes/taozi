package com.taozi.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ExcelUtil {
    private ExcelUtil() {
    }

    /**
     * 从classpath中读取Excel文件并转为workbook对象
     */
    public static Workbook readFromResource(String classpath) throws FileNotFoundException {
        ClassPathResource cpr = new ClassPathResource(classpath);
        if (!cpr.exists()) {
            throw new FileNotFoundException("Excel模板不存在");
        }
        try {
            if (classpath.endsWith(".xls")) {
                return new HSSFWorkbook(cpr.getInputStream());
            } else if (classpath.endsWith(".xlsx")) {
                return new XSSFWorkbook(cpr.getInputStream());
            }

            log.error("模板后缀名错误");
            throw new RuntimeException("模板后缀名错误");
        } catch (IOException e) {
            log.error("读取Excel模板异常", e);
            throw new RuntimeException("读取Excel模板失败");
        }
    }

    /**
     * 复制多个行到指定行，不覆盖，移动原来的行的位置
     */
    public static void copyRows(Sheet sheet, int startRow, int rowCount, int targetRow) {
        if (startRow < 0 || rowCount < 1 || targetRow < 0) {
            return;
        }
        // 先把目标位置下的所有行下移，给复制留出空间
        sheet.shiftRows(targetRow, sheet.getPhysicalNumberOfRows(), rowCount, true, true);
        // 如要把行往上方复制，shift后会把startRow往下推，这里要处理
        if (startRow > targetRow) {
            startRow += rowCount;
        }

        // 逐行复制
        for (int i = 0; i < rowCount; i++) {
            copyRow(sheet, startRow + i, targetRow + i);
        }
        copyMergedRegion(sheet, startRow, rowCount, targetRow);
    }

    /**
     * 复制行
     */
    public static void copyRow(Sheet sheet, int sourceRow, int targetRow) {
        Row sr = sheet.getRow(sourceRow);
        Row tr = sheet.getRow(targetRow);
        // 复制行高度
        tr.setHeightInPoints(sr.getHeightInPoints());

        for (short i = 0; i < sr.getLastCellNum(); i++) {
            Cell sc = sr.getCell(i);
            if (sc == null) {
                continue;
            }
            Cell tc = tr.getCell(i);
            if (tc == null) {
                tc = tr.createCell(i, sc.getCellTypeEnum());
            }
            // 复制单元格
            copyCell(sc, tc);
        }
    }

    /**
     * 复制单元格
     */
    public static void copyCell(Cell sourceCell, Cell targetCell) {
        CellStyle cs = sourceCell.getCellStyle();
        if (sourceCell.getRow().getSheet().getWorkbook() == targetCell.getRow().getSheet().getWorkbook()) {
            targetCell.setCellStyle(cs);
        } else {
            CellStyle ncs = targetCell.getRow().getSheet().getWorkbook().createCellStyle();
            ncs.cloneStyleFrom(cs);
            targetCell.setCellStyle(ncs);
        }
        if (sourceCell.getCellTypeEnum() != targetCell.getCellTypeEnum()) {
            return;
        }
        switch (sourceCell.getCellTypeEnum()) {
            case _NONE:
                break;
            case NUMERIC:
                targetCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case FORMULA:
                break;
            case BLANK:
                break;
            case BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case ERROR:
                targetCell.setCellValue(sourceCell.getErrorCellValue());
                break;
        }
    }

    /**
     * 复制合并的单元格
     */
    public static void copyMergedRegion(Sheet sheet, int startRow, int rowCount, int targetRow) {
        int moveRowSub = targetRow - startRow;
        List<CellRangeAddress> allMergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress region : allMergedRegions) {
            if (region.getFirstRow() < startRow || region.getLastRow() > startRow + rowCount - 1) {
                continue;
            }

            sheet.addMergedRegion(new CellRangeAddress(
                    region.getFirstRow() + moveRowSub,
                    region.getLastRow() + moveRowSub,
                    region.getFirstColumn(),
                    region.getLastColumn()));
        }
    }

    /**
     * 将数据写入指定的单元格
     */
    public static void writeVal2cell(Sheet sheet, int rowNum, int colNum, Object value) {
        if (sheet == null || rowNum < 0 || colNum < 0 || value == null) {
            return;
        }
        CellType cellType = CellType.STRING;

        if (Number.class.isAssignableFrom(value.getClass())) {
            cellType = CellType.NUMERIC;
        } else if (Boolean.class.isAssignableFrom(value.getClass())) {
            cellType = CellType.BOOLEAN;
        }

        //
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum, cellType);
        }
        switch (cellType) {
            case NUMERIC:
                cell.setCellValue(((Number) value).doubleValue());
                break;
            case STRING:
                cell.setCellValue(value.toString());
                break;
            case BOOLEAN:
                cell.setCellValue((Boolean) value);
                break;
        }
    }

    public static void deleteRows(Sheet sheet, int rowNum, int rowCount) {
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(rowNum + i);
            if (row == null) {
                continue;
            }
            sheet.removeRow(row);
        }
        List<Integer> willRemoveList = new LinkedList<>();
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();

        for (int i = 0, mergedRegionsSize = mergedRegions.size(); i < mergedRegionsSize; i++) {
            CellRangeAddress mergedRegion = mergedRegions.get(i);
            if (mergedRegion.getFirstRow() >= rowNum && mergedRegion.getLastRow() <= rowNum + rowCount) {
                willRemoveList.add(i);
            }
        }
        sheet.removeMergedRegions(willRemoveList);

        sheet.shiftRows(
                rowNum + rowCount,
                sheet.getLastRowNum(),
                rowCount * -1,
                true,
                true);
    }

    public static int appendSheetToAnotherSheet(Sheet sourceSheet, Sheet targetSheet) {
        // 这里是行数，有几行就是几，而下标是从0开始
        int targetSheetOriginalLastRowNum = targetSheet.getPhysicalNumberOfRows();

        Iterator<Row> ssRi = sourceSheet.rowIterator();
        int rowCount = 0;
        while (ssRi.hasNext()) {
            Row row = ssRi.next();
            Row createdRow = targetSheet.createRow(targetSheetOriginalLastRowNum + rowCount);
            rowCount++;
            // 复制高度
            createdRow.setHeightInPoints(row.getHeightInPoints());
            // 遍历单元格
            Iterator<Cell> rci = row.cellIterator();
            while (rci.hasNext()) {
                Cell cell = rci.next();
                Cell createdCell = createdRow.createCell(cell.getColumnIndex(), cell.getCellType());
                copyCell(cell, createdCell);
            }
        }
        // 复制合并单元格
        List<CellRangeAddress> mergedRegions = sourceSheet.getMergedRegions();
        for (CellRangeAddress mergedRegion : mergedRegions) {
            targetSheet.addMergedRegion(new CellRangeAddress(
                    mergedRegion.getFirstRow() + targetSheetOriginalLastRowNum,
                    mergedRegion.getLastRow() + targetSheetOriginalLastRowNum,
                    mergedRegion.getFirstColumn(),
                    mergedRegion.getLastColumn()));
        }
        return rowCount;
    }
}
