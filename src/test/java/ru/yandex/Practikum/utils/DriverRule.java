package ru.yandex.Practikum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.yandex.practikum.EnvConfig;

import java.time.Duration;

@Getter
public class DriverRule extends ExternalResource {
    private WebDriver webDriver;

    @Override
    protected void before() {
        initDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
    }

    @Override
    protected void after() {
        webDriver.quit();
    }

    public void initDriver() {
        if ("firefox".equalsIgnoreCase(System.getProperty("browser"))) {
            startUpFireFoxDriver();
        } else {
            startUpChromeDriver();
        }
    }

    public void startUpChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        this.webDriver = new ChromeDriver(options);
    }

    public void startUpFireFoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        this.webDriver = new FirefoxDriver(options);
    }
}
