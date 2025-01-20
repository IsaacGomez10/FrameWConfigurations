package qv.co.framewbase.core.basestepdefinitions;

import io.cucumber.java.Scenario;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import org.openqa.selenium.WebDriver;
import qv.co.framewbase.core.librarycore.BaseDriver;
import qv.co.framewbase.core.librarycore.evidence.Evidence;
import qv.co.framewbase.core.librarycore.evidence.Reports;
import qv.co.framewbase.core.librarycore.evidence.WordEvidence;

public class BaseHooks {
    @Managed
    private static WebDriver pv_driver;
    private static Scenario pv_scenario;

    public static void before(Scenario scenario) {
        try {
            // NOMBRE DE LA CARPETA DE EVIDENCIAS
            Evidence.scenario = "Escenario - " + scenario.getName();
            pv_scenario = scenario;
            // RUTA DONDE SE ALMACENARA LA INFORMACION DE LA EJECUCION
            Evidence.evidence_path = Evidence.pathEvidence(Evidence.scenario);
            // INICIALIZA EL USO DE EL LOG
            Reports.initializeLog(Evidence.evidence_path);
            Evidence.configurarRutaReporte(Evidence.evidence_path);
            // INICIALIZA EL DRIVER CON CONFIGURACIONES PERSONALIZADAS
            pv_driver = BaseDriver.driverConfiguration(Evidence.evidence_path);
            // INICIALIZA LAS EVIDENCIAS - ESTE VA EN EL HOOKS DEL PROYECTO QUE HACE USO DE LA LIBRERIA
            Evidence.actiate();
            Evidence.initializeEvidence(new WordEvidence());
            //INICIALIZA EL ACTOR
            OnStage.setTheStage(Cast.ofStandardActors());
            OnStage.theActorCalled("User");
            OnStage.theActorInTheSpotlight().can(BrowseTheWeb.with(pv_driver));
        } catch (Exception e) {
            Reports.reportEvent(Reports.FAILED, "No se logro configurar el driver, contacte al equipo de automatización.");
            throw new IllegalArgumentException("No se logro configurar el driver, contacte al equipo de automatización.");
        }
    }

    public static void after() {
        String test_result = pv_scenario.getStatus().toString();
        Reports.reportEvent(Reports.INFO, "Final Ejecucion: " + test_result);
        Reports.closeReports();
        for (String pats : Evidence.getImgPaths()) {
            Reports.reportEvent(Reports.DONE, "Evidencias: " + pats);
        }
        if (pv_driver != null) {
            pv_driver.quit();
        }
    }
}
