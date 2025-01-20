package qv.co.framewbase.core.screenplaycore.interactions.excelInteractions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import qv.co.framewbase.core.librarycore.evidence.ExcelFile;

public class SetParameter implements Interaction {
    private final String PARAMETER;
    private final String VALUE;

    public SetParameter(String PARAMETER, String VALUE) {
        this.PARAMETER = PARAMETER;
        this.VALUE = VALUE;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        new ExcelFile(PARAMETER, VALUE);
    }

    public static SetParameter set(String parameter, String value){
        return Tasks.instrumented(SetParameter.class, parameter, value);
    }
}
