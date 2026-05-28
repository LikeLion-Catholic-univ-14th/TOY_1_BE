package com.example.meetpick.repository;

import com.example.meetpick.entity.Meeting;
import com.example.meetpick.entity.Participant;
import com.example.meetpick.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByMeeting(Meeting meeting);
    List<Response> findByParticipant(Participant participant);
    List<Response> findByMeeting_MeetingId(String meetingId);
}
