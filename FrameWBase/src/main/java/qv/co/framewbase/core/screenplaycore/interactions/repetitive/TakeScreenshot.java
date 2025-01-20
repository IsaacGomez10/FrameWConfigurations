package qv.co.framewbase.core.screenplaycore.interactions.repetitive;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import qv.co.framewbase.core.librarycore.evidence.Evidence;

public class TakeScreenshot implements Interaction {
    private final String name_evidence;

    public TakeScreenshot(String name_evidence) {
        this.name_evidence = name_evidence;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Evidence.save(name_evidence);
    }

    public static TakeScreenshot save(String name_evidence) {
        return Tasks.instrumented(TakeScreenshot.class, name_evidence);
    }
}
