package ru.yandex.Practikum.tests;

import lombok.AllArgsConstructor;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.Practikum.utils.DriverRule;
import ru.yandex.practikum.constants.Constants;
import ru.yandex.practikum.constants.ScooterColours;
import ru.yandex.practikum.pages.ClientInfoPage;
import ru.yandex.practikum.pages.MainPage;
import ru.yandex.practikum.pages.OrderInfoPage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RunWith(Parameterized.class)
public class OrderTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private MainPage mainPage;

    private Boolean isHeaderButton;
    private String firstName;
    private String lastName;
    private String address;
    private int station;
    private String phoneNumber;
    private String rentDuration;
    private List<ScooterColours> colours;
    private String comment;

    public OrderTests(Boolean isHeaderButton, String firstName, String lastName, String address, int station,
                      String phoneNumber, String rentDuration, List<ScooterColours> colours, String comment) {
        this.isHeaderButton = isHeaderButton;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.station = station;
        this.phoneNumber = phoneNumber;
        this.rentDuration = rentDuration;
        this.colours = colours;
        this.comment = comment;
    }

    @Before
    public void acceptCookie(){
       mainPage = MainPage.openMainPage(driverRule.getWebDriver());
       mainPage.clickAcceptCookieButton();
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                { true, "Андрей", "Коротков", "Москва,ул.Новоясеневская", 5, "89347362884","двое суток", Collections.EMPTY_LIST, ""},
                { false, "Иван", "Иванов", "Москва,ул.Радужная", 10, "84653657363", "сутки", Arrays.asList(ScooterColours.BLACK),"Требуется заказать пропуск"},
        };
    }

    @Test
    public void orderScooterTest() {
        ClientInfoPage clientInfoPage = clickOrderButton(isHeaderButton, mainPage);
        clientInfoPage.fillClientInfo(firstName, lastName, address, station, phoneNumber);
        OrderInfoPage orderInfoPage = clientInfoPage.clickContinueButton();
        orderInfoPage.fillOrderInfo(rentDuration, colours, comment);
        orderInfoPage.clickOrderButton();
        orderInfoPage.clickSubmitOrderButton();
        String orderStatus = orderInfoPage.getOrderStatus();
        Assert.assertTrue(orderStatus.contains(Constants.SUCCESS_ORDER_STATUS));
    }

    private ClientInfoPage clickOrderButton(Boolean isHeaderButton, MainPage mainPage) {
        if (isHeaderButton) {
            return mainPage.clickHeaderOrderButton();
        }else {
            return mainPage.clickOrderButton();
        }
    }
}
