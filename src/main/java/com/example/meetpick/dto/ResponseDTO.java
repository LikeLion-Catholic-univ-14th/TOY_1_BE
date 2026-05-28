package com.example.meetpick.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseDTO {
    private Long responseId;
    private Long participantId;
    private String meetingId;
    private String rawText;
    private LocalDateTime submittedAt;
}
