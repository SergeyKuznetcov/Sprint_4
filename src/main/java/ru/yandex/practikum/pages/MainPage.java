package ru.yandex.practikum.pages;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static ru.yandex.practikum.EnvConfig.BASE_URL;

@AllArgsConstructor
public class MainPage {

    private WebDriver webDriver;

    //Кнопка "Принять куки"
    private final By acceptCookieButton = By.id("rcc-confirm-button");
    //Верхняя кнопка "Заказать"
    private final By headerOrderButton = By.cssSelector("div.Header_Nav__AGCXC>button.Button_Button__ra12g");
    //Нижняя кнопка "Заказать"
    private final By orderButton = By.xpath(".//button[contains(@class,'Button_Middle__1CSJM')]");

    public static MainPage openMainPage(WebDriver webDriver) {
        webDriver.get(BASE_URL);
        return new MainPage(webDriver);
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
