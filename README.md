# EduSale Server

Бэкенд платформы онлайн-обучения. Лёгкий REST-сервис на Spring Boot, отвечающий за аутентификацию пользователей и выдачу каталога курсов.

## Технологии

- Java 17, Spring Boot 4.1, Spring Security
- Spring Data JPA, Hibernate 7.4, SQLite 3.47
- JWT (jjwt 0.12)

## Быстрый старт

- Запустите сервер:

```bash
./mvnw spring-boot:run
```

- Либо запустите класс `Application.java` из IDE.

- Сервер доступен по адресу `http://localhost:8080`.

При первом старте `DataInitializer` автоматически создаст базу `edusale.db`, администратора `test@example.com` / `test1234` и три демонстрационных курса.

## Настройка окружения

Файл `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:sqlite:edusale.db
    driver-class-name: org.sqlite.JDBC
    hikari:
      maximum-pool-size: 1
  jpa:
    database-platform: org.hibernate.community.dialect.SQLiteDialect
    hibernate:
      ddl-auto: update

jwt:
  secret: ${JWT_SECRET:your_super_secret_key_32_bytes_long_minimum!!!}
```

## API

### Аутентификация (публичный доступ)

| Метод | Эндпоинт | Описание |
| :--- | :--- | :--- |
| POST | `/api/auth/register` | Регистрация нового аккаунта |
| POST | `/api/auth/login` | Вход, выдача токенов |
| POST | `/api/auth/refresh` | Обновление Access Token (`?refreshToken=`) |

### Курсы (требуется `Authorization: Bearer <token>`)

| Метод | Эндпоинт | Описание |
| :--- | :--- | :--- |
| GET | `/api/courses` | Список всех доступных курсов |
| GET | `/api/courses/{id}` | Информация о курсе по ID |

### Пример входа

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test1234"}'
```

Ответ (`200 OK`):

```json
{
  "accessToken": "eyJhbGciOi...",
  "refreshToken": "eyJhbGciOi...",
  "tokenType": "Bearer"
}
```

Ошибка (`400` / `401`):

```json
{
  "status": 400,
  "message": "Неверный пароль или email",
  "timestamp": "2026-06-22T13:42:06"
}
```



## Контакты

Разработчик: krp77
E-mail: krp77@mail.ru
