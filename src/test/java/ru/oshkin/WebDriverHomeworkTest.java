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

import java.util.List;
import java.util.concurrent.TimeUnit;

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

        WebElement userButton3 = driver.findElement(By.xpath("//input [@name ='fname']"));
        userButton3.clear();
        userButton3.sendKeys("Жора");
        logger.info("Очищаем поле имя и вводим новое имя");

        WebElement userButton4 = driver.findElement(By.xpath("//input [@name ='fname_latin']"));
        userButton4.clear();
        userButton4.sendKeys("George");
        logger.info("Очищаем поле имя и вводим новое имя");

        WebElement userButton5 = driver.findElement(By.xpath("//input [@name ='lname']"));
        userButton5.clear();
        userButton5.sendKeys("Иванов");
        logger.info("Очищаем поле фамилия и вводим новую фамилию");

        WebElement userButton6 = driver.findElement(By.xpath("//input [@name ='lname_latin']"));
        userButton6.clear();
        userButton6.sendKeys("Ivanov");
        logger.info("Очищаем поле фамилия и вводим новую фамилию");

        WebElement userButton7 = driver.findElement(By.xpath("//input [@name ='blog_name']"));
        userButton7.clear();
        userButton7.sendKeys("Жора");
        logger.info("Очищаем поле имя блога и вводим новое имя блога");

        WebElement userButton8 = driver.findElement(By.xpath("//input [@name ='date_of_birth']"));
        userButton8.clear();
        userButton8.sendKeys("13.11.2001");
        logger.info("Очищаем поле дата рождения и вводим новую дату рождения");

        WebElement calendarInput = driver.findElement(By.xpath("//input[@title='День рождения']"));
        calendarInput.click();
        logger.info("Закрываем всплывающее окно календарь");

        WebElement countryInput = driver.findElement(By.xpath("//div[@class ='input input_full lk-cv-block__input " +
                "lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation']"));
        countryInput.click();
        logger.info("Открываем выпадающий список стран");

        WebElement selectedCountry = driver.findElement(By.xpath("//button[@title='Узбекистан']"));
        selectedCountry.click();
        logger.info("Выбираем страну");

        WebElement cityInput = driver.findElement(By.xpath("//div[@class ='input input_full lk-cv-block__input" +
                " lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation']/child::span[contains(text(),'Город')]"));
        cityInput.click();
        logger.info("Открываем выпадающий список городов");

        WebElement selectedCity = driver.findElement(By.xpath("//button[@title='Бухара']"));
        selectedCity.click();
        logger.info("Выбираем город");

        WebElement languageInput = driver.findElement(By.xpath("//input[@data-title ='Уровень знания английского языка']/following-sibling::div"));
        languageInput.click();
        logger.info("Открываем выпадающий список уровней знания английского языка");

        WebElement selectedLanguageLevel = driver.findElement(By.xpath("//button[@title='Начальный уровень (Beginner)']"));
        selectedLanguageLevel.click();
        logger.info("Выбираем уровень знания английского языка");

        WebElement phoneModalWindow = driver.findElement(By.xpath("//button[contains(text(),'Указать телефон')]"));
        phoneModalWindow.click();
        logger.info("Открываем модальное окно с формой ввода телефона");

        WebElement phoneInput = driver.findElement(By.xpath("//Input[@name ='phone' and @placeholder ='Номер телефона']"));
        phoneInput.clear();
        phoneInput.sendKeys("+7 911 111-11-11");
        logger.info("Вводим номер телефона");

        WebElement sendButton = driver.findElement(By.xpath("//button[contains(text(),'Отправить')]"));
        sendButton.click();
        logger.info("Нажимаем кнопку отправить");

        WebElement clouseButton = driver.findElement(By.xpath("//div[@class ='modal__close ic-close js-close-modal']"));
        clouseButton.click();
        logger.info("Нажимаем кнопку закрыть модальное окно");

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

        WebElement wayCommunication = driver.findElement(By.xpath("//span[contains(text(),'Способ связи')]"));
        wayCommunication.click();
        logger.info("Открываем выбор способа связи");

        List<WebElement> elements = driver.findElements(By.xpath("//div[@class ='lk-cv-block__select-options " +
                "lk-cv-block__select-options_left js-custom-select-options-container']/descendant::button[@title='Viber']"));
        elements.get(0).click();
        logger.info("Выбираем способ связи Viber");

        WebElement firstContactInput = driver.findElement(By.xpath("//input[@id='id_contact-0-value']"));

        firstContactInput.sendKeys("+7 966 666-66-66");
        logger.info("Заполняем поле ввода Viber");

        WebElement firstContactInput3 = driver.findElement(By.xpath("//input[@id='id_contact-0-value']"));
        firstContactInput3.click();
        logger.info("Закрываем поле выбора способа связи");

        driver.findElement(By.cssSelector("button.js-lk-cv-custom-select-add")).click();
        logger.info("Добавить контакт");

        WebElement wayCommunication2 = driver.findElement(By.xpath("//span[contains(text(),'Способ связи')]"));
        wayCommunication2.click();
        logger.info("Открываем выбор способа связи");

        List<WebElement> elements2 = driver.findElements(By.xpath("//div[@class ='lk-cv-block__select-scroll" +
                " lk-cv-block__select-scroll_service js-custom-select-options']/child::button[@title ='Skype']"));
        elements2.get(1).click();
        logger.info("Выбираем способ связи Skype");

        WebElement firstContactInput2 = driver.findElement(By.xpath("//input[@id='id_contact-1-value']"));
        firstContactInput2.sendKeys("Test_login");
        logger.info("Заполняем поле ввода Skype");

        WebElement firstContactInput4 = driver.findElement(By.xpath("//input[@id='id_contact-1-value']"));
        firstContactInput4.click();
        logger.info("Закрываем поле выбора способа связи");

        WebElement saveButton = driver.findElement(By.xpath("//button[@title ='Сохранить и продолжить']"));
        saveButton.click();
        logger.info("Нажимаем кнопку сохранить и продолжить");
    }
}
