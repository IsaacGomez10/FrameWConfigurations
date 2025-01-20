package qv.co.framewbase.core.screenplaycore.interactions.repetitive;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.targets.Target;

public class GetTextOfElement implements Interaction {
    private final Target element;
    private static String text;

    public GetTextOfElement(Target element) {
        this.element = element;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        text = element.resolveFor(actor).getText();
    }

    public static String text(){
        return text;
    }
    public static GetTextOfElement of(Target element){
        return Tasks.instrumented(GetTextOfElement.class, element);
    }
}
