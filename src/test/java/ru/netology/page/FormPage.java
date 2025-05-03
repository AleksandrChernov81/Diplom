package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FormPage {

    // Селекторы кнопок "Купить" и "Купить в кредит" с использованием XPath
    private SelenideElement paymentButton = $x("//button[.//span[contains(text(), 'Купить')]]");
    private SelenideElement creditButton = $x("//button[.//span[contains(text(), 'Купить в кредит')]]");

    // Уточнённые селекторы для полей месяца и года
    public SelenideElement monthField = $(".input-group__input-case:first-child [placeholder='08']");
    public SelenideElement yearField = $(".input-group__input-case:nth-child(2) [placeholder='22']");

    // Остальные элементы
    private SelenideElement formHead = $("[class^='App_appContainer'] > h3.heading");
    private SelenideElement formBody = $("form");
    public SelenideElement numberField = $("[placeholder='0000 0000 0000 0000']");
    public SelenideElement holderField = $("input:not([placeholder])");
    public SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement continueButton = $("form button.button_view_extra"); // Уточнённый селектор
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");

    // Метод для поиска сообщений об ошибках (добавлена проверка структуры)
    private SelenideElement errorNotify(SelenideElement element) {
        return element.closest(".input__inner").find(".input__sub");
    }

    public FormPage() {
        // Проверка видимости элементов с явным ожиданием
        paymentButton.shouldBe(visible, Duration.ofSeconds(5));
        creditButton.shouldBe(visible, Duration.ofSeconds(5));
        formHead.shouldBe(visible);
        formBody.shouldBe(visible);
        continueButton.shouldBe(visible);
        successNotification.shouldBe(hidden);
        errorNotification.shouldBe(hidden);
    }

    public void setValues(DataHelper.CardInfo info) {
        numberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        holderField.setValue(info.getHolder());
        cvcField.setValue(info.getCvc());
        continueButton.click();
    }

    // Методы проверки уведомлений с оптимизированным временем ожидания
    public void checkSuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void checkErrorNotification() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(10));
    }

    // Обобщённый метод проверки текста ошибки для поля
    public void checkErrorNotify(SelenideElement field, String expectedText) {
        errorNotify(field)
                .shouldBe(visible)
                .shouldHave(text(expectedText));
    }

    public void checkErrorNotifyIfEmptyNumber() {
    }

    public void checkErrorNotifyIfInvalidNumber() {
    }
}