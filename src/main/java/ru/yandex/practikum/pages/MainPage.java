package ru.yandex.practikum.pages;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.yandex.practikum.EnvConfig.BASE_URL;
import static ru.yandex.practikum.EnvConfig.EXPLICIT_WAIT;

@AllArgsConstructor
public class MainPage {

    private WebDriver webDriver;

    //Логотип Самокат
    private final By scooterLogo = By.className("Header_LogoScooter__3lsAR");
    //Логотип Yandex
    private final By yandexLogo = By.className("Header_LogoYandex__3TSOI");
    //Кнопка "Принять куки"
    private final By acceptCookieButton = By.id("rcc-confirm-button");
    //Кнопка "Статус заказа"
    private final By orderStatusButton = By.xpath(".//button[text()='Статус заказа']");
    //Поле ввода номера заказа
    private final By orderNumberField = By.xpath(".//input[@placeholder='Введите номер заказа']");
    //Кнопка "Go!"
    private final By goButton = By.xpath(".//button[text()='Go!']");
    //Верхняя кнопка "Заказать"
    private final By headerOrderButton = By.cssSelector("div.Header_Nav__AGCXC>button.Button_Button__ra12g");
    //Нижняя кнопка "Заказать"
    private final By orderButton = By.xpath(".//button[contains(@class,'Button_Middle__1CSJM')]");

    public static MainPage openMainPage(WebDriver webDriver) {
        webDriver.get(BASE_URL);
        return new MainPage(webDriver);
    }

    public void clickOrderStatusButton() {
        webDriver.findElement(orderStatusButton).click();
    }

    public void fillOrderNumberField(String orderNumber) {
        webDriver.findElement(orderNumberField).sendKeys(orderNumber);
    }

    public OrderStatusPage clickGoButton() {
        new WebDriverWait(webDriver, Duration.ofSeconds(EXPLICIT_WAIT)).until(ExpectedConditions.elementToBeClickable(goButton));
        webDriver.findElement(goButton).click();
        return new OrderStatusPage(webDriver);
    }

    public void clickScooterLogo() {
        webDriver.findElement(scooterLogo).click();
    }

    public void clickYandexLogo() {
        webDriver.findElement(yandexLogo).click();
    }

    public void clickAcceptCookieButton() {
        webDriver.findElement(acceptCookieButton).click();
    }

    public ClientInfoPage clickHeaderOrderButton() {
        webDriver.findElement(headerOrderButton).click();
        return new ClientInfoPage(webDriver);
    }

    public ClientInfoPage clickOrderButton() {
        webDriver.findElement(orderButton).click();
        return new ClientInfoPage(webDriver);
    }
}
