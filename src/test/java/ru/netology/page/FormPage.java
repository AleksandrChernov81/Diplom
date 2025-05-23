package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FormPage {

    private SelenideElement paymentButton = $x("//button[.//span[contains(text(), 'Купить')]]");
    private SelenideElement creditButton = $x("//button[.//span[contains(text(), 'Купить в кредит')]]");
    private SelenideElement monthField = $(".input-group__input-case:first-child [placeholder='08']");
    private SelenideElement yearField = $(".input-group__input-case:nth-child(2) [placeholder='22']");
    private SelenideElement formHead = $("[class^='App_appContainer'] > h3.heading");
    private SelenideElement formBody = $("form");
    private SelenideElement numberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement holderField = $("input:not([placeholder])");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement continueButton = $("form button.button_view_extra");
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");

    private SelenideElement getErrorElement(SelenideElement field) {
        return field.closest(".input__inner").find(".input__sub");
    }

    public FormPage() {
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

    public void checkSuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void checkErrorNotification() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void checkFieldError(String fieldName, String expectedError) {
        SelenideElement field;
        switch (fieldName.toLowerCase()) {
            case "number":
                field = numberField;
                break;
            case "month":
                field = monthField;
                break;
            case "year":
                field = yearField;
                break;
            case "holder":
                field = holderField;
                break;
            case "cvc":
                field = cvcField;
                break;
            default:
                throw new IllegalArgumentException("Неизвестное поле: " + fieldName);
        }
        getErrorElement(field)
                .shouldBe(visible)
                .shouldHave(text(expectedError));
    }
}