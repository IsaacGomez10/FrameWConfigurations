package com.co.airbnb.navigation;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateTo {
    public static Performable airbnbHomePage() {
        return Task.where("{0} abrir Airbnb página de inicio",
                Open.browserOn().the(AirbnbHomePage.class));
    }
}
