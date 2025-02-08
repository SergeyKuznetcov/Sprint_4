package ru.yandex.Practikum.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.Practikum.utils.DriverRule;
import ru.yandex.practikum.pages.MainPage;
import ru.yandex.practikum.pages.QuestionsPage;

import java.time.Duration;
import java.util.Map;

import static ru.yandex.practikum.EnvConfig.EXPLICIT_WAIT;
import static ru.yandex.practikum.constants.Constants.QUESTIONS_AND_EXPECTED_ANSWERS;

@Slf4j
public class QuestionsListTest {
    @ClassRule
    public static DriverRule driverRule = new DriverRule();

    @BeforeClass
    public static void acceptCookie() {
        MainPage.openMainPage(driverRule.getWebDriver()).clickAcceptCookieButton();
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
