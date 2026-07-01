package org.interviews.doodle.repository;

import org.interviews.doodle.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting,Long> {
    List<Meeting> findByOrganizerId(Long organizerId);
    boolean existsBySlotId(Long slotId);
}
