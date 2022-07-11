package com.taozi.common.excel;

import org.apache.poi.ss.usermodel.Workbook;

@FunctionalInterface
public interface ExcelInterceptor {

    String FLAG = "EXCEL_INTERCEPTOR";

    void doAfterExcelRendered(Workbook wb);
}
