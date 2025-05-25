// FormPage.java
package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FormPage {
    private final SelenideElement paymentButton = $x("//button[.//span[contains(text(), 'Купить')]]");
    private final SelenideElement creditButton = $x("//button[.//span[contains(text(), 'Купить в кредит')]]");
    private final SelenideElement formHead = $("[class^='App_appContainer'] > h3.heading");
    private final SelenideElement formBody = $("form");
    private final SelenideElement numberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement yearField = $("input[placeholder='22']");
    private final SelenideElement holderField = $("input:not([placeholder])");
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement continueButton = $("form button.button_view_extra");
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

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

    public void checkNumberFieldError(String expectedText) {
        getErrorElement(numberField).shouldHave(text(expectedText));
    }

    public void checkMonthFieldError(String expectedText) {
        getErrorElement(monthField).shouldHave(text(expectedText));
    }

    public void checkYearFieldError(String expectedText) {
        getErrorElement(yearField).shouldHave(text(expectedText));
    }

    public void checkHolderFieldError(String expectedText) {
        getErrorElement(holderField).shouldHave(text(expectedText));
    }

    public void checkCvcFieldError(String expectedText) {
        getErrorElement(cvcField).shouldHave(text(expectedText));
    }

    private SelenideElement getErrorElement(SelenideElement field) {
        return field.closest(".input__inner").find(".input__sub")
                .shouldBe(visible);
    }
}