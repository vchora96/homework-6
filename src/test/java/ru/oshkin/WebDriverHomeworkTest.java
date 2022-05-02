package ru.oshkin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static ru.oshkin.TestData.*;

public class WebDriverHomeworkTest {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(WebDriverHomeworkTest.class.getName());
    private final String login = "macorax714@idurse.com";
    private final String pass = "Test12345";

    @BeforeEach
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        logger.info("Драйвер поднялся");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

//    @AfterEach
//    public void finish() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

    @Test
    public void setPrivetDataOtusTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        logInByUser();
        setPrivateDataInfo();
        setMainInfo();
        setContactInfo();

        makeClick("//button[@title ='Сохранить и продолжить']", "Сохранить и продолжить");
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

        WebElement userButton = driver.findElement(By.xpath("//div[@class = 'header2-menu__item-wrapper header2-menu__item-wrapper__username']"));
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
//        JavascriptExecutor executor = (JavascriptExecutor) driver;
//        executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(locator)));

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
        findElemSetValue("//input [@name ='fname']", NAME, "имя");
        findElemSetValue("//input [@name ='fname_latin']", LATIN_NAME, "имя на английском");
        findElemSetValue("//input [@name ='lname']", SECOND_NAME, "фамилия");
        findElemSetValue("//input [@name ='lname_latin']", LATIN_SECOND_NAME, "фамилия на английском");
        findElemSetValue("//input [@name ='blog_name']", BLOG_NAME, "имя в блоге");
        findElemSetValue("//input [@name ='date_of_birth']", DATE_OF_BIRTH, "дата рождения");

        makeClick("//input[@title='День рождения']", "окно календаря");
    }

    private void setMainInfo() {
        makeClick("//div[@class ='input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake " + "js-custom-select-presentation']", "список стран");
        makeClick("//button[@title='Узбекистан']", "Узбекистан");
        makeClick("//div[@class ='input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake" + " js-custom-select-presentation']/child::span[contains(text(),'Город')]", "список городов");
        makeClick("//button[@title='Бухара']", "город");
        makeClick("//input[@data-title ='Уровень знания английского языка']/following-sibling::div", "уровни языка");
        makeClick("//button[@title='Начальный уровень (Beginner)']", "(Beginner)");
    }

    private void setContactInfo() {

        makeClick("//button[contains(text(),'Указать телефон')]", "форма ввода телефона");

        findElemSetValue("//Input[@name ='phone' and @placeholder ='Номер телефона']", PHONE_NUMBER, "номер телефона");

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

    private void addContact(String locator, String contactType, String value) {
        driver.findElement(By.cssSelector("button.js-lk-cv-custom-select-add")).click();
        logger.info("Добавить контакт");

        makeClick("//span[contains(text(),'Способ связи')]", "Способ связи");

        selectCommunicationWay(locator, contactType);
        List<WebElement> elements = driver.findElements(By.xpath("//input[@class='input input_straight-top-left input_straight-bottom-left lk-cv-block__input" +
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
