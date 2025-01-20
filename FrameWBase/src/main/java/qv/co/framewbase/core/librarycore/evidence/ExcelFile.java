package qv.co.framewbase.core.librarycore.evidence;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExcelFile {
    private static Map<String, String> get_test_data;
    private static Map<String, String> set_test_data;

    private static int row = 0;
    private static File duplicated_file = null;

    public ExcelFile(String file_path, int row_number) {
        get_test_data = readOriginalExcelData(getExcelFile(file_path), row_number);
    }
    public ExcelFile(String parameter, String value) {
        setParameter(parameter, value);
    }

    public static File getExcelFile(String file_name) {
        Path file_path = Paths.get("src", "test", "resources", "testdata", file_name);
        File EXCEL_FILE = file_path.toFile();
        if (!EXCEL_FILE.exists() && !file_name.toLowerCase().endsWith(".xlsx")) {
            Reports.reportEvent(Reports.FAILED, "El archivo no se encontró en la ruta especificada" + file_path);
            throw new IllegalArgumentException("El archivo no se encontró en la ruta especificada" + file_path);
        }
        try {
            Files.copy(file_path, Paths.get(Evidence.evidence_path + "/" + file_name), StandardCopyOption.REPLACE_EXISTING);
            duplicated_file = Paths.get(Evidence.evidence_path + "/" + file_name).toFile();
        } catch (IOException e) {
            Reports.reportEvent(Reports.FAILED, "No se pudo realizar una copia del documento original, contacte al equipo de automatización.");
            throw new IllegalArgumentException("NO se pudo realizar una copia del documento original, contacte al equipo de automatización.");
        }
        return EXCEL_FILE;
    }


    private Map<String, String> readOriginalExcelData(File file, int row_number) {
        Map<String, String> data = new HashMap<>();

        if (row_number < 2) {
            Reports.reportEvent(Reports.FAILED, "La fila ingresada es el encabezado, por favor indique la fila donde se encuentra la data.");
            throw new IllegalArgumentException("La fila ingresada es el encabezado, por favor indique la fila donde se encuentra la data.");
        }
        row = row_number - 1;

        try (FileInputStream fileInputStream = new FileInputStream(file)) {

            Evidence.result_dir = Paths.get(Evidence.evidence_path + "/row_" + row_number);
            Files.createDirectory(Evidence.result_dir);

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row header_row = sheet.getRow(0);
            Row data_row = sheet.getRow(row);

            if (data_row == null) {
                Reports.reportEvent(Reports.FAILED, "No se encontró data en la fila especificada en el archivo.");
                throw new IllegalArgumentException("No se encontró data en la fila especificada en el archivo.");
            }

            for (int i = 0; i < header_row.getPhysicalNumberOfCells(); i++) {
                Cell header_cell = header_row.getCell(i);

                if (header_cell == null && header_cell.getStringCellValue().isEmpty()) {
                    Reports.reportEvent(Reports.FAILED, "El encabezado de la columna " + (i + 1) + " está vacío, por favor ingrese información.");
                    throw new IllegalArgumentException("El encabezado de la columna " + (i + 1) + " está vacío, por favor ingrese información.");
                }

                String header = header_cell.getStringCellValue().trim();
                Cell data_cell = data_row.getCell(i);
                if (data_cell != null) {
                    data.put(header, getCellValueAsString(data_cell));
                } else {
                    data.put(header, "");
                }

            }
        } catch (Exception e) {
            Reports.reportEvent(Reports.FAILED, "No se encontró data en la fila especificada en el archivo.");
            throw new IllegalArgumentException("No se encontró data en la fila especificada en el archivo.");
        }
        return data;
    }

    public static void setHeaders(String[] headers) {
        readDuplicateExcelData(duplicated_file, headers);
    }

    private static Set<String> readDuplicateExcelData(File file, String... headers) {
        Set<String> header_set = new HashSet<>();
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        if (!file.exists() && !file.getName().toLowerCase().endsWith(".xlsx")) {
            Reports.reportEvent(Reports.FAILED, "El archivo no se encontró en la ruta especificada" + file.getPath());
            throw new IllegalArgumentException("El archivo no se encontró en la ruta especificada" + file.getPath());
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            workbook = new XSSFWorkbook(fileInputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Row header_row = sheet.getRow(0);
            int start_column_index = header_row.getLastCellNum();
            if (start_column_index < 0) start_column_index = 0;
            start_column_index += 2;

            for (int i = 0; i < headers.length; i++) {
                Cell new_cell = header_row.createCell(start_column_index + i);
                new_cell.setCellValue(headers[i].toUpperCase());

                CellStyle header_style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                font.setColor(IndexedColors.WHITE.getIndex());
                header_style.setFont(font);

                header_style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
                header_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                new_cell.setCellStyle(header_style);

                sheet.autoSizeColumn(start_column_index + i);
                header_set.add(headers[i]);
            }

            fileInputStream.close();
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            Reports.reportEvent(Reports.FAILED, "No se logro duplicar el archivo, contecte al equipo de automatización.");
            throw new IllegalArgumentException("No se logro duplicar el archivo, contecte al equipo de automatización.");
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                Reports.reportEvent(Reports.FAILED, "No se pudo cerrar el libro, contacte al equipo de automatización.");
                throw new IllegalArgumentException("No se pudo cerrar el libro, contacte al equipo de automatización.");
            }
        }

        return header_set;
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double number = Double.parseDouble(String.valueOf(cell.getNumericCellValue()));
                return String.valueOf((int) number);
            default:
                return "";
        }
    }

    public static String getTestData(String column_name) {
        if (!get_test_data.containsKey(column_name)) {
            Reports.reportEvent(Reports.FAILED, "El encabezado " + column_name + " no existe en el archivo excel.");
            throw new IllegalArgumentException("El encabezado " + column_name + " no existe en el archivo excel.");
        }
        return get_test_data.get(column_name);
    }

    public static void setParameter(String parameter, String value) {
        try (FileInputStream fileInputStream = new FileInputStream(duplicated_file)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Row header_row = sheet.getRow(0);
            int column_index = -1;

            for (Cell cell : header_row) {
                if (cell.getStringCellValue().equalsIgnoreCase(parameter.toUpperCase().trim())) {
                    column_index = cell.getColumnIndex();
                    break;
                }
            }

            if (column_index == -1) {
                Reports.reportEvent(Reports.FAILED, "El encabezado " + parameter + " no existe en el archivo excel.");
                throw new IllegalArgumentException("El encabezado " + parameter + " no existe en el archivo excel.");
            }

            Row data_row = sheet.getRow(row);
            if (data_row == null) data_row = sheet.createRow(row);
            Cell cell = data_row.createCell(column_index);
            cell.setCellValue(value.trim());

            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(duplicated_file);
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            Reports.reportEvent(Reports.FAILED, "No se seteo el dato en el arcivo, contacte al equipo de automatización.");
            throw new IllegalArgumentException("No se seteo el dato en el arcivo, contacte al equipo de automatización.");
        }
    }

}
