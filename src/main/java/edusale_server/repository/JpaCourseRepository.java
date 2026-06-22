package edusale_server.repository;


import edusale_server.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCourseRepository extends JpaRepository<CourseEntity, Long> {
}