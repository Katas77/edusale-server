package edusale_server.service.impl;


import edusale_server.entity.CourseEntity;
import edusale_server.repository.JpaCourseRepository;
import edusale_server.model.Course;
import edusale_server.repository.CourseRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    
    private final JpaCourseRepository jpaCourseRepository;
    
    public CourseRepositoryImpl(JpaCourseRepository jpaCourseRepository) {
        this.jpaCourseRepository = jpaCourseRepository;
    }
    
    @Override
    public List<Course> findAll() {
        return jpaCourseRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Course findById(Long id) {
        return jpaCourseRepository.findById(id)
                .map(this::toDomain)
                .orElse(null);
    }
    
    @Override
    public Course save(Course course) {
        CourseEntity entity = toEntity(course);
        CourseEntity saved = jpaCourseRepository.save(entity);
        return toDomain(saved);
    }
    
    private CourseEntity toEntity(Course course) {
        CourseEntity entity = new CourseEntity();
        entity.setId(course.getId());
        entity.setTitle(course.getTitle());
        entity.setText(course.getText());
        entity.setPrice(course.getPrice());
        entity.setRate(course.getRate());
        entity.setStartDate(course.getStartDate());
        entity.setPublishDate(course.getPublishDate());
        entity.setImageUrl(course.getImageUrl());
        return entity;
    }
    
    private Course toDomain(CourseEntity entity) {
        Course course = new Course();
        course.setId(entity.getId());
        course.setTitle(entity.getTitle());
        course.setText(entity.getText());
        course.setPrice(entity.getPrice());
        course.setRate(entity.getRate());
        course.setStartDate(entity.getStartDate());
        course.setPublishDate(entity.getPublishDate());
        course.setImageUrl(entity.getImageUrl());
        return course;
    }
}