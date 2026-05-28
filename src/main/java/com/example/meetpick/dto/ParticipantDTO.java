package com.example.meetpick.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ParticipantDTO {
    private Long participantId;
    private String meetingId;
    private String nickname;
    private LocalDateTime createdAt;
}
