package com.co.airbnb.runners;

import com.co.airbnb.screenplay.util.cucumber.CucumberConfigConstans;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import qv.co.framewbase.core.baserunners.BaseTestRunner;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = CucumberConfigConstans.FEATURES,
        glue = CucumberConfigConstans.GLUE,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        tags = "@RegistroExistoso"
)
public class AirbnbRunner extends BaseTestRunner {
}
