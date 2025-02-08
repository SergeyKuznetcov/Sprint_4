package ru.yandex.practikum.pages;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@AllArgsConstructor
public class OrderStatusPage {
    private WebDriver webDriver;

    //Картинка с ошибкой "Not found"
    private final By notFoundImage = By.xpath(".//img[@alt='Not found']");

    public WebElement getNotFoundImage() {
        return webDriver.findElement(notFoundImage);
    }
}
