package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleTest {

    WebDriver webdriver;

    @BeforeEach
    public void setup() {
        String browserOption = System.getProperty("app.browser_driver");
        webdriver = DriverFactory.valueOf(Objects.requireNonNullElse(browserOption, "CHROME")).getDriver();
        webdriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    }

    @Test
    public void simpleTestExample() {
        webdriver.get("https://www.avianca.com/es/");
        WebElement origenLabel = webdriver.findElement(By.id("departureStationInputLabel"));
        String origenLabelText = origenLabel.getText();
        WebElement buscarButton = webdriver.findElement(By.id("searchButton"));
        assertEquals("Origen", origenLabelText);
        assertTrue(buscarButton.isDisplayed());
    }

    /**
     * It is disable due to archlinux site may launch too much request prompt
     */
    @Disabled
    public void exampleTest() {
        webdriver.get("https://www.archlinux.org");
        WebElement search = webdriver.findElement(By.id("pkgsearch-field"));
        search.sendKeys("playstation");
        search.sendKeys(Keys.RETURN);
        List<WebElement> results = webdriver.findElements(By.cssSelector(".results td a"));
        Optional<WebElement> expected = results.stream().filter(result -> result.getText().contains("pcsx2")).findFirst();
        assertTrue(expected.isPresent());
        System.out.println(expected.get().getText());
    }

    @ParameterizedTest
    @CsvSource({"archlinux,Arch Linux", "fedora,Fedora", "debian,Debian"})
    public void exampleParametrized(String query, String expected) {
        webdriver.get("https://distrowatch.com/");
        WebElement searchDistro = webdriver.findElement(By.name("distribution"));
        searchDistro.sendKeys(query);
        searchDistro.sendKeys(Keys.RETURN);
        String title = webdriver.findElement(By.cssSelector(".TablesTitle h1")).getText();
        assertEquals(title, expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void exampleParametrizedCvsFile(String query, String expected) {
        webdriver.get("https://distrowatch.com/");
        WebElement searchDistro = webdriver.findElement(By.name("distribution"));
        searchDistro.sendKeys(query);
        searchDistro.sendKeys(Keys.RETURN);
        String title = webdriver.findElement(By.cssSelector(".TablesTitle h1")).getText();
        assertEquals(title, expected);
    }

    @AfterEach
    public void tieUp() {
        webdriver.quit();
    }

}
