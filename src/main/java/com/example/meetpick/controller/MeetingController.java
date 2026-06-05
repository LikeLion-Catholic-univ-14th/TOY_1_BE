package com.example.meetpick.controller;

import com.example.meetpick.dto.MeetingDTO;
import com.example.meetpick.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public MeetingDTO createMeeting(@RequestBody MeetingDTO dto) {
        return meetingService.createMeeting(dto);
    }

    @GetMapping("/{meetingId}")
    public MeetingDTO getMeeting(@PathVariable String meetingId) {
        return meetingService.getMeeting(meetingId);
    }

    @GetMapping
    public List<MeetingDTO> getAllMeetings() {
        return meetingService.getAllMeetings();
    }
}
