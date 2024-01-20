package simpleprojects.scheduleproject.dto;

import lombok.Getter;

@Getter
public class ResponseMessageDto {

    private int status;
    private String message;
    private ResponseDto data;

    public ResponseMessageDto(int status, String message, ResponseDto data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
