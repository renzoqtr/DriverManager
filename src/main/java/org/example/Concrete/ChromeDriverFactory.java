package org.example.Concrete;

import org.example.DriverManagerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverFactory extends DriverManagerFactory {

    @Override
    protected WebDriver createWebDriver(){
        return new ChromeDriver();
    }

}
