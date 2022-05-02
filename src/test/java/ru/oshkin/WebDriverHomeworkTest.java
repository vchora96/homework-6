package ru.oshkin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.oshkin.TestData.*;
import static ru.oshkin.TestLocatorsData.*;

public class WebDriverHomeworkTest {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(WebDriverHomeworkTest.class.getName());
    private final String login = System.getenv("LOGIN");
    private final String pass = System.getenv("PASS");


    @BeforeEach
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        logger.info("Драйвер поднялся");
    }

    @AfterEach
    public void finish() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void setPrivetDataOtusTest() {
        startSession();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        logInByUser();
        setPrivateDataInfo();
        setMainInfo();
        setContactInfo();

        makeClick("//button[@title ='Сохранить и продолжить']", "Сохранить и продолжить");
        driver.quit();
        startSession();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //выполняем проверки
        logInByUser();
        checkPrivateDataInfo();
        checkMainInfo();
        checkContactInfo();
    }

    private void logInByUser() {
        String link = "https://otus.ru";
        driver.get(link);
        logger.info("Перешли по ссылке");

        WebElement logPage = driver.findElement(By.xpath("//button[@data-modal-id='new-log-reg']"));
        logPage.click();

        WebElement mail = driver.findElement(By.xpath(" //input[@type='text' and @placeholder='Электронная почта']"));
        mail.sendKeys(login);
        logger.info("Ввели почту");

        WebElement password = driver.findElement(By.xpath("//input[@type='password' and @placeholder='Введите пароль']"));
        password.sendKeys(pass);
        logger.info("Ввели пароль");

        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Войти')]"));
        button.submit();
        logger.info("Попытка авторизации");

        WebElement userButton = driver.findElement(By.xpath("//div[@class = 'header2-menu__item-wrapper " +
                "header2-menu__item-wrapper__username']"));
        userButton.click();
        logger.info("Раскрытие блока");

        WebElement personalAccountButton = driver.findElement(By.xpath("//a[@title ='Личный кабинет']"));
        personalAccountButton.click();
        logger.info("Открываем личный кабинет");

        WebElement aboutUserButton = driver.findElement(By.xpath("//a[@title ='О себе']"));
        aboutUserButton.click();
        logger.info("Открываем информацию о себе");
    }

    private void findElemSetValue(String locator, String value, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, 4);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        element.clear();
        element.sendKeys(value);
        logger.info(format("Очищаем поле: %s и вводим новое поле: %s", fieldName, fieldName));
    }

    private void makeClick(String locator, String fieldName) {
        WebElement elem = driver.findElement(By.xpath(locator));
        elem.click();
        logger.info(format("Кликаем на поле: %s", fieldName));
    }

    private void setPrivateDataInfo() {
        findElemSetValue(NAME_LOCATOR, NAME, "имя");
        findElemSetValue(LATIN_NAME_LOCATOR, LATIN_NAME, "имя на английском");
        findElemSetValue(SECOND_NAME_LOCATOR, SECOND_NAME, "фамилия");
        findElemSetValue(LATIN_SECOND_NAME_LOCATOR, LATIN_SECOND_NAME, "фамилия на английском");
        findElemSetValue(BLOG_NAME_LOCATOR, BLOG_NAME, "имя в блоге");
        findElemSetValue(DATE_OF_BIRTH_LOCATOR, DATE_OF_BIRTH, "дата рождения");

        makeClick(DATE_OF_BIRTH_LOCATOR, "окно календаря");
    }

    private void startSession() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
    }

    private void checkPrivateDataInfo() {
        assertEquals(NAME, driver.findElement(By.xpath(NAME_LOCATOR)).getAttribute("value"));
        assertEquals(LATIN_NAME, driver.findElement(By.xpath(LATIN_NAME_LOCATOR)).getAttribute("value"));
        assertEquals(SECOND_NAME, driver.findElement(By.xpath(SECOND_NAME_LOCATOR)).getAttribute("value"));
        assertEquals(LATIN_SECOND_NAME, driver.findElement(By.xpath(LATIN_SECOND_NAME_LOCATOR)).getAttribute("value"));
        assertEquals(BLOG_NAME, driver.findElement(By.xpath(BLOG_NAME_LOCATOR)).getAttribute("value"));
        assertEquals(DATE_OF_BIRTH, driver.findElement(By.xpath(DATE_OF_BIRTH_LOCATOR)).getAttribute("value"));
    }

    private void setMainInfo() {
        makeClick("//div[@class ='input input_full lk-cv-block__input lk-cv-block__input_fake " +
                "lk-cv-block__input_select-fake js-custom-select-presentation']", "список стран");
        makeClick("//button[@title='Узбекистан']", "Узбекистан");
        makeClick("//div[@class ='input input_full lk-cv-block__input lk-cv-block__input_fake " +
                        "lk-cv-block__input_select-fake js-custom-select-presentation']/child::span[contains(text(),'Город')]",
                "список городов");
        makeClick("//button[@title='Бухара']", "город");
        makeClick("//input[@data-title ='Уровень знания английского языка']/following-sibling::div",
                "уровни языка");
        makeClick("//button[@title='Начальный уровень (Beginner)']", "(Beginner)");
    }

    private void checkMainInfo() {
        assertEquals("Узбекистан", driver.findElement(By.xpath("//input[@name ='country']/following-sibling::div")).getText());
        assertEquals("Бухара", driver.findElement(By.xpath("//input[@name ='city']/" +
                "following-sibling::div")).getText());
        assertEquals("Начальный уровень (Beginner)", driver.findElement(By.xpath("//input[@name ='english_level']/" +
                "following-sibling::div")).getText());
    }

    private void setContactInfo() {

        makeClick("//button[contains(text(),'Указать телефон')]", "форма ввода телефона");

        findElemSetValue("//Input[@name ='phone' and @placeholder ='Номер телефона']", PHONE_NUMBER,
                "номер телефона");

        makeClick("//button[contains(text(),'Отправить')]", "Отправить");
        makeClick("//div[@class ='modal__close ic-close js-close-modal']", "модальное окно с телефоном");

        addContact(
                "//div[@class ='lk-cv-block__select-options lk-cv-block__select-options_left " +
                        "js-custom-select-options-container']/descendant::button[@title='Viber']",
                "Viber", PHONE_NUMBER);
        addContact(
                "//div[@class ='lk-cv-block__select-options lk-cv-block__select-options_left " +
                        "js-custom-select-options-container']/descendant::button[@title ='Skype']",
                "Skype", SKYPE_LOGIN);
    }

    private void checkContactInfo() {
        WebElement skype = driver.findElement(By.xpath("//div[contains(text(),'Skype')]"));
        boolean skypeEnabled = skype.isEnabled();
        assertTrue(skypeEnabled);

        WebElement viber = driver.findElement(By.xpath("//div[contains(text(),'Viber')]"));
        boolean viberEnabled = viber.isEnabled();
        assertTrue(viberEnabled);

        List<WebElement> contacts = driver.findElements(By.xpath("//input[@class='input input_straight-top-left " +
                "input_straight-bottom-left lk-cv-block__input" +
                " lk-cv-block__input_9 lk-cv-block__input_md-8']"));
        ArrayList<String> strings = new ArrayList<>();
        for (WebElement contact : contacts) {
            String text = contact.getAttribute("value");
            strings.add(text);
        }

        boolean isPhoneContains = strings.contains(PHONE_NUMBER);
        assertTrue(isPhoneContains);

        boolean isSkypeLoginContains = strings.contains(SKYPE_LOGIN);
        assertTrue(isSkypeLoginContains);
    }

    private void addContact(String locator, String contactType, String value) {
        driver.findElement(By.cssSelector("button.js-lk-cv-custom-select-add")).click();
        logger.info("Добавить контакт");

        makeClick("//span[contains(text(),'Способ связи')]", "Способ связи");

        selectCommunicationWay(locator, contactType);
        List<WebElement> elements = driver.findElements(By.xpath("//input[@class='input input_straight-top-left " +
                "input_straight-bottom-left lk-cv-block__input" +
                " lk-cv-block__input_9 lk-cv-block__input_md-8']"));
        for (WebElement element : elements) {
            String text = element.getAttribute("value");
            if (text.length() == 0) {
                element.sendKeys(value);
                element.click();
            }
        }
    }

    private void selectCommunicationWay(String locator, String name) {
        List<WebElement> communicationWays = driver.findElements(By.xpath(locator));
        communicationWays.get(0).click();
        logger.info(format("Выбираем способ связи %s", name));
    }
}
