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

1. Склонировать проект из данного репозитория.
2. Если на устройстве имеется PostgreSQL, то необходимо создать базу данных для запуска приложение. В данном примере используется БД под названием rate_db с именем пользователя - postgres и паролем - root.
3. Если PostgreSQL не установлена, то изначально придется ее установить и повторить пункт 2 иначе приложение не запустится.
4. В случае названия созданной БД отличного от примера, необходится открыть файл application.properties и установить свои значения datasource.url, datasource.username, datasource.password.
5. Запустить проект.

## Документация к проекту.

Документация сервиса выполнена с помощью Swagger. После запуска проекта открыть ее можно по ссылке http://localhost:8080/swagger-ui/index.html.