<<<<<<< HEAD:Readme.md
# Дипломный проект по профессии «Тестировщик»
=======
# Дипломный проект профессии «Тестировщик»

## Запуск SUT, авто-тестов и генерация репорта

### Подключение SUT к PostgreSQL

1. Запустить Docker Desktop
1. Открыть проект в IntelliJ IDEA
1. В терминале в корне проекта запустить контейнеры:

   `docker-compose up -d`
1. Запустить приложение:

   `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5433/app`
1. Открыть второй терминал
1. Запустить тесты:

   `.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5433/app`
1. Создать отчёт Allure и открыть в браузере

   `.\gradlew allureServe`
1. Закрыть отчёт:

   **CTRL + C -> y -> Enter**
1. Перейти в первый терминал
1. Остановить приложение:

   **CTRL + C**
1. Остановить контейнеры:

   `docker-compose down`
   </a>

### Подключение SUT к MySQL

1. Запустить Docker Desktop
1. Открыть проект в IntelliJ IDEA
1. В терминале в корне проекта запустить контейнеры:

   `docker-compose up -d`
1. Запустить приложение:

   `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3307/app`
1. Открыть второй терминал
1. Запустить тесты:

   `.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3307/app`
1. Создать отчёт Allure и открыть в браузере

   `.\gradlew allureServe`
1. Закрыть отчёт:

   **CTRL + C -> y -> Enter**
1. Перейти в первый терминал
1. Остановить приложение:

   **CTRL + C**
1. Остановить контейнеры:

   `docker-compose down`
   </a>
>>>>>>> 41c84875dab0623f144608fb17a067cbac771eff:Running tests.md

- [План тестирования]()
- [Запуск тестов]()
- [Отчет]()