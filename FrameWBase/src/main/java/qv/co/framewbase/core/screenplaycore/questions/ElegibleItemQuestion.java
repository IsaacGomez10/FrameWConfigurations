package qv.co.framewbase.core.screenplaycore.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import net.serenitybdd.screenplay.targets.Target;

public class ElegibleItemQuestion implements Question<Boolean> {
    private final Target element;

    public ElegibleItemQuestion(Target element) {
        this.element = element;
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return Visibility.of(element).answeredBy(actor);
    }

    public static ElegibleItemQuestion to(Target element){
        return new ElegibleItemQuestion(element);
    }
}
