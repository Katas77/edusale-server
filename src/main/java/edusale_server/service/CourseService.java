package edusale_server.service;


import edusale_server.dto.CourseResponse;
import edusale_server.model.Course;
import edusale_server.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {
    
    private final CourseRepository courseRepository;
    
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public List<CourseResponse> getAllCourses(Set<Long> favoriteIds) {
        List<Course> courses = courseRepository.findAll();
        
        return courses.stream()
                .map(course -> new CourseResponse(course, favoriteIds.contains(course.getId())))
                .collect(Collectors.toList());
    }
    
    public CourseResponse getCourseById(Long id, Set<Long> favoriteIds) {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        return new CourseResponse(course, favoriteIds.contains(course.getId()));
    }
}