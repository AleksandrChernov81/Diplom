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
        SQLHelper.clean();
    }

    @Test
    void shouldSucceedIfApprovedCardAndValidData() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldBeRejectedIfDeclinedCardAndValidData() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidDeclinedCard();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotification();
        SQLHelper.verifyPaymentStatus("DECLINED");
    }

    @Test
    void shouldSucceedIfCurrentMonthAndCurrentYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithCurrentMonthAndCurrentYear();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfExpirationDateIs4YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithPlus4YearsFromCurrent();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfExpirationDateIs5YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithPlus5YearsFromCurrent();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfHyphenatedHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithHyphenatedName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.verifyPaymentStatus("APPROVED");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyNumber() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardNumberEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.numberField, "Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains15Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith15Digits();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.numberField, "Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains17Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith17Digits();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.numberField, "Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains16RandomDigits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith16RandomDigits();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotification();
    }

    @Test
    void shouldGetErrorNotifyIfNumberContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithLettersInNumber();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.numberField, "Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardMonthEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.monthField, "Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs00() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith00Month();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.monthField, "Неверно указан срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs13() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith13thMonth();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.monthField, "Неверно указан срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfExpiredMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredMonth();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.monthField, "Истёк срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardYearEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.yearField, "Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfExpirationDateIs6YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardWithPlus6YearsFromCurrent();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.yearField, "Неверно указан срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfExpiredYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredYear();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.yearField, "Истёк срок действия карты");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyHolder() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardHolderEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.holderField, "Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfCyrillicHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithCyrillicName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.holderField, "Неверный формат");
    }

    @Test
    void shouldGetErrorIfHolderNameContainsDigits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithDigitsInName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.holderField, "Неверный формат");
    }

    @Test
    void shouldGetErrorIfHolderNameContainsSpecialSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithSpecialSymbolsInName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.holderField, "Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyCVC() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardCVCEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.cvcField, "Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorNotifyIfCVCContains2Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWith2Digits();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.cvcField, "Неверный формат");
    }

    @Test
    void shouldGetErrorNotifyIfCVCContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWithLetters();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotify(formPage.cvcField, "Неверный формат");
    }
}