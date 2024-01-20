package simpleprojects.scheduleproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simpleprojects.scheduleproject.dto.RequestDto;
import simpleprojects.scheduleproject.dto.ResponseListMessageDto;
import simpleprojects.scheduleproject.dto.ResponseMessageDto;
import simpleprojects.scheduleproject.service.ScheduleService;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 전체 목록을 조회하는데 (작성일 기준으로 내림차순 정렬)
    @GetMapping("/schedules")
    public ResponseListMessageDto getSchedules() {
        return scheduleService.getSchedules();
    }

    // id값과 비밀번호를 통해서 선택할 일정 조회가 가능하다.
    @GetMapping("/schedules/{id}")
    public ResponseMessageDto selectSchedule(@PathVariable("id")Long id, @RequestParam("password") String password) {
        return scheduleService.selectSchedule(id,password);
    }

    @PostMapping("/schedules")
    public ResponseMessageDto registerSchedule(@RequestBody RequestDto requestDto) {
        return scheduleService.registerSchedule(requestDto);
    }

    @PutMapping("/schedules/{id}")
    public ResponseMessageDto updateSchedule(@PathVariable("id") Long id, @RequestBody RequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    // id값과 비밀번호를 통해서 선택한 일정을 삭제한다.
    @DeleteMapping("/schedules")
    public ResponseMessageDto deleteSchedule(@RequestParam("id") Long id, @RequestParam("password") String password) {
        return scheduleService.deleteSchedule(id,password);
    }
}
