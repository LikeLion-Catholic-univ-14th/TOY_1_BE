package com.example.meetpick.service;

import com.example.meetpick.dto.ParticipantDTO;
import com.example.meetpick.entity.Meeting;
import com.example.meetpick.entity.Participant;
import com.example.meetpick.repository.MeetingRepository;
import com.example.meetpick.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final MeetingRepository meetingRepository;

    @Transactional
    public ParticipantDTO addParticipant(ParticipantDTO dto) {
        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found"));

        Participant participant = new Participant();
        participant.setMeeting(meeting);
        participant.setNickname(dto.getNickname());

        Participant saved = participantRepository.save(participant);
        return convertToDTO(saved);
    }

    public List<ParticipantDTO> getParticipantsByMeeting(String meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found"));
        
        return participantRepository.findByMeeting(meeting).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ParticipantDTO convertToDTO(Participant entity) {
        ParticipantDTO dto = new ParticipantDTO();
        dto.setParticipantId(entity.getParticipantId());
        dto.setMeetingId(entity.getMeeting().getMeetingId());
        dto.setNickname(entity.getNickname());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
