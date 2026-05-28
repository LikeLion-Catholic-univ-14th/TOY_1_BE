package com.example.meetpick.repository;

import com.example.meetpick.entity.Meeting;
import com.example.meetpick.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByMeeting(Meeting meeting);
}
