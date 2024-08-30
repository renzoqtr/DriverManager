package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SauceLoginTest {
    WebDriver webdriver;

    @BeforeEach
    public void setup() {
        String browserOption = System.getProperty("app.browser_driver");
        webdriver = DriverFactory.valueOf(Objects.requireNonNullElse(browserOption, "CHROME")).getDriver();
        webdriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/loginData.csv", numLinesToSkip = 1)
    public void LoginTest(String userName, String password) {
        webdriver.get("https://www.saucedemo.com/");

        WebElement userNameInput = webdriver.findElement(By.id("user-name"));
        WebElement passwordInput = webdriver.findElement(By.id("password"));
        WebElement loginButton = webdriver.findElement(By.id("login-button"));

        userNameInput.clear();
        userNameInput.sendKeys(userName);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();

        int producstQuantity = webdriver.findElements(By.cssSelector("[data-test='inventory-item']")).size();
        assertEquals(producstQuantity, 6);
    }

    @Test
    public void checkoutProducts() {
        //Navigates to page
        webdriver.get("https://www.saucedemo.com/");

        //Finds all elements to do a login
        WebElement userNameInput = webdriver.findElement(By.id("user-name"));
        WebElement passwordInput = webdriver.findElement(By.id("password"));
        WebElement loginButton = webdriver.findElement(By.id("login-button"));

        //Clears and types info for login
        userNameInput.clear();
        userNameInput.sendKeys("standard_user");
        passwordInput.clear();
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        //Products to be added
        String[] productsToAdd = {
                "Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt", "Sauce Labs Onesie"
        };

        //Finds all Product Elements
        List<WebElement> products = webdriver.findElements(By.cssSelector("[data-test='inventory-item']"));

        //Filter on products by the product to be added
        for (String productName : productsToAdd) {
            products.stream()
                    .filter(elem -> elem.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText().equals(productName))
                    .findFirst()
                    .get()
                    .findElement(By.cssSelector("button.btn_inventory"))
                    .click();
        }

        //Clicks on the cart button so cart page be displayed
        webdriver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();


        // Gets all added products titles as String List
        List<String> productsOnCart = webdriver.findElements(By.cssSelector("[data-test='inventory-item-name']"))
                .stream()
                .map(WebElement::getText)
                .toList();

        //Asserts the added products titles names matches the expected ones
        Assertions.assertIterableEquals(Arrays.asList(productsToAdd), productsOnCart);
    }

    @AfterEach
    public void tieUp() {
        webdriver.quit();
    }

}
