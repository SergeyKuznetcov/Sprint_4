package ru.yandex.Practikum.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebElement;
import ru.yandex.Practikum.utils.DriverRule;
import ru.yandex.practikum.constants.ExpectedFieldErrors;
import ru.yandex.practikum.pages.ClientInfoPage;
import ru.yandex.practikum.pages.MainPage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practikum.constants.ExpectedFieldErrors.*;

@RunWith(Parameterized.class)
public class NegativeOrderTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private static MainPage mainPage;

    private final String firstName;
    private final String lastName;
    private final String address;
    private final int station;
    private final String phoneNumber;
    private final List<ExpectedFieldErrors> expectedFieldErrors;

    public NegativeOrderTests(String firstName, String lastName, String address, int station, String phoneNumber, List<ExpectedFieldErrors> expectedFieldErrors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.station = station;
        this.phoneNumber = phoneNumber;
        this.expectedFieldErrors = expectedFieldErrors;
    }

    @Before
    public void acceptCookie() {
        mainPage = MainPage.openMainPage(driverRule.getWebDriver());
        mainPage.clickAcceptCookieButton();
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"123", "15145", "423", -1, "-79023489009", Arrays.asList(ExpectedFieldErrors.values())},
                {"adfdfs", "adfasd", "agfdd", 6, "8903", Arrays.asList(FIRST_NAME_ERROR, LAST_NAME_ERROR, ADDRESS_ERROR, PHONE_NUMBER_ERROR)},
                {"Я", "О", "ул", 1, "73475987234785987234567234567", Arrays.asList(FIRST_NAME_ERROR, LAST_NAME_ERROR,ADDRESS_ERROR, PHONE_NUMBER_ERROR)},
                {"Аофыпрлвдыоаплдовпфаы", "ФЫарпфвыоларрфыврлофвы", "Фвыраыорваппыоварпфваорфдлоаплдфырвдлопрдфлвпмадлпфрвиадмл", -2, "+79040384758", Arrays.asList(FIRST_NAME_ERROR, ADDRESS_ERROR, STATION_ERROR)},
        };
    }

    @Test
    public void checkClientInfoPageFieldErrors() {
        ClientInfoPage clientInfoPage = mainPage.clickHeaderOrderButton();
        clientInfoPage.fillClientInfo(firstName, lastName, address, station, phoneNumber);
        clientInfoPage.clickContinueButton();
        List<WebElement> errorElements = clientInfoPage.getFieldErrorElements(expectedFieldErrors.size());
        List<String> expectedFieldErrorMessages = expectedFieldErrors.stream()
                .map(ExpectedFieldErrors::getErrorMessage)
                .collect(Collectors.toList());
        errorElements.stream()
                .map(WebElement::getText)
                .forEach(v -> Assert.assertTrue("Unexpected error message \"" + v + "\"", expectedFieldErrorMessages.contains(v)));
    }
}
