package qv.co.framewbase.core.librarycore.evidence;

import org.apache.poi.xwpf.usermodel.*;
import qv.co.framewbase.core.librarycore.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WordEvidence implements EvidenceType {

    private static String template_path = "src/test/resources/templates/wordEvidence.docx";
    private static String final_dir = "Temp";
    private static Path results_path;
    private static XWPFDocument document;
    private static FileOutputStream fileOutputStream;
    private static String image_text = "";
    private static int index = 0;

    @Override
    public void createEvidence() {
        try (FileInputStream fileInputStream = new FileInputStream(template_path)) {
            if (Evidence.result_dir == null) {
                Evidence.result_dir = Paths.get(Evidence.evidence_path, "evidences");
                final_dir = Evidence.result_dir.toString();
            }

            results_path = Paths.get(final_dir, ".RESULTS");
            if (!Files.exists(results_path))
                Files.createDirectories(results_path);

            document = new XWPFDocument(fileInputStream);
            fileOutputStream = new FileOutputStream(results_path.toString() + "/final_test_evidence.docx");
            replaceTextInTemplate(document);
            document.write(fileOutputStream);
        } catch (Exception e) {
            Reports.reportEvent(Reports.FAILED, "No se logro construir la evidencia en word.");
            throw new IllegalArgumentException("No se logro construir la evidencia en word.");
        }
    }

    public static void addEvidence(String img_path) {
        try (FileInputStream fileInputStream = new FileInputStream(img_path)) {
            index += 1;
            int last_underscore = img_path.lastIndexOf('_');
            int dot_index = img_path.lastIndexOf('.');
            if (last_underscore != -1 && dot_index != -1 && last_underscore < dot_index)
                image_text = img_path.substring(last_underscore + 1, dot_index);

            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(index + " - " + image_text);
            run.setBold(true);
            run.setFontSize(12);

            try (FileInputStream imgInputStream = new FileInputStream(img_path)) {
                run.addPicture(
                        imgInputStream,
                        XWPFDocument.PICTURE_TYPE_PNG,
                        img_path,
                        550 * 9525,
                        250 * 9525
                );
            }
            paragraph.setSpacingAfter(150);
            run.addBreak();
            document.write(fileOutputStream);
        } catch (Exception e) {
            Reports.reportEvent(Reports.FAILED, "No se logró agregar la evidencia al documento.");
            throw new IllegalArgumentException("Error al agregar evidencia: " + img_path, e);
        }
    }

    private void replaceTextInTemplate(XWPFDocument document) {
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    String cell_text = cell.getText();
                    if (cell_text.contains("reemplazar1"))
                        cell.setText(cell_text.replace("reemplazar1", /*ExcelFile.getTestData("TestConfiguration")*/ "@Testconfig"));
                    if (cell_text.contains("reemplazar2"))
                        cell.setText(cell_text.replace("reemplazar2", /*ExcelFile.getTestData("Autor")*/ "@Testconfig"));
                    if (cell_text.contains("reemplazar3"))
                        cell.setText(cell_text.replace("reemplazar3", Util.currentDate()));
                }
            }
        }
    }
}
