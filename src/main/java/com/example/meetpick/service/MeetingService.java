package com.example.meetpick.service;

import com.example.meetpick.dto.MeetingDTO;
import com.example.meetpick.entity.Meeting;
import com.example.meetpick.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingService {

    private final MeetingRepository meetingRepository;

    @Transactional
    public MeetingDTO createMeeting(MeetingDTO dto) {
        Meeting meeting = new Meeting();
        meeting.setMeetingId(UUID.randomUUID().toString());
        meeting.setTitle(dto.getTitle());
        meeting.setMaxParticipants(dto.getMaxParticipants());
        
        Meeting saved = meetingRepository.save(meeting);
        return convertToDTO(saved);
    }

    public MeetingDTO getMeeting(String meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found"));
        return convertToDTO(meeting);
    }

    public List<MeetingDTO> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MeetingDTO convertToDTO(Meeting entity) {
        MeetingDTO dto = new MeetingDTO();
        dto.setMeetingId(entity.getMeetingId());
        dto.setTitle(entity.getTitle());
        dto.setMaxParticipants(entity.getMaxParticipants());
        dto.setCreatedAT(entity.getCreatedAT());
        return dto;
    }
}
