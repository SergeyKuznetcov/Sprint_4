package ru.yandex.practikum.pages;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.practikum.EnvConfig;

import java.time.Duration;
import java.util.List;

@AllArgsConstructor
public class ClientInfoPage {
    private WebDriver webDriver;
    //Поле для ввода имени
    private final By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    //Поле для ввода фамилии
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    //Поле для ввода адресса
    private final By addressField = By.xpath(".//.//input[contains(@placeholder, '* Адрес')]");
    //Поле для ввода станции метро
    private final By stationField = By.xpath(".//input[@placeholder='* Станция метро']");
    //Поле для ввода номера телефона
    private final By phoneNumberField = By.xpath(".//input[contains(@placeholder, '* Телефон')]");
    //Кнопка "Далее"
    private final By continueButton = By.className("Button_Middle__1CSJM");
    //Список ошибок при заполнении полей
    private final By fieldErrorsList = By.xpath(".//div[contains(@class,'Input_ErrorMessage__3HvIb Input_Visible___syz6') or contains(@class,'Order_MetroError__1BtZb')]");

    private final String stationListElementXpathTemplate = ".//li[@data-index='%s']";

    public List<WebElement> getFieldErrorElements(Integer expectedErrorsCount) {
        new WebDriverWait(webDriver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.numberOfElementsToBe(fieldErrorsList, expectedErrorsCount));
        return webDriver.findElements(fieldErrorsList);
    }

    public void chooseStation(int stationIndex) {
        webDriver.findElement(stationField).click();
        webDriver.findElement(By.xpath(String.format(stationListElementXpathTemplate, stationIndex))).click();
    }

    public OrderInfoPage clickContinueButton() {
        webDriver.findElement(continueButton).click();
        return new OrderInfoPage(webDriver);
    }

    public void fillClientInfo(String firstName, String lastName, String address, int station, String phoneNumber) {
        webDriver.findElement(firstNameField).sendKeys(firstName);
        webDriver.findElement(lastNameField).sendKeys(lastName);
        webDriver.findElement(addressField).sendKeys(address);
        if (station >= 0) {
            chooseStation(station);
        }
        webDriver.findElement(phoneNumberField).sendKeys(phoneNumber);
    }
}
