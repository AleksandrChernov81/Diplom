# Отчёт по итогам тестирования
Отчёт по результату автоматизированного тестированию функционала покупки и оформления кредита по данным карты веб-сервиса покупки тура
"Путешествие дня".

### Краткое описание.
Выполнена автоматизация тестирования веб-сервиса "Путешествие дня". В ходе тестирования были проверены:

1) Возможность оплаты двумя способами (покупка по карте и покупка в кредит);
2) Взаимодействие с банковскими сервисами;
3) Взаимодействие с СУБД (MySQL и PostgreSQL);
4) Обработка ответа и демонстрация соответствующих уведомлений;
5) Выдача сообщений об ошибках при неверном заполнении формы.
## Количество тест-кейсов
Всего было проведено 50 автотестов. Процент успешных тестов с PostgreSQL равен 50%.
Из них успешных — 25, неуспешных — 25.
![p2](https://github.com/user-attachments/assets/14a699ce-5b8a-4be8-b937-0c196b628b0d)
Процент успешных тестов с MySQL равен 52%
Из них успешных — 26, неуспешных — 24.
![mysql](https://github.com/user-attachments/assets/ac86a633-912c-440b-95e6-742b2311adac)

В результате прогона тестов было составлено 9 [issue](https://github.com/AleksandrChernov81/Diplom/issues).

### Общие рекомендации.
1) Устранить найденные [ошибки](https://github.com/AleksandrChernov81/Diplom/issues)
2) Добавить в код страницы специально подготовленные CSS атрибуты для тестирования (data-test-id).
3) Подсказки об ошибках при заполнении полей формы должны быть более информативные и приведены к единому формату.
