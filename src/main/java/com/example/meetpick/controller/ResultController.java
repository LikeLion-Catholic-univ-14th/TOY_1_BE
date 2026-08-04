package com.example.meetpick.controller;

import com.example.meetpick.dto.ResultResponseDTO;
import com.example.meetpick.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/{meetingId}/result")
    public ResultResponseDTO getResult(
            @PathVariable String meetingId
    ) {

        return resultService.getMeetingResult(meetingId);
    }
}