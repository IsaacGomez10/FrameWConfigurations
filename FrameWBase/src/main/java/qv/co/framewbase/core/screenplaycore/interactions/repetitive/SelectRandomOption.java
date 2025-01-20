package qv.co.framewbase.core.screenplaycore.interactions.repetitive;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import qv.co.framewbase.core.librarycore.evidence.Reports;

import java.util.List;
import java.util.Random;

public class SelectRandomOption implements Interaction {
    private final WebElementFacade SELECT;

    public SelectRandomOption(WebElementFacade SELECT) {
        this.SELECT = SELECT;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            Select select = new Select(SELECT);
            List<WebElement> options = select.getOptions();
            if (options.size() > 1) {
                Random random = new Random();
                int random_index = 1 + random.nextInt(options.size() - 1);
                select.selectByIndex(random_index);
            } else {
                Reports.reportEvent(Reports.FAILED, "El elemento no tiene opciones disponible.");
                throw new IllegalArgumentException("El elemento no tiene opciones disponible.");
            }
        } catch (Exception e) {
            Reports.reportEvent(Reports.FAILED, "Error al seleccionar la opcion de un select.");
            e.printStackTrace();
        }
    }

    /**
     * Con el [element] se espera que sea de tag select al cual,<br>
     * tomara una opción de manera random
     *
     * @param element
     * @return
     * @author: Isaac Gomez
     */
    public static SelectRandomOption select(WebElementFacade element) {
        return Tasks.instrumented(SelectRandomOption.class, element);
    }
}
