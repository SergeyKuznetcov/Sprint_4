package ru.yandex.practikum.pages;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    private final String stationListElementXpathTemplate = ".//li[@data-index='%s']";

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
        chooseStation(station);
        webDriver.findElement(phoneNumberField).sendKeys(phoneNumber);
    }
}
