package qv.co.framewbase.core.baserunners;

import org.junit.BeforeClass;
import qv.co.framewbase.core.librarycore.evidence.Evidence;
import qv.co.framewbase.core.librarycore.evidence.Reports;

public class BaseTestRunner {

    @BeforeClass
    public static void setUp() {
        String path = System.getProperty("user.dir");
        if (path.endsWith(".jar")) {
            Evidence.is_running_form = true;
            Reports.reportEvent(Reports.INFO, "Se esta ejecutando por JAR");
        } else
            Reports.reportEvent(Reports.INFO, "Se esta ejecutando por IDE");
    }
//
//
//    private static String TAG;
//
//
//    public static void main() {
//        TAG = JOptionPane.showInputDialog(null,
//                "Ingrese el tar que ejecutara:",
//                "Ejecutar pruebas por tag",
//                JOptionPane.QUESTION_MESSAGE);
//
//        if (TAG == null || TAG.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(null,
//                    "No se ingreso ningun tag",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
//        }
//
//        TAG = TAG.trim().startsWith("@") ? TAG : "@" + TAG;
//
//        if (!doesTagExist(TAG)) {
//            JOptionPane.showMessageDialog(null, "El tag '" + TAG + "' no existe, por favor verifique.",
//                    "Tag no encontrado",
//                    JOptionPane.ERROR_MESSAGE
//            );
//            Reports.reportEvent(Reports.FAILED, "El tag '" + TAG + "' no existe, por favor verifique.");
//            throw new IllegalArgumentException("El tag '" + TAG + "' no existe, por favor verifique.");
//        } else {
//            String project_path = System.getProperty("user.dir");
//            String command_excecution = "cmd.exe /c gradle clean test -Dcucumber.filter.tags=\"%s\"", TAG;
//
//            try {
//
//                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command_excecution);
//                processBuilder.directory(new File(project_path));
//                processBuilder.redirectErrorStream(true);
//
//                Process process = processBuilder.start();
//                process.getInputStream().transferTo(System.out);
//
//                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                }
//                int exit_code = process.waitFor();
//                if (exit_code == 0) JOptionPane.showMessageDialog(null, "Ejecucion completada exitosamente");
//                else
//                    JOptionPane.showMessageDialog(null, "Ocurrio un error durante la ejecicion, codigo de salida: " + exit_code);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar ejecutar el comando.\n" + e.getMessage());
//            }
//
//        }
//    }
//
//    private static boolean doesTagExist(String tag) {
//        try {
//            String featuresPath = "src/test/resources/features";
//
//            File folder = new File(featuresPath);
//            File[] files = folder.listFiles((dir, name) -> name.endsWith(".feature"));
//            if (files != null) {
//                for (File file : files) {
//                    List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
//
//                    for (String line : lines) {
//                        if (line.trim().equals("@" + tag) || line.trim().equals(tag)) {
//                            return true;
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Reports.reportEvent(Reports.FAILED, "No se encontro el tag ingresado");
//            throw new IllegalArgumentException("No se encontro el tag ingresado");
//        }
//        return false;
//    }
//
//    public static String getTag() {
//        return TAG;
//    }
}
