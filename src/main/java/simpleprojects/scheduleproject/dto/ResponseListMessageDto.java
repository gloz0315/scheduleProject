package simpleprojects.scheduleproject.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseListMessageDto {
    private int status;
    private String message;
    private List<ResponseDto> data;

    public ResponseListMessageDto(int status, String message, List<ResponseDto> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
