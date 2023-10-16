# exchange-rate-provider

## Инструментарий проектирования 
Java 17, Spring Boot 3.1.4. База данных PostgreSQL.

## Описание приложения
Приложение представляет собой веб-сервис для получения информации о курсах валют с сайта НБ РБ.
Сервис имеет две осноные возможности:
- Получение и сохранение в базу данных списка курсов валют на заданную пользователем дату.
- Получение информации о конкретном курсе валюты на заданную пользователем дату по коду валюты, где также отображается информация об изменении курса по сравнению с предыдущим рабочим днем.

Ответ на каждый запрос приложению подписывается добавлением CRC32 тела ответа в заголовок.

## Инструкция по запуску проекта

При разработке приложение использовалась IDE IntelliJ IDEA, поэтому запуск описывается с ее помощью. 

1) Создать локальную копию данного удаленного репозитория при помощи команды git clone или через панель Version Control в IntelliJ Idea.
2) Установить соответствующий дистрибутив PostgreSQL в зависимости от используемой операционной системы. Для удобства работы с базой данных рекомендуется дополнительно установить визуальный SQL редактор PgAdmin 4.
> Пункт номер 2 можно пропустить, если соответствующие инструменты уже предустановлены
3) Создание базы данных: 

    3.1 В программе PgAdmin раскрыть контекстное меню для сервера PostgreSQL 14 —> выбрать пункт Create —> Database... В открывшемся окне необходимо ввести название базы rate_db и выбрать пользователя. В данном примере используется пользователь с именем postgres и паролем root.

    3.2 Альтернативный способ создания через терминал:

``` CREATE DATABASE {название базы данных}; ```

``` CREATE USER {имя пользователя} WITH PASSWORD {пароль}; ```

``` GRANT ALL PRIVILEGES ON DATABASE {название базы данных} to {имя пользователя}; ```
 
4) Если была создана база данных с названием, отличным от предложенного rate_db, в файле application.properties проекта необходимо установить свои значения datasource.url, datasource.username, datasource.password.
5) Запуск проекта осуществляется при помощи панели инструментов IntelliJ Idea.

## Документация к проекту

API проекта описан при помощи набора инструментов Swagger. Документация доступна по ссылке http://localhost:8080/swagger-ui/index.html после локального запуска проекта.
Описание доступных endpoint'ов:
- Endpoint для получения списка валют на указанную дату
![](https://github.com/ParkhomenkoArtyom/exchange-rate-provider/raw/master/image/SwaggerDoc1.png)
- Endpoint для получения информации о конткретной валюте на указанную дату по коду валюты
![](https://github.com/ParkhomenkoArtyom/exchange-rate-provider/raw/master/image/SwaggerDoc2.png)
