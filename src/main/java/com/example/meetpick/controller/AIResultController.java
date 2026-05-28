package com.example.meetpick.controller;

import com.example.meetpick.dto.AIResultDTO;
import com.example.meetpick.service.AIResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai-results")
public class AIResultController {

    private final AIResultService aiResultService;

    @GetMapping("/meeting/{meetingId}")
    public List<AIResultDTO> getAIResults(@PathVariable String meetingId) {
        return aiResultService.getAIResultsByMeetingId(meetingId);
    }
}
