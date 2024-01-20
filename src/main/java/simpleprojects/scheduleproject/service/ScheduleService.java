package simpleprojects.scheduleproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simpleprojects.scheduleproject.dto.RequestDto;
import simpleprojects.scheduleproject.dto.ResponseDto;
import simpleprojects.scheduleproject.dto.ResponseListMessageDto;
import simpleprojects.scheduleproject.dto.ResponseMessageDto;
import simpleprojects.scheduleproject.entity.Schedule;
import simpleprojects.scheduleproject.repository.ScheduleRepository;
import simpleprojects.scheduleproject.status.HttpMessage;
import simpleprojects.scheduleproject.status.HttpStatus;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseListMessageDto getSchedules() {
        return new ResponseListMessageDto(HttpStatus.HTTP_STATUS_OK, HttpMessage.CHECK_SUCCESS,
                scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ResponseDto::new)
                        .toList());
    }

    public ResponseMessageDto registerSchedule(RequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ResponseMessageDto(HttpStatus.HTTP_STATUS_CREATE,HttpMessage.CREATE_SUCCESS,
                new ResponseDto(savedSchedule));
    }

    public ResponseMessageDto selectSchedule(Long id, String password) {
        Schedule findSchedule = null;

        try {
            findSchedule = findById(id);
            passwordCheck(findSchedule,password);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            if(e.getMessage().equals("해당 데이터가 존재하지 않습니다."))
                return new ResponseMessageDto(HttpStatus.HTTP_STATUS_NOT_FOUND, HttpMessage.NOT_FOUND,
                        null);
            else if(e.getMessage().equals("비밀번호가 일치하지 않습니다."))
                return new ResponseMessageDto(HttpStatus.HTTP_STATUS_BAD_REQUEST, HttpMessage.PASSWORD_ERROR,
                        null);
        }

        return new ResponseMessageDto(HttpStatus.HTTP_STATUS_OK,HttpMessage.CHECK_SUCCESS,
                new ResponseDto(findSchedule));
    }

    public ResponseMessageDto deleteSchedule(Long id, String password) {
        Schedule findSchedule = null;

        try {
            findSchedule = findById(id);
            passwordCheck(findSchedule,password);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            if(e.getMessage().equals("해당 데이터가 존재하지 않습니다."))
                return new ResponseMessageDto(HttpStatus.HTTP_STATUS_NOT_FOUND, HttpMessage.NOT_FOUND,
                        null);
            else if(e.getMessage().equals("비밀번호가 일치하지 않습니다."))
                return new ResponseMessageDto(HttpStatus.HTTP_STATUS_BAD_REQUEST, HttpMessage.PASSWORD_ERROR,
                        null);
        }

        scheduleRepository.delete(findSchedule);

        return new ResponseMessageDto(HttpStatus.HTTP_STATUS_OK,HttpMessage.DELETE,
                new ResponseDto(findSchedule));
    }

    @Transactional
    public ResponseMessageDto updateSchedule(Long id, RequestDto requestDto) {
        Schedule findSchedule = null;

        try {
            findSchedule = findById(id);
            passwordCheck(findSchedule,requestDto.getPassword());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            if(e.getMessage().equals("해당 데이터가 존재하지 않습니다."))
                return new ResponseMessageDto(HttpStatus.HTTP_STATUS_NOT_FOUND, HttpMessage.NOT_FOUND,
                    null);
            else if(e.getMessage().equals("비밀번호가 일치하지 않습니다."))
                return new ResponseMessageDto(HttpStatus.HTTP_STATUS_BAD_REQUEST, HttpMessage.PASSWORD_ERROR,
                        null);
        }

        findSchedule.updateSchedule(requestDto);

        return new ResponseMessageDto(HttpStatus.HTTP_STATUS_OK, HttpMessage.UPDATE_SCHEDULE,
                new ResponseDto(findSchedule));
    }


    private Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 데이터가 존재하지 않습니다.")
        );
    }

    private void passwordCheck(Schedule schedule,String password) {
        if(schedule.getPassword() == null)      // 만약 비밀번호를 처음 세팅할때 없었다면 , 수정을 해도 비밀번호는 영원히 없는거로..
            return;

        if(schedule.getPassword().equals(password))
            return;

        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

}
