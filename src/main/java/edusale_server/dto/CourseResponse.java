package edusale_server.dto;

import edusale_server.model.Course;

import java.time.LocalDate;

public record CourseResponse(
    Long id,
    String title,
    String text,
    String price,
    String rate,
    LocalDate startDate,
    LocalDate publishDate,
    String imageUrl,
    boolean hasLike
) {

    public CourseResponse(Course course, boolean hasLike) {
        this(
            course.getId(),
            course.getTitle(),
            course.getText(),
            course.getPrice(),
            course.getRate(),
            course.getStartDate(),
            course.getPublishDate(),
            course.getImageUrl(),
            hasLike
        );
    }
}
