package ru.yandex.practikum.pages;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.practikum.EnvConfig;
import ru.yandex.practikum.constants.ScooterColours;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class OrderInfoPage {
    private WebDriver webDriver;

    //Поле для ввода даты
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    //Поле для ввода длительности аренды
    private final By rentDurationField = By.className("Dropdown-placeholder");
    //Поле для ввода комментария для курьера
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    //Кнопка "Заказать" под полями ввода
    private final By orderButton = By.xpath(".//button[text()='Заказать' and @class='Button_Button__ra12g Button_Middle__1CSJM']");
    //Кнопка "Да"
    private final By submitOrderButton = By.xpath(".//button[text()='Да' and @class='Button_Button__ra12g Button_Middle__1CSJM']");
    //Окно со статусом заказа
    private final By orderStatusPlane = By.className("Order_ModalHeader__3FDaJ");
    //Список кнопок для выбора даты
    private final By chooseDayButtons = By.xpath(".//div[contains(@class,'react-datepicker__day--')]");
    //Список возможных вариантов длительности аренды
    private final By chooseRentDurationButtons = By.className("Dropdown-option");

    public void chooseRandomDeliveryDay() {
        clickDateField();
        List<WebElement> daysButtons = webDriver.findElements(chooseDayButtons);
        daysButtons.get(new Random().nextInt(daysButtons.size())).click();
    }

    public void chooseRandomRentDuration() {
        clickRentDurationField();
        List<WebElement> rentDurationButtons = webDriver.findElements(chooseRentDurationButtons);
        rentDurationButtons.get(new Random().nextInt(rentDurationButtons.size())).click();
    }

    public void clickRentDurationField() {
        webDriver.findElement(rentDurationField).click();
    }

    public void clickDateField() {
        webDriver.findElement(dateField).click();
    }

    public String getOrderStatus(){
        return webDriver.findElement(orderStatusPlane).getText();
    }

    public void clickOrderButton() {
        webDriver.findElement(orderButton).click();
    }

    public void clickSubmitOrderButton() {
        new WebDriverWait(webDriver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.presenceOfElementLocated(submitOrderButton));
        webDriver.findElement(submitOrderButton).click();
    }

    public void clickColorCheckBoxes(List<ScooterColours> colours) {
        colours.forEach(colour -> webDriver.findElement(By.id(colour.name().toLowerCase())).click());
    }

    public void fillOrderInfo(String rentDuration, List<ScooterColours> colours, String comment) {
        chooseRandomDeliveryDay();
        chooseRandomRentDuration();
        clickColorCheckBoxes(colours);
        webDriver.findElement(commentField).sendKeys(comment);
    }
}
