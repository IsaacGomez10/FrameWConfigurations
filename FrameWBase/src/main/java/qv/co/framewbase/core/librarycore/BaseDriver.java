package qv.co.framewbase.core.librarycore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.Map;

public class BaseDriver {
    public static boolean IGNORE_PREFS = false; // PONER EN TRUE SI SE DESEA IGNORAR LOS PREFERENCES
    private static String DOWNLOAD_PATH; // RUTA DONDE SE DESCARGARAN ARCHIVOS, SI ESTA VACIO LA PAGINA NO DESCARGARA ARCHIVOS

    private static WebDriver DRIVER;

    //NAVEGADORES QUE SE PUEDEN USAR PARA CARGAR PAGINAS WEB
    public static final String CHROME = "CHROME";
    private static final String MOBILE = "MOBILE";

    private String navegador;


    public static WebDriver driverConfiguration(String download_path) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        DOWNLOAD_PATH = download_path;
        // CONFIGURACION DE OPCIONES DE CHROME
        ChromeOptions options = new ChromeOptions(); //HEREDA DE MULTICAPABILITIES
        options.addArguments("--no-sandbox");// PERMITIR EJECICIONES DE FUENTES NO FIABLES: DEBE ESTAR DE PRIMERAS
        options.addArguments("safebrowsing.enabled=false");//DESACTIVAR LA PROTECCION DESCARGAS DE SITIOS NO SEGUROS
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true); //SITIOS NO SEGUROS
        // DESACTIVA EL BLOQUEO DE PANTALLAS EMERGENTES // "--disable-popup-blocking"
        options.addArguments("--disable-extensions", "--disable-print-preview");
        options.addArguments("--disabled-features=VizDisplayCompositor");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        // CONFIGURAR PREFERENCIAS Y CARGARLAS
        if (!IGNORE_PREFS) {
            Map<String, Object> prefs = new HashMap<String, Object>();
            if (!DOWNLOAD_PATH.isEmpty()) {
                // CARPTA DE DESCARGAS
                prefs.put("download.default_directory", DOWNLOAD_PATH);
                // APAGAR LA VENTANA QUE PREGUNTA DONDE UBICAR Y COMO LLAMAR EL ARCHIVO POR DESCARGAR
                prefs.put("download.prompt_for_download", false);
                // OPCION PARA QUE CHROME PUEDA CREAR LA RUTA DE DESCARGA SI ES QUE NO EXISTE
                prefs.put("download.directory_upgrade", false);
                // PARA EVITAR QUE SALGA EL POPUP CHROME CUANDO HAY DESCARGA DE MULTIPLES ARCHIVOS
                prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
            }
            //PARA QUE NO SAQUE PANTALLA PREGUNTANDO SI QUIERE GUARDAR CONTRASENA
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            // NAVEGASION SEGURA ACTIVA (OPCIONAL)
            prefs.put("safebrowsing.enabled", false);
            // PARA ABRIR PDFS EXTERNAMENTE: NO HACE DESCARGA
            prefs.put("plugin.always_open_pdf_externally", false);

            options.setExperimentalOption("prefs", prefs);
        }

        DRIVER = new ChromeDriver(options);
        return DRIVER;
    }

    public static WebDriver getDriver() {
        return DRIVER;
    }

}

