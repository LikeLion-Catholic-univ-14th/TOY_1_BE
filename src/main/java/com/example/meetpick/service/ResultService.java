package com.example.meetpick.service;

import com.example.meetpick.dto.OptionStatDto;
import com.example.meetpick.dto.ResultResponseDTO;
import com.example.meetpick.entity.Response;
import com.example.meetpick.repository.ResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResultService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final WebClient.Builder webClientBuilder;
    private final ResponseRepository responseRepository;

    public ResultResponseDTO getMeetingResult(String meetingId) {

        // 1. DB에서 가져오기
        List<Response> responses =
                responseRepository.findByMeeting_MeetingId(meetingId);

        List<String> responseTexts = responses.stream()
                .map(Response::getRawText)
                .toList();

        List<OptionStatDto> optionStats = createDummyStats();

        try {

            String aiResult = getRecommendation(responseTexts);

            String cleaned = aiResult
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            ObjectMapper objectMapper = new ObjectMapper();

            ResultResponseDTO aiDto =
                    objectMapper.readValue(cleaned, ResultResponseDTO.class);

            return ResultResponseDTO.builder()
                    .totalResponses(responses.size())
                    .optionStats(optionStats)
                    .recommendedTime(aiDto.getRecommendedTime())
                    .alternativeTime(aiDto.getAlternativeTime())
                    .reason(aiDto.getReason())
                    .noticeText(aiDto.getNoticeText())
                    .isFallback(aiDto.isFallback())
                    .build();

        } catch (Exception e) {

            return fallbackResult(responses.size(), optionStats);
        }
    }

    public String getRecommendation(
            List<String> responsesFromDB
    ) {

        if (responsesFromDB.isEmpty()) {

            return """
                    {
                      "recommendedTime":"없음",
                      "alternativeTime":"없음",
                      "reason":"응답이 없습니다.",
                      "noticeText":"아직 응답이 없어요!",
                      "isFallback":true
                    }
                    """;
        }

        String combinedResponses =
                String.join("\n", responsesFromDB);

        String prompt =
                "다음 모임 응답들을 분석해서 가장 겹치는 시간을 기준으로 가장 적절한 모임 시간을 추천해줘.\n\n"
                        + combinedResponses
                        + "\n\n"
                        + "반드시 JSON만 반환해.\n"
                        + "설명 절대 붙이지 마.\n\n"
                        + "{\n"
                        + "\"recommendedTime\":\"\",\n"
                        + "\"alternativeTime\":\"\",\n"
                        + "\"reason\":\"\",\n"
                        + "\"noticeText\":\"\",\n"
                        + "\"isFallback\":false\n"
                        + "}";

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                        + apiKey;

        Map<String, Object> requestBody = Map.of(
                "contents",
                List.of(
                        Map.of(
                                "parts",
                                List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        Map responseMap =
                webClientBuilder.build()
                        .post()
                        .uri(url)
                        .header("Content-Type", "application/json")
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        List candidates =
                (List) responseMap.get("candidates");

        Map candidate =
                (Map) candidates.get(0);

        Map content =
                (Map) candidate.get("content");

        List parts =
                (List) content.get("parts");

        Map part =
                (Map) parts.get(0);

        return (String) part.get("text");
    }

    private ResultResponseDTO fallbackResult(
            int totalResponses,
            List<OptionStatDto> optionStats
    ) {

        return ResultResponseDTO.builder()
                .totalResponses(totalResponses)
                .optionStats(optionStats)
                .recommendedTime("수요일 17시")
                .alternativeTime("목요일 18시")
                .reason("AI 서버 지연으로 기본 추천 결과를 표시합니다.")
                .noticeText("수요일 17시에 모임을 진행해요!")
                .isFallback(true)
                .build();
    }

    private List<OptionStatDto> createDummyStats() {

        return List.of(
                OptionStatDto.builder()
                        .option("수요일 17시")
                        .availableCount(4)
                        .build(),

                OptionStatDto.builder()
                        .option("목요일 18시")
                        .availableCount(3)
                        .build()
        );
    }
}