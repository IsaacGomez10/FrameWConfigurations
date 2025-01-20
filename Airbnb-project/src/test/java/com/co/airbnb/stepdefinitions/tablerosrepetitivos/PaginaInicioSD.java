package com.co.airbnb.stepdefinitions.tablerosrepetitivos;

import com.co.airbnb.navigation.NavigateTo;
import io.cucumber.java.es.Dado;
import net.serenitybdd.screenplay.actors.OnStage;

public class PaginaInicioSD {
    @Dado("el usuario se encuentra en la página Airbnb")
    public void elUsuarioSeEncuentraEnLaPáginaAirbnb() {
        OnStage.theActorInTheSpotlight().wasAbleTo(NavigateTo.airbnbHomePage());
    }
}
