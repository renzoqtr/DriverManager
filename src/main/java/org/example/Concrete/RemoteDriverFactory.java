package org.example.Concrete;

import org.example.DriverManagerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class RemoteDriverFactory extends DriverManagerFactory {

    @Override
    protected WebDriver createWebDriver() {
        ChromeOptions options = new ChromeOptions();

        URL remoteURL = null;
        try {
            remoteURL = new URL("http://localhost:4444");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return new RemoteWebDriver(remoteURL, options);
    }

}
