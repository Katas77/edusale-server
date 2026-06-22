package edusale_server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    private Long id;
    private String title;
    private String text;
    private String price;
    private String rate;
    private LocalDate startDate;
    private LocalDate publishDate;
    private String imageUrl;

}