package com.example.meetpick.service;

import com.example.meetpick.dto.ResponseDTO;
import com.example.meetpick.entity.Meeting;
import com.example.meetpick.entity.Participant;
import com.example.meetpick.entity.Response;
import com.example.meetpick.repository.MeetingRepository;
import com.example.meetpick.repository.ParticipantRepository;
import com.example.meetpick.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    public ResponseDTO submitResponse(ResponseDTO dto) {
        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found"));
        Participant participant = participantRepository.findById(dto.getParticipantId())
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));

        Response response = new Response();
        response.setMeeting(meeting);
        response.setParticipant(participant);
        response.setRawText(dto.getRawText());

        Response saved = responseRepository.save(response);
        return convertToDTO(saved);
    }

    public List<ResponseDTO> getResponsesByMeeting(String meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found: " + meetingId));
        
        return responseRepository.findByMeeting(meeting).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ResponseDTO convertToDTO(Response entity) {
        ResponseDTO dto = new ResponseDTO();
        dto.setResponseId(entity.getResponseId());
        dto.setMeetingId(entity.getMeeting().getMeetingId());
        dto.setParticipantId(entity.getParticipant().getParticipantId());
        dto.setRawText(entity.getRawText());
        dto.setSubmittedAt(entity.getSubmittedAt());
        return dto;
    }
}
