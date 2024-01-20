package simpleprojects.scheduleproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simpleprojects.scheduleproject.dto.RequestDto;
import simpleprojects.scheduleproject.dto.ResponseDto;
import simpleprojects.scheduleproject.entity.Schedule;
import simpleprojects.scheduleproject.repository.ScheduleRepository;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().
                stream().map(ResponseDto::new).toList();
    }

    public ResponseDto registerSchedule(RequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ResponseDto(savedSchedule);
    }

    public ResponseDto selectSchedule(Long id, String password) {
        Schedule findSchedule = findById(id); // 해당 데이터베이스에 id가 있는지 찾아주고 반환

        passwordCheck(findSchedule,password);  // 비밀번호 유무를 통해 비밀번호가 있으면 체크 (비밀번호가 맞지않으면 예외)

        return new ResponseDto(findSchedule);
    }

    public String deleteSchedule(Long id, String password) {
        Schedule findSchedule = findById(id);

        passwordCheck(findSchedule,password);

        scheduleRepository.delete(findSchedule);

        return "일정 관리가 삭제되었습니다.";
    }

    @Transactional
    public ResponseDto updateSchedule(Long id, RequestDto requestDto) {
        Schedule findSchedule = findById(id);
        passwordCheck(findSchedule,requestDto.getPassword());

        findSchedule.updateSchedule(requestDto);

        return new ResponseDto(findSchedule);
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
