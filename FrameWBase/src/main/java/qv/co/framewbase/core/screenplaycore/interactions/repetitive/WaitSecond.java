package qv.co.framewbase.core.screenplaycore.interactions.repetitive;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import qv.co.framewbase.core.librarycore.Util;

public class WaitSecond implements Interaction {
    private final int SECONDS;

    public WaitSecond(int SECONDS) {
        this.SECONDS = SECONDS;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        new Util(SECONDS);
    }

    public static WaitSecond wait(int seconds){
        return Tasks.instrumented(WaitSecond.class, seconds);
    }
}
