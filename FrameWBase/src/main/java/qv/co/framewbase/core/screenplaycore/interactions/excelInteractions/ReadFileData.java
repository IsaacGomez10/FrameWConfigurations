package qv.co.framewbase.core.screenplaycore.interactions.excelInteractions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import qv.co.framewbase.core.librarycore.evidence.ExcelFile;

public class ReadFileData implements Interaction {
    private final String name_file;
    private final int row_number;

    public ReadFileData(String name_file, int row_number) {
        this.name_file = name_file;
        this.row_number = row_number;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        new ExcelFile(name_file, row_number);
    }

    public static ReadFileData read(String name_file, int row_number) {
        return Tasks.instrumented(ReadFileData.class, name_file, row_number);
    }
}
