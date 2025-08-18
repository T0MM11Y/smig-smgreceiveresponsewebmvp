package com.smig.smg.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smig.smg.receiver.dto.request.CreateResponseRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the application
 * 
 * @author SMIG Development Team
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ReceiveResponseWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create and retrieve response message end-to-end")
    void shouldCreateAndRetrieveResponseMessageEndToEnd() throws Exception {
        // Arrange
        CreateResponseRequest request = CreateResponseRequest.builder()
                .content("Integration test response message")
                .build();

        // Act & Assert - Create response message
        String createResponse = mockMvc.perform(post("/api/v1/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.data.content").value("Integration test response message"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract ID from create response for retrieval test
        // This is a simplified approach - in real scenario you'd parse the JSON properly
        
        // Act & Assert - Retrieve all response messages
        mockMvc.perform(get("/api/v1/responses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray());

        // Act & Assert - Retrieve response message by ID
        mockMvc.perform(get("/api/v1/responses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content").value("Integration test response message"));
    }

    @Test
    @DisplayName("Should return validation error for invalid request")
    void shouldReturnValidationErrorForInvalidRequest() throws Exception {
        // Arrange
        CreateResponseRequest invalidRequest = CreateResponseRequest.builder()
                .content("") // Empty content
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/v1/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"));
    }

    @Test
    @DisplayName("Should return not found for non-existent response message")
    void shouldReturnNotFoundForNonExistentResponseMessage() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/responses/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"));
    }
}
