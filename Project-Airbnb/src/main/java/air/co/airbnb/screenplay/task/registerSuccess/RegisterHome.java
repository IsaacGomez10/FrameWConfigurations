package air.co.airbnb.screenplay.task.registerSuccess;

import air.co.airbnb.screenplay.userinterface.homepage.HomePage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import qv.co.framewbase.core.screenplaycore.interactions.repetitive.TakeScreenshot;
import qv.co.framewbase.core.screenplaycore.interactions.repetitive.WaitElement;

public class RegisterHome implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                TakeScreenshot.save("PaginaDeInicio"),
                Click.on(HomePage.BTN_PROFILE),
                WaitElement.untilAppears(HomePage.BTN_REGISTER),
                JavaScriptClick.on(HomePage.BTN_REGISTER),
                TakeScreenshot.save("PopupDeRegistro")
        );
    }

    public static RegisterHome registro(){
        return new RegisterHome();
    }
}
