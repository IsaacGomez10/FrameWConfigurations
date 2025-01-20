package qv.co.framewbase.core.screenplaycore.interactions.repetitive;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.targets.Target;
import qv.co.framewbase.core.librarycore.evidence.Reports;
import qv.co.framewbase.core.screenplaycore.questions.ElegibleItemQuestion;

public class VisibleButton implements Interaction {
    private final Target element;

    public VisibleButton(Target element) {
        this.element = element;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (actor.asksFor(ElegibleItemQuestion.to(element)))
            element.resolveFor(actor).click();
        else
            Reports.reportEvent(Reports.WARNING, "No se encontro elemento para interactuar");
    }

    public static VisibleButton see(Target element) {
        return Tasks.instrumented(VisibleButton.class, element);
    }
}
