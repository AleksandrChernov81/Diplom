// PaymentTest.java
package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.*;

public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @AfterEach
    public void teardrop() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldSucceedIfApprovedCardAndValidData() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getValidApprovedCard());
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldBeRejectedIfDeclinedCardAndValidData() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getValidDeclinedCard());
        formPage.checkErrorNotification();
        SQLHelper.verifyPaymentStatus("DECLINED");
    }

    @Test
    void shouldSucceedIfCurrentMonthAndCurrentYear() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getValidCardWithCurrentMonthAndCurrentYear());
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfExpirationDateIs4YearsFromCurrent() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getValidCardWithPlus4YearsFromCurrent());
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfExpirationDateIs5YearsFromCurrent() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getValidCardWithPlus5YearsFromCurrent());
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfHyphenatedHolderName() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getValidCardWithHyphenatedName());
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyNumber() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getCardInfoCardNumberEmpty());
        formPage.checkNumberFieldError("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains15Digits() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWith15Digits());
        formPage.checkNumberFieldError("Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains17Digits() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWith17Digits());
        formPage.checkNumberFieldError("Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains16RandomDigits() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWith16RandomDigits());
        formPage.checkErrorNotification();
        SQLHelper.verifyPaymentStatus("DECLINED");
    }

    @Test
    void shouldGetErrorNotifyIfNumberContainsLetters() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWithLettersInNumber());
        formPage.checkNumberFieldError("Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyMonth() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getCardInfoCardMonthEmpty());
        formPage.checkMonthFieldError("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs00() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWith00Month());
        formPage.checkMonthFieldError("Неверно указан срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs13() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWith13thMonth());
        formPage.checkMonthFieldError("Неверно указан срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfExpiredMonth() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWithExpiredMonth());
        formPage.checkMonthFieldError("Истёк срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyYear() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getCardInfoCardYearEmpty());
        formPage.checkYearFieldError("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfExpirationDateIs6YearsFromCurrent() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getCardWithPlus6YearsFromCurrent());
        formPage.checkYearFieldError("Неверно указан срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfExpiredYear() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWithExpiredYear());
        formPage.checkYearFieldError("Истёк срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyHolder() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getCardInfoCardHolderEmpty());
        formPage.checkHolderFieldError("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfCyrillicHolderName() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWithCyrillicName());
        formPage.checkHolderFieldError("Неверный формат");
    }

    @Test
    void shouldGetErrorIfHolderNameContainsDigits() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWithDigitsInName());
        formPage.checkHolderFieldError("Неверный формат");
    }

    @Test
    void shouldGetErrorIfHolderNameContainsSpecialSymbols() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCardWithSpecialSymbolsInName());
        formPage.checkHolderFieldError("Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyCVC() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getCardInfoCardCVCEmpty());
        formPage.checkCvcFieldError("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfCVCContains2Digits() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCVCWith2Digits());
        formPage.checkCvcFieldError("Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfCVCContainsLetters() {
        var mainPage = new MainPage();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(DataHelper.getInvalidCVCWithLetters());
        formPage.checkCvcFieldError("Неверный формат");
    }
}