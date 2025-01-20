package qv.co.framewbase.core.librarycore.evidence;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import qv.co.framewbase.core.librarycore.BaseDriver;
import qv.co.framewbase.core.librarycore.Util;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Evidence {
    public static String scenario = "Temp";
    public static String evidence_path = "Temp";

    public static String DOWNLOAD_PATH = "Temp";
    private static int num_excec = 1;
    public static boolean is_running_form = false;
    public static Path result_dir = null;
    private static boolean do_evidence = false;

    private static String last_prefix = "";
    private static int consecutive;
    public static List<String> screenshots_path = null;

    private static EvidenceType evidence;

    public static void initializeEvidence(EvidenceType evidenceType) {
        evidence = evidenceType;
        evidence.createEvidence();
    }

    public static String pathEvidence(String escenario) throws Exception {
        // CREA UNA NUEVA CARPETA INICIAL DE EVIDENCIAS
        String inicio_usuario = System.getProperty("user.home");
        Path carpeta_evidencias = Paths.get(inicio_usuario, ".EVIDENCIAS");
        if (!Files.exists(carpeta_evidencias))
            Files.createDirectory(carpeta_evidencias);

        // CREA LA CARPETA DEL STEP QUE SE VA A EJECUTAR
        Path carpeta_modulo = carpeta_evidencias.resolve(escenario);
        if (!Files.exists(carpeta_modulo))
            Files.createDirectory(carpeta_modulo);

        // CREA LA CARPETA EN SECUENCIA POR EJECUCION POR MODULO, AQUI SE ESPERA GUARDAR ALGUN TIPO DE EVIDENCIA
        Path excecution_folder;
        do {
            excecution_folder = carpeta_modulo.resolve("Ejecucion " + num_excec);
            num_excec++;
        } while (Files.exists(excecution_folder));
        Files.createDirectory(excecution_folder);
        return excecution_folder.toAbsolutePath().toString();
    }

    public static void configurarRutaReporte(String nueva_ruta_reporte) {
        System.setProperty("serenity.outputDirectory", nueva_ruta_reporte);
    }

    // ABRE LA CARPETA CREADA DURANTE LA EJECUCION
    public static void openPath() {
        try {
            Desktop.getDesktop().open(new File(evidence_path));
            Reports.reportEvent(Reports.INFO, "Evidencias almacenadas en: " + evidence_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actiate() {
        Evidence.do_evidence = true;
    }

    public static void desactiate() {
        Evidence.do_evidence = false;
    }

    public static String save(String name_evidence) {
        File screenshot_file = null;
        if (!Evidence.do_evidence) return "";
        try {
            screenshot_file = new File(makeNameOfEvidence(name_evidence));
            File src_pic = ((TakesScreenshot) BaseDriver.getDriver()).getScreenshotAs(OutputType.FILE);
            Files.copy(src_pic.toPath(), screenshot_file.toPath());
            WordEvidence.addEvidence(screenshot_file.getPath().toString());
        } catch (Exception e) {
            Reports.reportEvent(Reports.FAILED, "No se logro tomar la captura,contacte al equipo de automatización.");
            throw new IllegalArgumentException("No se logro tomar la captura,contacte al equipo de automatización.");
        }
        return screenshot_file.getPath().toString();
    }

    public static void setEvidence(String img_path) {
        File directory = new File(img_path);
        if (directory.exists() && directory.isDirectory()) {
            if (directory.isFile() && isImageFile(directory)) WordEvidence.addEvidence(img_path);
        }
    }

    public static List<String> getImgPaths() {
        List<String> screenshots_path = new ArrayList<>();
        File directory = new File(Evidence.result_dir.toString());

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) screenshots_path.add(file.getAbsolutePath());
            }
        }
        return screenshots_path;
    }

    private static boolean isImageFile(File file) {
        String[] image_extensions = {".png", ".jpg", ".jpeg", ".gif"};
        String file_name = file.getName().toLowerCase();

        for (String ext : image_extensions) {
            if (file_name.endsWith(ext)) return true;
        }

        return false;
    }

    public static String saveFull() {
        File src_pic = null;
        if (!Evidence.do_evidence) return "";

        return null;
    }

    private static String makeNameOfEvidence(String name_evidence) {
        String final_name = Util.cleanTextFromFile(name_evidence);
        return (startEvidenceName() + final_name + ".png");
    }

    public static String startEvidenceName() {
        String evidence_dir;
        if (Evidence.result_dir == null) {
            evidence_dir = Evidence.evidence_path + "/evidences";
        } else
            evidence_dir = Evidence.result_dir.toString();

        String current_prefix = Util.nowDate() + "_" + Util.currentTime();

        if (last_prefix.equals(current_prefix))
            consecutive = 0;
        last_prefix = current_prefix;
        return (evidence_dir + System.getProperty("file.separator") + current_prefix + "_" + (consecutive++) + "_");
    }
}
