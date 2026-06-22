
# 🎓 EduSale (Сервер)
Платформа онлайн-обучения с открытым исходным кодом. Включает нативное мобильное приложение (Android) и легковесный бэкенд (Spring Boot).
## 🏗 Архитектура системы
```
─────────────────────┐ HTTP / JSON ┌───────────────────────┐
│ Android App │ ──────────────────────────────────────────────> │ Spring Boot │
│ (Compose + Koin) │ ◄────────────────────────────────────────────── │ (Security + JPA) │
└───────────────────────┘ Bearer JWT / Refresh Token └───────────────────────┘
│ │
▼ Local Storage ▼ Database
┌───────────────────────┐ ┌───────────────────────┐
│ Room Database │ │ SQLite Database │
│ (Избранное) │ │ (Пользователи/Курсы) │

```


## 🛠 Технологический стек

### Бэкенд (Сервер)
*   **Сборщик:** Maven
*   **Core:** Java 21, Spring Boot 4.1, Spring Security
*   **Data:** Spring Data JPA, Hibernate 7.4, SQLite 3.47
*   **Auth:** JWT (jjwt 0.12)

### Клиент (Android)
*   **Сборщик:** Gradle
*   **UI:** Kotlin, Jetpack Compose, Material 3, Navigation Compose
*   **DI / Network:** Koin 4.0, Retrofit 2.11, OkHttp 4.12
*   **Async / Storage:** Kotlin Coroutines & Flows, Room 2.8, Coil 2.6 (Images)



## 🚀 Быстрый старт

### 1. Подготовка и запуск сервера

Для работы с SQLite в многопоточном режиме Spring Boot убедитесь, что в `application.yml` выставлен лимит пула соединений (максимум 1 соединение на запись для избежания `database is locked`).

#### Настройка окружения (`src/main/resources/application.yml`):
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
  secret: \${JWT_SECRET:your_super_secret_key_32_bytes_long_minimum!!!}
```

#### Запуск через Maven:
1. Выполните сборку и запуск: `./mvnw spring-boot:run` или запустите `Application.java` из IDE.
2. Сервер будет доступен по адресу: `http://localhost:8080`.
3. При первом старте `DataInitializer` автоматически создаст:
  * Файл БД `edusale.db`
  * Администратора: `test@example.com` / `test1234`
  * 3 демонстрационных курса

### 2. Запуск мобильного приложения

1. Откройте директорию `edusale/` в **Android Studio**.
2. Дождитесь окончания `Gradle Sync`.
3. Выберите таргет и нажмите **Run 'app'**.

> 💡 **Важно про сетевую конфигурацию:**
> * **Эмулятор Android:** Автоматически перенаправляет запросы на `10.0.2.2:8080`. В коде конфигурация уже настроена.
> * **Реальное устройство:** Измените базовый URL в `core/network/di/networkModule.kt` на локальный IP вашего ПК (например, `192.168.1.50`). Добавьте этот IP в `core/network/res/xml/network_security_config.xml` для разрешения HTTP-трафика.



## 🔑 Бизнес-правила и Безопасность

*   **Валидация паролей:** Строго от 7 до 100 символов.
*   **Валидация Email:** Регулярное выражение (RegEx) проверяется на клиенте перед отправкой и дублируется аннотацией `@Email` на сервере.
*   **Время жизни токенов:**
  *   `Access Token`: **15 минут** (короткий срок для безопасности).
  *   `Refresh Token`: **7 дней** (хранится в HttpOnly Cookie на сервере или защищенном хранилище Android EncryptedSharedPreferences).



## 📑 Спецификация API

### 1. Модуль аутентификации (Публичный)

| Метод | Эндпоинт | Описание |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Регистрация нового аккаунта |
| `POST` | `/api/auth/login` | Аутентификация, выдача токенов |
| `POST` | `/api/auth/refresh` | Обновление Access Token по Query-параметру `?refreshToken=` |

### 2. Модуль курсов (Приватный — требуется заголовок `Authorization: Bearer <token>`)

| Метод | Эндпоинт | Описание |
| :--- | :--- | :--- |
| `GET` | `/api/courses` | Получить список всех доступных курсов |
| `GET` | `/api/courses/{id}` | Получить детальную информацию о курсе по ID |


### Примеры взаимодействия

#### Запрос на авторизацию (Авторизован):
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test1234"}'
```

#### Успешный ответ (`200 OK`):
```json
{
  "accessToken": "eyJhbGciOi...",
  "refreshToken": "eyJhbGciOi...",
  "tokenType": "Bearer"
}
```

#### Обработка ошибок сервера (`400 Bad Request` / `401 Unauthorized`):
```json
{
  "status": 400,
  "message": "Неверный пароль или email",
  "timestamp": "2026-06-22T13:42:06"
}
```


## 📂 Структура исходного кода

### Бэкенд Архитектура (Maven)
```
├── pom.xml # Спецификация зависимостей Maven
└── src/main/java/edusale_server/
├── Application.java # Точка входа Spring Boot
├── config/ # Конфигурации (Security, CORS, Web)
├── controller/ # REST-контроллеры (Валидация входящих DTO)
├── dto/ # Объекты обмена данными (Request/Response)
├── exception/ # @ControllerAdvice для унификации ошибок
├── model/ # Сущности БД (User, Course)
├── repository/ # Репозитории Spring Data JPA
├── security/ # Фильтры, JwtProvider, CustomUserDetailsService
└── service/ # Бизнес-логика приложения
```

### Архитектура клиента (Clean Architecture + Feature-First)
```
edusale/
├── app/ # Application класс, глобальная навигация, Koin-модули
├── core/ # Шаринг-модули без бизнес-логики
│ ├── common/ # Мапперы, расширения, UiText для локализации
│ ├── database/ # Room: AppDatabase, Entity, Dao-интерфейсы
│ └── network/ # Retrofit-клиент, ОkHttp-интерцепторы (JWT Authenticator)
├── domain/ # Чистая бизнес-логика (Модели, UseCases, Repositories Interfaces)
├── data/ # Реализация репозиториев (Сеть + Локальный кэш Room)
└── feature/ # Независимые фичи приложения
├── auth/ # Авторизация (Screens, ViewModels, UI State)
├── courses/ # Каталог курсов
├── favorites/ # Экран избранного (Интеграция с Room)
└── account/ # Профиль пользователя
```



## ✉️ Контакты и поддержка

Разработчик: **krp77**  
E-mail для связи: [krp77@mail.ru](mailto:krp77@mail.ru)




