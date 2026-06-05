package com.example.meetpick;

import com.example.meetpick.dto.MeetingDTO;
import com.example.meetpick.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.test.context.ActiveProfiles("test")
public class ApiFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void fullFlowTest() throws Exception {
        // 1. Create a Meeting
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setTitle("Test Meeting");
        meetingDTO.setMaxParticipants(5);

        MvcResult meetingResult = mockMvc.perform(post("/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meetingId").exists())
                .andReturn();

        String meetingResponseJson = meetingResult.getResponse().getContentAsString();
        MeetingDTO createdMeeting = objectMapper.readValue(meetingResponseJson, MeetingDTO.class);
        String meetingId = createdMeeting.getMeetingId();

        // 2. Submit a Response (Join via Link flow)
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMeetingId(meetingId);
        responseDTO.setNickname("Tester");
        responseDTO.setRawText("Available: Mon 10am-12pm");

        mockMvc.perform(post("/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseId").exists())
                .andExpect(jsonPath("$.nickname").value("Tester"))
                .andExpect(jsonPath("$.meetingId").value(meetingId));
    }
}
