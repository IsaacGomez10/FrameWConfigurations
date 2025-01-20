package qv.co.framewbase.core.screenplaycore.interactions.repetitive;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.targets.Target;
import qv.co.framewbase.core.screenplaycore.questions.ElegibleItemQuestion;

public class VisibleElement implements Interaction {
    private final Target element;

    public VisibleElement(Target element) {
        this.element = element;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (actor.asksFor(ElegibleItemQuestion.to(element)))
            actor.attemptsTo(WaitElement.untilBeEnable(element));
    }

    public static VisibleElement see(Target element) {
        return Tasks.instrumented(VisibleElement.class, element);
    }
}
