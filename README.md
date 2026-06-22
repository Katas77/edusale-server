
# EduSale

Платформа онлайн-курсов: мобильное приложение (Android) + серверная часть (Spring Boot).

## Архитектура
```
┌─────────────────┐         HTTP/JSON + JWT          ┌─────────────────┐
│                 │  ──────────────────────────────► │                 │
│  Android App    │                                  │  Spring Boot    │
│  (Compose/Koin) │  ◄────────────────────────────── │  (SQLite/JWT)   │
│                 │                                  │                 │
└─────────────────┘                                  └─────────────────┘
        │                                                    │
        │ Room (локально)                                    │ SQLite
        ▼                                                    ▼
   Избранное                                            Пользователи Курсы

```
## Быстрый старт

### 1. Сервер
Сервер запустится на http://localhost:8080.
При первом запуске создаются:
- БД edusale.db
- Тестовый пользователь: test@example.com / test1234
- 3 тестовых курса

### 2. Мобильное приложение

1. Открыть проект в Android Studio
2. Sync Gradle Files
3. Run -> Run 'app'

Эмулятор обращается к серверу через 10.0.2.2:8080 (настроено автоматически).
Реальное устройство — заменить URL в core/network/di/networkModule.kt на IP компьютера в локальной сети и добавить его в network_security_config.xml.

## API

### Аутентификация (публичные)

| Метод | URL                              | Описание          |
|-------|----------------------------------|-------------------|
| POST  | /api/auth/register               | Регистрация       |
| POST  | /api/auth/login                  | Вход              |
| POST  | /api/auth/refresh?refreshToken=  | Обновление токена |

### Курсы (требуется Authorization: Bearer <token>)

| Метод | URL                  | Описание      |
|-------|----------------------|---------------|
| GET   | /api/courses         | Список курсов |
| GET   | /api/courses/{id}    | Курс по ID    |

### Пример: вход

curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test1234"}'

Ответ:

{
  "accessToken": "eyJhbGciOi...",
  "refreshToken": "eyJhbGciOi...",
  "tokenType": "Bearer"
}

### Формат ошибки

{
  "message": "Неверный пароль",
  "timestamp": "2026-06-22T13:42:06",
  "status": 400
}

## Стек

### Сервер
- Spring Boot 4.1
- Spring Security + JWT (jjwt 0.12)
- Spring Data JPA + Hibernate 7.4
- SQLite 3.47
- Java 21

### Клиент
- Kotlin, Jetpack Compose, Material 3
- Koin 4.0 (DI)
- Retrofit 2.11 + OkHttp 4.12
- Room 2.8 (локальное хранение избранного)
- Coil 2.6 (загрузка изображений)
- Navigation Compose

## Структура проекта

### Сервер
```
src/main/java/edusale_server/
  ├── Application.java
  ├── config/          # SecurityConfig, DataInitializer
  ├── controller/      # AuthController, CourseController
  ├── dto/             # Request/Response классы
  ├── exception/       # GlobalExceptionHandler
  ├── model/           # User, Course
  ├── repository/      # JPA репозитории
  ├── security/        # JwtTokenProvider, JwtAuthenticationFilter
  └── service/         # AuthService, CourseService
```
### Клиент
```
edusale/
  ├── app/             # Точка входа, навигация, DI модули
  ├── core/
  │   ├── common/      # Утилиты (DateMapper, UiText)
  │   ├── database/    # Room: AppDatabase, FavoriteDao
  │   └── network/     # Retrofit, TokenManager, AuthInterceptor
  ├── domain/          # Модели, интерфейсы репозиториев, UseCase
  ├── data/            # Реализации репозиториев
  └── feature/
      ├── auth/        # LoginScreen, RegisterScreen
      ├── courses/     # Список курсов
      ├── favorites/   # Избранное
      └── account/     # Профиль
```
## Правила

- Пароль: от 7 до 100 символов
- Email: валидация формата на клиенте и сервере
- Access token: 24 часа
- Refresh token: 7 дней


✉ **Обратная связь**: [krp77@mail.ru](mailto:krp77@mail.ru)
