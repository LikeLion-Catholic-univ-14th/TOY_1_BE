package com.example.meetpick.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseDTO {

    private int totalResponses;

    private List<OptionStatDto> optionStats;

    private String recommendedTime;

    private String alternativeTime;

    private String reason;

    private String noticeText;
    @JsonProperty("isFallback")
    private boolean isFallback;
}