package ru.yandex.Practikum.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.Practikum.utils.DriverRule;
import ru.yandex.practikum.constants.Constants;
import ru.yandex.practikum.constants.ScooterColours;
import ru.yandex.practikum.pages.ClientInfoPage;
import ru.yandex.practikum.pages.MainPage;
import ru.yandex.practikum.pages.OrderInfoPage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private MainPage mainPage;

    private final Boolean isHeaderButton;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int station;
    private final String phoneNumber;
    private final List<ScooterColours> colours;
    private final String comment;

    public OrderTests(Boolean isHeaderButton, String firstName, String lastName, String address, int station,
                      String phoneNumber, List<ScooterColours> colours, String comment) {
        this.isHeaderButton = isHeaderButton;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.station = station;
        this.phoneNumber = phoneNumber;
        this.colours = colours;
        this.comment = comment;
    }

    @Before
    public void acceptCookie() {
        mainPage = MainPage.openMainPage(driverRule.getWebDriver());
        mainPage.clickAcceptCookieButton();
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {true, "Андрей", "Коротков", "Москва,ул.Новоясеневская", 5, "89347362884", Collections.EMPTY_LIST, ""},
                {false, "Иван", "Иванов", "Москва,ул.Радужная", 10, "84653657363", Arrays.asList(ScooterColours.BLACK, ScooterColours.GREY), "Требуется заказать пропуск"},
        };
    }

    @Test
    public void orderScooterTest() {
        ClientInfoPage clientInfoPage = clickOrderButton(isHeaderButton, mainPage);
        clientInfoPage.fillClientInfo(firstName, lastName, address, station, phoneNumber);
        OrderInfoPage orderInfoPage = clientInfoPage.clickContinueButton();
        orderInfoPage.fillOrderInfo(colours, comment);
        orderInfoPage.clickOrderButton();
        orderInfoPage.clickSubmitOrderButton();
        String orderStatus = orderInfoPage.getOrderStatus();
        Assert.assertTrue(orderStatus.contains(Constants.SUCCESS_ORDER_STATUS));
    }

    private ClientInfoPage clickOrderButton(Boolean isHeaderButton, MainPage mainPage) {
        if (isHeaderButton) {
            return mainPage.clickHeaderOrderButton();
        } else {
            return mainPage.clickOrderButton();
        }
    }
}
