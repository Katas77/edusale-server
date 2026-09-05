package edusale_server.controller;

import edusale_server.dto.CourseResponse;
import edusale_server.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(Authentication authentication) {
        return ResponseEntity.ok(courseService.getAllCourses(new HashSet<>()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(courseService.getCourseById(id, new HashSet<>()));
    }
}