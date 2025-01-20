package qv.co.framewbase.core.librarycore.evidence;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reports {
    private static PrintWriter WRITER;
    //private static String TIMES_STAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private static final String LOG_FILE_NAME = "Console_log.log";

    //DEFINICION DE CONSTANTES PARA LOS ESTADOS DE LOG
    public static final int PASSED = 0;
    public static final int FAILED = 1;
    public static final int DONE = 2;
    public static final int WARNING = 3;
    public static final int NOEXEC = 4;
    public static final int INFO = 5;
    public static final int NOT_COMPLETED = 6;

    //EL NOMBRE DEL EVENT STATUS COINCIDE CON LA POSICIÓN A LA QUE CORRESPONDE EL ARRAY
    private static final String[] ARR_EVENT_STATUS = {
            "PASSED", "FAILED", "DONE", "WARNING", "NO EXEC", "INFO", "NOT COMPLETE"
    };

    /**
     * Método para escribir sólo en la consola un mensaje - este NO se escribe en el archivo LOG
     */
    public static void onlyWriteConsole(String msg) {

    }

    /**
     * INICIALIZA EL NUEVO ARCHIVO DE LOG
     *
     * @param ruta_directorio
     */
    public static void initializeLog(String ruta_directorio) {
        try {
            File log_file = new File(ruta_directorio + File.separator + LOG_FILE_NAME);
            if (!log_file.exists()) {
                log_file.createNewFile(); // CREA EL ARCHIVO NO EXISTENTE
            }
            WRITER = new PrintWriter(new FileWriter(log_file, true)); //ESCRIBE EN EL ARCHIVO DE LOG
        } catch (Exception e) {
            System.err.println("Error al inicializar el archivo de log: " + e.getMessage());
        }
    }

    /**
     * Permite realizar el reporte del evento [estado_log] en la consola y en archivo del LOG.<br>
     *
     * @param estado_log
     * @param mensaje
     */
    public static void reportEvent(int estado_log, String mensaje) {
        String string_estado = getStatus(estado_log);

        String mensaje_log = String.format("[%s] [%s] ----------- %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), string_estado, mensaje);

        //IMPRIMIR EN CONSOLA CON FORMATO SEGUN EL ESTADO
        if (estado_log == FAILED) {
            System.err.println("\u001B[31m" + mensaje_log + "\u001B[0m"); // MENSAJE ROJO PARA FAILED
        } else {
            System.out.println(mensaje_log);
        }

        if (WRITER != null) {
            WRITER.println(mensaje_log);
            WRITER.flush();
        }
    }

    /**
     * CONVERTIRA EL ESTADO DE ENTERO A TEXTO
     *
     * @param estado_log
     * @return
     */
    private static String getStatus(int estado_log) {
        String string_estado = null;

        switch (estado_log) {
            case NOEXEC:
            case NOT_COMPLETED:
            case INFO:
                string_estado = "INFO";
                break;
            case PASSED:
                string_estado = "PASSED";
                break;
            case FAILED:
                string_estado = "FAILED";
                break;
            case WARNING:
                string_estado = "WARNING";
                break;
            case DONE:
                string_estado = "DONE";
                break;
            default:
                string_estado = "UNKNOWN";
        }

        return string_estado;
    }

    /**
     * Este método se llamará después de ejecutar cada test y reportará su resultado.
     *
     * @param resultadoPrueba El resultado de la prueba (PASSED, FAILED, SKIPPED, etc.)
     */
    public static void reportTestResult(int resultadoPrueba) {
        String mensaje = "Resultado de la prueba: " + getStatus(resultadoPrueba);
        reportEvent(resultadoPrueba, mensaje);
    }

    private int convertScenarioStatusToResult(String scenarioStatus) {
        switch (scenarioStatus) {
            case "PASSED":
                return Reports.PASSED;
            case "FAILED":
                return Reports.FAILED;
            case "SKIPPED":
                return Reports.WARNING;
            case "UNDEFINED":
                return Reports.NOEXEC;
            default:
                return Reports.INFO;
        }
    }

    public static void closeReports() {
        if (WRITER != null)
            WRITER.close();
    }
}
