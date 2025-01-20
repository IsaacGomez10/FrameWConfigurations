package com.co.airbnb.stepdefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import qv.co.framewbase.core.basestepdefinitions.BaseHooks;

public class Hooks extends BaseHooks {
    @Before
    public static void setUp(Scenario scenario){
        BaseHooks.before(scenario);
    }

    @After
    public static void tearDown(){
        BaseHooks.after();
    }
}
