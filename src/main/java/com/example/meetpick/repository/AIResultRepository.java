package com.example.meetpick.repository;

import com.example.meetpick.entity.AIResult;
import com.example.meetpick.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIResultRepository extends JpaRepository<AIResult, Long> {
    List<AIResult> findByMeeting(Meeting meeting);
}
