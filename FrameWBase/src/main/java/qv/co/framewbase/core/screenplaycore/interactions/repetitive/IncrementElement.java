package qv.co.framewbase.core.screenplaycore.interactions.repetitive;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.targets.Target;
import qv.co.framewbase.core.librarycore.Util;

public class IncrementElement implements Interaction {
    private final Target QUANTITY;
    private final Target INCREMENT;
    private final String SET_QUANTITY;

    public IncrementElement(Target QUANTITY, Target INCREMENT, String SET_QUANTITY) {
        this.QUANTITY = QUANTITY;
        this.INCREMENT = INCREMENT;
        this.SET_QUANTITY = SET_QUANTITY;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        int cantidad = Integer.parseInt(QUANTITY.resolveFor(actor).getText());
        int set_cantidad = Integer.parseInt(SET_QUANTITY);
        do {
            Util.waitSeconds(1);
            if (cantidad < set_cantidad) {
                INCREMENT.resolveFor(actor).click();
            }
            cantidad++;
        } while (cantidad <= set_cantidad);
    }

    public static IncrementElement increment(Target quantity, Target increment, String set_increment) {
        return Tasks.instrumented(IncrementElement.class, quantity, increment, set_increment);
    }


}
