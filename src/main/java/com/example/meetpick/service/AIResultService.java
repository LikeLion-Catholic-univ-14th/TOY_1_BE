package com.example.meetpick.service;

import com.example.meetpick.dto.AIResultDTO;
import com.example.meetpick.entity.AIResult;
import com.example.meetpick.entity.Meeting;
import com.example.meetpick.repository.AIResultRepository;
import com.example.meetpick.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AIResultService {

    private final AIResultRepository aiResultRepository;
    private final MeetingRepository meetingRepository;

    public List<AIResultDTO> getAIResultsByMeetingId(String meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid meeting ID"));
        
        return aiResultRepository.findByMeeting(meeting).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AIResultDTO convertToDTO(AIResult entity) {
        AIResultDTO dto = new AIResultDTO();
        dto.setResultId(entity.getResultId());
        dto.setMeetingId(entity.getMeeting().getMeetingId());
        dto.setPriority(entity.getPriority());
        dto.setRecommendedDate(entity.getRecommendedDate());
        dto.setAvailableCount(entity.getAvailableCount());
        dto.setAiReason(entity.getAiReason());
        return dto;
    }
}
