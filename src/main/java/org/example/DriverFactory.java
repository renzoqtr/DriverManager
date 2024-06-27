package org.example;

import org.example.Concrete.ChromeDriverFactory;
import org.example.Concrete.FirefoxDriverFactory;
import org.example.Concrete.RemoteDriverFactory;

import org.openqa.selenium.WebDriver;

public enum DriverFactory {

    CHROME {
        @Override
        public WebDriver getDriver() {
            return new ChromeDriverFactory().create();
        }
    },
    FIREFOX {
        @Override
        public WebDriver getDriver() {
            return new FirefoxDriverFactory().create();
        }
    },
    REMOTE {
        @Override
        public WebDriver getDriver() {
            return new RemoteDriverFactory().create();
        }
    };

    public abstract WebDriver getDriver();
}


