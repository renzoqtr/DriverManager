package org.example;

import org.openqa.selenium.WebDriver;

public abstract class DriverManagerFactory {

    public WebDriver create(){
        return createWebDriver();
    }

    protected abstract WebDriver createWebDriver();
}
