package com.smig.smg.receiver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smig.smg.receiver.dto.request.CreateResponseRequest;
import com.smig.smg.receiver.dto.response.ResponseMessageDto;
import com.smig.smg.receiver.exception.SmgApiException;
import com.smig.smg.receiver.service.ResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for ResponseController
 * 
 * @author SMIG Development Team
 */
@WebMvcTest(ResponseController.class)
class ResponseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResponseService responseService;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseMessageDto sampleResponseDto;
    private CreateResponseRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleResponseDto = ResponseMessageDto.builder()
                .id(1L)
                .content("Sample response content")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        sampleRequest = CreateResponseRequest.builder()
                .content("Sample response content")
                .build();
    }

    @Test
    @DisplayName("Should create response message successfully")
    void shouldCreateResponseMessageSuccessfully() throws Exception {
        // Arrange
        when(responseService.createResponse(any(CreateResponseRequest.class)))
                .thenReturn(sampleResponseDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("Response created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.content").value("Sample response content"));
    }

    @Test
    @DisplayName("Should return bad request for invalid input")
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        // Arrange
        CreateResponseRequest invalidRequest = CreateResponseRequest.builder()
                .content("") // Empty content should fail validation
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/v1/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"));
    }

    @Test
    @DisplayName("Should get all response messages successfully")
    void shouldGetAllResponseMessagesSuccessfully() throws Exception {
        // Arrange
        List<ResponseMessageDto> responses = Arrays.asList(sampleResponseDto);
        when(responseService.getAllResponses()).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/v1/responses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Responses retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].content").value("Sample response content"));
    }

    @Test
    @DisplayName("Should get response message by ID successfully")
    void shouldGetResponseMessageByIdSuccessfully() throws Exception {
        // Arrange
        when(responseService.getResponseById(1L)).thenReturn(sampleResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/responses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Response retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.content").value("Sample response content"));
    }

    @Test
    @DisplayName("Should return not found when response message does not exist")
    void shouldReturnNotFoundWhenResponseMessageDoesNotExist() throws Exception {
        // Arrange
        when(responseService.getResponseById(anyLong()))
                .thenThrow(new SmgApiException("404", "Response message not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/responses/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("Response message not found"));
    }
}
