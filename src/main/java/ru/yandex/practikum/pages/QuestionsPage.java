package ru.yandex.practikum.pages;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class QuestionsPage {
    private WebDriver webDriver;

    //Список вопросов
    private final By questionsList = By.xpath(".//*[contains(@id,'accordion__heading-')]");
    //Список ответов
    private final By answersList = By.xpath(".//*[contains(@id,'accordion__panel-')]");

    public Map<WebElement, WebElement> getQuestionsAndAnswersMap() {
        List<WebElement> questions = webDriver.findElements(questionsList);
        List<WebElement> answers = webDriver.findElements(answersList);
        Map<WebElement, WebElement> questionsAndAnswersMap = new HashMap<>();
        for (int i = 0; i < questions.size(); i++) {
            questionsAndAnswersMap.put(questions.get(i), answers.get(i));
        }
        return questionsAndAnswersMap;
    }
}
