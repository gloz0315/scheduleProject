package simpleprojects.scheduleproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simpleprojects.scheduleproject.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByCreatedAtDesc();         // 작성일을 기준으로 내림차순 -> 최신 내용이 위에 있음
}
