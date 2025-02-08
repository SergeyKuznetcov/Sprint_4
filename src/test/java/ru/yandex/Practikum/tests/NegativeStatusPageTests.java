package ru.yandex.Practikum.tests;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebElement;
import ru.yandex.Practikum.utils.DriverRule;
import ru.yandex.practikum.pages.MainPage;
import ru.yandex.practikum.pages.OrderStatusPage;

@RunWith(Parameterized.class)
public class NegativeStatusPageTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private final String orderStatus;

    public NegativeStatusPageTests(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    private static MainPage mainPage;

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {""},
                {"94375222"},
                {"text"},
        };
    }

    @Before
    public void acceptCookie() {
        mainPage = MainPage.openMainPage(driverRule.getWebDriver());
        mainPage.clickAcceptCookieButton();
    }

    @Test
    public void getOrderStatusByWrongNumber() {
        mainPage.clickOrderStatusButton();
        mainPage.fillOrderNumberField(orderStatus);
        OrderStatusPage orderStatusPage = mainPage.clickGoButton();
        WebElement webElement = orderStatusPage.getNotFoundImage();
        Assert.assertNotNull(webElement);
    }
}
