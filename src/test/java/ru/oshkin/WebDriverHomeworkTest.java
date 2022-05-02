package ru.oshkin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        WebElement button1 = driver.findElement(By.xpath("//button[@data-modal-id='new-log-reg']"));
        button1.click();

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

        WebElement userButton1 = driver.findElement(By.xpath("//a[@title ='Личный кабинет']"));
        userButton1.click();
        logger.info("Открываем личный кабинет");

        WebElement userButton2 = driver.findElement(By.xpath("//a[@title ='О себе']"));
        userButton2.click();
        logger.info("Открываем информацию о себе");
    }

    private void findElemSetValue(String locator, String value, String fieldName) {
        WebElement firstNameButton = driver.findElement(By.xpath(locator));
        firstNameButton.clear();
        firstNameButton.sendKeys(value);
        logger.info(String.format("Очищаем поле: %s и вводим новое поле: %s", fieldName, fieldName));
    }

    private void makeClick(String locator, String fieldName) {
        WebElement calendarInput = driver.findElement(By.xpath(locator));
        calendarInput.click();
        logger.info(String.format("Кликаем на поле: %s", fieldName));
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
        makeClick("//div[@class ='input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake " +
                "js-custom-select-presentation']", "список стран");
        makeClick("//button[@title='Узбекистан']", "Узбекистан");
        makeClick("//div[@class ='input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake" +
                " js-custom-select-presentation']/child::span[contains(text(),'Город')]", "список городов");
        makeClick("//button[@title='Бухара']", "город");
        makeClick("//input[@data-title ='Уровень знания английского языка']/following-sibling::div", "уровни языка");
        makeClick("//button[@title='Начальный уровень (Beginner)']", "(Beginner)");
    }

    private void setContactInfo() {

        makeClick("//button[contains(text(),'Указать телефон')]", "форма ввода телефона");

        findElemSetValue("//Input[@name ='phone' and @placeholder ='Номер телефона']", "+7 911 111-11-11", "номер телефона");

        makeClick("//button[contains(text(),'Отправить')]", "Отправить");
        makeClick("//div[@class ='modal__close ic-close js-close-modal']", "модальное окно с телефоном");

//        List<WebElement> elements10 = driver.findElements(By.xpath("//div[@data-prefix ='contact']/descendant::button[contains(text(),'Удалить')]"));
//        for (int i = 0; i < elements10.size(); i++) {
//            try {
//                elements10.get(i).click();
//                logger.info(String.format("Удаляем контакт № %s", i));
//            } catch (Exception ex) {
//                logger.error(String.format("При удалении контакта № %s произошла ошибка", i), ex);
//            }
//        }


        driver.findElement(By.cssSelector("button.js-lk-cv-custom-select-add")).click();
        logger.info("Добавить контакт");

        makeClick("//span[contains(text(),'Способ связи')]", "Способ связи");

        selectCommunicationWay("//div[@class ='lk-cv-block__select-options " +
                "lk-cv-block__select-options_left js-custom-select-options-container']/descendant::button[@title='Viber']", "Viber");

        findElemSetValue("//input[@id='id_contact-0-value']", "+7 966 666-66-66", "Viber контакт");
        makeClick("//input[@id='id_contact-0-value']", "выбор способа связи");

        driver.findElement(By.cssSelector("button.js-lk-cv-custom-select-add")).click();
        logger.info("Добавить контакт");

        makeClick("//span[contains(text(),'Способ связи')]", "способ связи");
        selectCommunicationWay("//div[@class ='lk-cv-block__select-scroll " +
                "lk-cv-block__select-scroll_service js-custom-select-options']/child::button[@title ='Skype']", "Skype");

        findElemSetValue("//input[@id='id_contact-1-value']", "Test_login", "логин Skype");

        makeClick("//input[@id='id_contact-1-value']", "способ связи");
    }

    private void selectCommunicationWay(String locator, String name) {
        List<WebElement> communicationWays = driver.findElements(By.xpath(locator));
        communicationWays.get(1).click();
        logger.info(String.format("Выбираем способ связи %s"), name);
    }
}
