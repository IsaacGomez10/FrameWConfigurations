package com.co.airbnb.stepdefinitions.registro;

import com.co.airbnb.screenplay.task.registerSuccess.RegisterHome;
import io.cucumber.java.es.*;
import net.serenitybdd.screenplay.actors.OnStage;
import qv.co.framewbase.core.librarycore.evidence.Reports;

public class RegistroExitosoSD {

    @Entonces("carga el archivo excel datos_registro.xlsx de la fila {int}")
    public void cargaElArchivoExcelDatosRegistroXlsxDeLaFila(Integer int1) {
        Reports.reportEvent(Reports.INFO,"Se logro ingreser a entonces");
    }
    @Cuando("complete las caracteristicas de la reserva")
    public void completeLasCaracteristicasDeLaReserva() {
        OnStage.withCurrentActor(RegisterHome.registro());

    }
    @Entonces("obtendra un mensaje de registro exitoso")
    public void obtendraUnMensajeDeRegistroExitoso() {
        Reports.reportEvent(Reports.INFO,"Se logro ingreser a entonces");

    }

}
