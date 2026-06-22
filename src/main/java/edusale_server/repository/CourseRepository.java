package edusale_server.repository;


import edusale_server.model.Course;

import java.util.List;

public interface CourseRepository {
    List<Course> findAll();
    Course findById(Long id);
    Course save(Course course);
}