package simpleprojects.scheduleproject.dto;

import lombok.Getter;
import simpleprojects.scheduleproject.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class ResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String author;
    private LocalDateTime createTime;

    public ResponseDto(Schedule schedule) {
        id = schedule.getId();
        title = schedule.getTitle();
        contents = schedule.getContents();
        author = schedule.getAuthor();
        createTime = schedule.getCreatedAt();
    }
}
