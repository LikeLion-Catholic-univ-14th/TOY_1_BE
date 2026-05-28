package com.example.meetpick.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIResultDTO {
    private Long resultId;
    private String meetingId;
    private int priority;
    private String recommendedDate;
    private int availableCount;
    private String aiReason;
}
