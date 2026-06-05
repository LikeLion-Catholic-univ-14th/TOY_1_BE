package com.example.meetpick.controller;

import com.example.meetpick.dto.ResponseDTO;
import com.example.meetpick.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/responses")
public class ResponseController {

    private final ResponseService responseService;

    @PostMapping
    public ResponseDTO submitResponse(@RequestBody ResponseDTO dto) {
        return responseService.submitResponse(dto);
    }
    @GetMapping("/{meetingId}")
    public List<ResponseDTO> getResponses(@PathVariable String meetingId) {
        return responseService.getResponsesByMeeting(meetingId);
    }
}
