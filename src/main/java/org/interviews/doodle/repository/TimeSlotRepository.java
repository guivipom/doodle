package org.interviews.doodle.repository;

import org.interviews.doodle.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> {

    List<TimeSlot> findByUserId(long id);

}
