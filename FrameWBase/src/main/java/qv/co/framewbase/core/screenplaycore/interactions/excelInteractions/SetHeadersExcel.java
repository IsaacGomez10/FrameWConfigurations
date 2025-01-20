package qv.co.framewbase.core.screenplaycore.interactions.excelInteractions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import qv.co.framewbase.core.librarycore.evidence.ExcelFile;

public class SetHeadersExcel implements Interaction {
    private final String[] headers;
    public SetHeadersExcel(String... headers) {
        this.headers = headers;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        ExcelFile.setHeaders(headers);
    }

    public static SetHeadersExcel set(String... headers){
        return Tasks.instrumented(SetHeadersExcel.class, (Object) headers);
    }
}
