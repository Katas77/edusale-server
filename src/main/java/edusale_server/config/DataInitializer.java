package edusale_server.config;

import edusale_server.entity.Course;
import edusale_server.entity.User;
import edusale_server.repository.CourseRepository;
import edusale_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) {
        if (!userRepository.existsByEmail("test@example.com")) {
            User testUser = User.builder()
                    .email("test@example.com")
                    .password(passwordEncoder.encode("test123"))
                    .name("Тестовый Пользователь")
                    .build();
            userRepository.save(testUser);
            System.out.println("✅ Тестовый пользователь создан: test@example.com / test123");
        }

        if (courseRepository.findAll().isEmpty()) {
            Course course1 = Course.builder()
                    .title("Java для начинающих")
                    .text("Полный курс по Java от основ до продвинутого уровня.")
                    .price("5000")
                    .rate("4.8")
                    .startDate(LocalDate.of(2026, 7, 1))
                    .publishDate(LocalDate.of(2026, 6, 15))
                    .imageUrl("https://ui-avatars.com/api/?name=Java+Course&background=4CAF50&color=fff&size=400")
                    .build();

            Course course2 = Course.builder()
                    .title("UX/UI Дизайн")
                    .text("Научитесь создавать красивые и удобные интерфейсы.")
                    .price("7000")
                    .rate("4.9")
                    .startDate(LocalDate.of(2026, 8, 1))
                    .publishDate(LocalDate.of(2026, 6, 10))
                    .imageUrl("https://ui-avatars.com/api/?name=Design+Course&background=2196F3&color=fff&size=400")
                    .build();

            Course course3 = Course.builder()
                    .title("Digital Маркетинг")
                    .text("Стратегии продвижения в интернете.")
                    .price("6000")
                    .rate("4.7")
                    .startDate(LocalDate.of(2026, 7, 15))
                    .publishDate(LocalDate.of(2026, 6, 20))
                    .imageUrl("https://ui-avatars.com/api/?name=Marketing&background=FF9800&color=fff&size=400")
                    .build();

            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);

            System.out.println("Initial courses loaded!");
        }
    }
}
