package org.example.Concrete;

import org.example.DriverManagerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverFactory extends DriverManagerFactory {

    @Override
    protected WebDriver createWebDriver(){
        return new FirefoxDriver();
    }
}
