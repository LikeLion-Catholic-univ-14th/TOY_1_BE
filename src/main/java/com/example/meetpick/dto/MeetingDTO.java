package com.example.meetpick.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class MeetingDTO {
    private String meetingId;
    private String title;
    private int maxParticipants;
    private LocalDateTime createdAT;
}
