package com.example.meetpick.controller;

import com.example.meetpick.dto.ParticipantDTO;
import com.example.meetpick.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    public ParticipantDTO addParticipant(@RequestBody ParticipantDTO dto) {
        return participantService.addParticipant(dto);
    }

    @GetMapping("/meeting/{meetingId}")
    public List<ParticipantDTO> getParticipants(@PathVariable String meetingId) {
        return participantService.getParticipantsByMeeting(meetingId);
    }
}
