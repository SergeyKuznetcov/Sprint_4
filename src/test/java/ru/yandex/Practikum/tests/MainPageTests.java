package ru.yandex.Practikum.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.Practikum.utils.DriverRule;
import ru.yandex.practikum.EnvConfig;
import ru.yandex.practikum.pages.MainPage;
import ru.yandex.practikum.pages.QuestionsPage;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

import static ru.yandex.practikum.EnvConfig.EXPLICIT_WAIT;
import static ru.yandex.practikum.EnvConfig.YANDEX_REDIRECT_URL;
import static ru.yandex.practikum.constants.Constants.QUESTIONS_AND_EXPECTED_ANSWERS;

@Slf4j
public class MainPageTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private static MainPage mainPage;

    @Before
    public void acceptCookie() {
        mainPage = MainPage.openMainPage(driverRule.getWebDriver());
        mainPage.clickAcceptCookieButton();
    }

    @Test
    public void checkScooterLogoRedirect() {
        WebDriver webDriver = driverRule.getWebDriver();
        mainPage.clickScooterLogo();
        Assert.assertEquals(EnvConfig.BASE_URL, webDriver.getCurrentUrl());
    }

    @Test
    public void checkYandexLogoRedirect() {
        WebDriver webDriver = driverRule.getWebDriver();
        mainPage.clickYandexLogo();
        Iterator<String> windowHandlesIterator = webDriver.getWindowHandles().iterator();
        String lastHandle;
        do {
            lastHandle = windowHandlesIterator.next();
        } while (windowHandlesIterator.hasNext());
        webDriver.switchTo().window(lastHandle);
        new WebDriverWait(webDriver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.urlToBe(YANDEX_REDIRECT_URL));
    }

    @Test
    public void checkIsAnswerDisplayedAfterQuestionClick() {
        WebDriver webDriver = driverRule.getWebDriver();
        QuestionsPage questionsPage = new QuestionsPage(webDriver);
        Map<WebElement, WebElement> questionsAndAnswersMap = questionsPage.getQuestionsAndAnswersMap();
        questionsAndAnswersMap.forEach((questionElement, answerElement) -> {
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", questionElement);
            new WebDriverWait(webDriver, Duration.ofSeconds(EXPLICIT_WAIT))
                    .until(ExpectedConditions.elementToBeClickable(questionElement));
            questionElement.click();
            new WebDriverWait(webDriver, Duration.ofSeconds(EXPLICIT_WAIT))
                    .until(ExpectedConditions.visibilityOf(answerElement));
            String questionText = questionElement.getText();
            String answerText = answerElement.getText();

            Assert.assertTrue("Unexpected question text \"" + questionText + "\"",
                    QUESTIONS_AND_EXPECTED_ANSWERS.containsKey(questionText));
            Assert.assertEquals("Unexpected answer text \"" + answerText + "\" for question text \"" + questionText + "\"", answerText, QUESTIONS_AND_EXPECTED_ANSWERS.get(questionText));

        });
    }
}
