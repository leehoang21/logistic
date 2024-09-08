package com.octl2.api.commons.utils;

import com.octl2.api.dto.LocationDTO;
import com.octl2.api.dto.LocationLogisticDTO;
import com.octl2.api.dto.LocationType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ExportExcelUtil {
    private final XSSFWorkbook workbook;

    public ExportExcelUtil() {
        workbook = new XSSFWorkbook();
    }


    private CellStyle createHeaderStyle() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createCellStyle() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        return style;
    }


    private void writeLine(List<String> columns, int rowNum, CellStyle style, XSSFSheet sheet) {
        Row row = sheet.createRow(rowNum);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns.get(i));
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }
    }

    private List<String> getHeaderLocationColumns() {
        List<String> columnHeaders = new ArrayList<>();
        columnHeaders.add("ID ");
        columnHeaders.add("Tên ");
        columnHeaders.add("Tên ngắn ");
        columnHeaders.add("Mã ");
        columnHeaders.add("Mô tả ");
        return columnHeaders;
    }

    private List<String> getHeaderLogisticColumns() {
        List<String> columnHeaders = getHeaderLocationColumns();
        columnHeaders.add("FFM ID ");
        columnHeaders.add("FFM name ");
        columnHeaders.add("Lastmile ID ");
        columnHeaders.add("Lastmile name ");
        columnHeaders.add("Warehouse ID ");
        columnHeaders.add("Warehouse name ");
        columnHeaders.add("Warehouse Contact Name ");
        columnHeaders.add("Warehouse Contact Phone ");
        columnHeaders.add("Warehouse Address ");
        columnHeaders.add("Warehouse Full Address ");
        return columnHeaders;
    }

    private List<String> getLocationColumns(LocationDTO location) {
        if (ObjectUtils.isEmpty(location)) {
            return List.of("", "", "", "", "");
        }
        List<String> columns = new ArrayList<>();
        columns.add(location.getId().toString());
        columns.add(location.getName());
        columns.add(location.getShortname());
        columns.add(location.getCode());
        columns.add(location.getDescription());
        return columns;
    }

    private List<List<String>> getLogisticColumns(LocationLogisticDTO location) {
        List<List<String>> columns = new ArrayList<>();

        for (int i = 0; i < location.getLogistic().getFulfilments().size(); i++) {
            List<String> column = new ArrayList<>();
            if (ObjectUtils.isEmpty(location.getLogistic().getFulfilments().get(i))) {
                column.add("");
                column.add("");
            } else {
                column.add(location.getLogistic().getFulfilments().get(i).getId().toString());
                column.add(location.getLogistic().getFulfilments().get(i).getName());
            }
            if (ObjectUtils.isEmpty(location.getLogistic().getLastmiles().get(i))) {
                column.add("");
                column.add("");
            } else {
                column.add(location.getLogistic().getLastmiles().get(i).getId().toString());
                column.add(location.getLogistic().getLastmiles().get(i).getName());
            }

            if (ObjectUtils.isEmpty(location.getLogistic().getWarehouses().get(i))) {
                column.add("");
                column.add("");
                column.add("");
                column.add("");
                column.add("");
            } else {
                column.add(location.getLogistic().getWarehouses().get(i).getId().toString());
                column.add(location.getLogistic().getWarehouses().get(i).getName());
                column.add(location.getLogistic().getWarehouses().get(i).getContactName());
                column.add(location.getLogistic().getWarehouses().get(i).getContactPhone());
                column.add(location.getLogistic().getWarehouses().get(i).getAddress());
                column.add(location.getLogistic().getWarehouses().get(i).getFullAddress());
            }
            columns.add(column);
        }

        return columns;
    }

    private void loadParentData(List<LocationDTO> parents, LocationType locationType) {
        if (ObjectUtils.isEmpty(parents)) {
            return;
        }
        if (locationType == LocationType.DISTRICT && parents.size() == 1) {
            XSSFSheet districtSheet = workbook.createSheet("Province");
            writeLine(getHeaderLocationColumns(), 0, createHeaderStyle(), districtSheet);

            List<String> columns = getLocationColumns(parents.get(0));
            writeLine(columns, 1, createCellStyle(), districtSheet);

        } else if (locationType == LocationType.SUBDISTRICT && parents.size() == 2) {
            XSSFSheet provinceSheet = workbook.createSheet("Province");
            writeLine(getHeaderLocationColumns(), 0, createHeaderStyle(), provinceSheet);
            writeLine(getLocationColumns(parents.get(0)), 1, createCellStyle(), provinceSheet);

            XSSFSheet districtSheet = workbook.createSheet("District");
            writeLine(getHeaderLocationColumns(), 0, createHeaderStyle(), districtSheet);
            writeLine(getLocationColumns(parents.get(1)), 1, createCellStyle(), districtSheet);
        }

    }


    public void loadData(List<LocationLogisticDTO> logistics, List<LocationDTO> parents) {
        if (ObjectUtils.isEmpty(logistics)) {
            return;
        }

        loadParentData(parents, logistics.get(0).getLocationType());
        XSSFSheet sheet = workbook.createSheet("Logistics của " + logistics.get(0).getLocationType().name().toLowerCase());


        int rowNum = 0;
        writeLine(getHeaderLogisticColumns(), rowNum++, createHeaderStyle(), sheet);

        for (LocationLogisticDTO logistic : logistics) {
            List<String> column = new ArrayList<>(getLocationColumns(logistic.getLocation()));

            for (List<String> logisticColumn : getLogisticColumns(logistic)) {
                logisticColumn.addAll(0, column);
                writeLine(logisticColumn, rowNum++, createCellStyle(), sheet);
            }

        }

    }

    public Workbook export() {
        return workbook;
    }

}
