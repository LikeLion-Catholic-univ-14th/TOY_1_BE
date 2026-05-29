package com.example.meetpick.service;

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

        // DB 응답 가져오기
        List<Response> responses =
                responseRepository.findByMeeting_MeetingId(meetingId);

        List<String> responseTexts = responses.stream()
                .map(Response::getRawText)
                .toList();

        try {

            String aiResult = getRecommendation(responseTexts);

            String cleaned = aiResult
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            ObjectMapper objectMapper = new ObjectMapper();

            ResultResponseDTO aiDto =
                    objectMapper.readValue(cleaned, ResultResponseDTO.class);

            return aiDto;

        } catch (Exception e) {

            e.printStackTrace();

            return ResultResponseDTO.builder()
                    .totalResponses(responses.size())
                    .recommendedTime("분석 실패")
                    .alternativeTime("없음")
                    .reason("AI 응답 파싱 실패")
                    .noticeText("잠시 후 다시 시도해주세요.")
                    .isFallback(true)
                    .optionStats(List.of())
                    .build();
        }
    }

    public String getRecommendation(List<String> responsesFromDB) {

        if (responsesFromDB.isEmpty()) {

            return """
                    {
                      "totalResponses":0,
                      "recommendedTime":"없음",
                      "alternativeTime":"없음",
                      "reason":"응답이 없습니다.",
                      "noticeText":"아직 응답이 없어요!",
                      "isFallback":true,
                      "optionStats":[]
                    }
                    """;
        }

        String combinedResponses =
                String.join("\n", responsesFromDB);

        String prompt =
                """
                다음 모임 응답들을 분석해서
                가장 적절한 모임 시간을 추천해줘.

                응답 목록:
                """
                        + combinedResponses +
                        """
        
                        반드시 JSON만 반환해.
                        설명 절대 붙이지 마.
        
                        형식:
                        {
                          "totalResponses": 0,
                          "recommendedTime": "",
                          "alternativeTime": "",
                          "reason": "",
                          "noticeText": "",
                          "isFallback": false,
                          "optionStats": [
                            {
                              "option": "",
                              "availableCount": 0
                            }
                          ]
                        }
                        """;

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
}