package com.co.airbnb.screenplay.userinterface.homepage;

import net.serenitybdd.screenplay.targets.Target;

public class HomePage {

    public static final Target BTN_PROFILE = Target.the("boton de perfil").locatedBy("//*[@data-tooltip-anchor-id='headernav-profile-button']");
    public static final Target BTN_REGISTER = Target.the("boton de registro").locatedBy("//*[text()='Sign up']");


}
