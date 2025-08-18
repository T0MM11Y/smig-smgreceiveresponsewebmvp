package com.smig.smg.receiver.service;

import com.smig.smg.receiver.dto.request.CreateResponseRequest;
import com.smig.smg.receiver.dto.response.ResponseMessageDto;
import com.smig.smg.receiver.entity.ResponseMessage;
import com.smig.smg.receiver.exception.SmgApiException;
import com.smig.smg.receiver.repository.ResponseMessageRepository;
import com.smig.smg.receiver.service.impl.ResponseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ResponseService
 * 
 * @author SMIG Development Team
 */
@ExtendWith(MockitoExtension.class)
class ResponseServiceTest {

    @Mock
    private ResponseMessageRepository repository;

    @InjectMocks
    private ResponseServiceImpl responseService;

    private ResponseMessage sampleEntity;
    private CreateResponseRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleEntity = ResponseMessage.builder()
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
    void shouldCreateResponseMessageSuccessfully() {
        // Arrange
        when(repository.save(any(ResponseMessage.class))).thenReturn(sampleEntity);

        // Act
        ResponseMessageDto result = responseService.createResponse(sampleRequest);

        // Assert
        assertNotNull(result);
        assertEquals(sampleEntity.getId(), result.getId());
        assertEquals(sampleEntity.getContent(), result.getContent());
        assertEquals(sampleEntity.getCreatedAt(), result.getCreatedAt());
        assertEquals(sampleEntity.getUpdatedAt(), result.getUpdatedAt());
        
        verify(repository).save(any(ResponseMessage.class));
    }

    @Test
    @DisplayName("Should throw exception when repository save fails")
    void shouldThrowExceptionWhenRepositorySaveFails() {
        // Arrange
        when(repository.save(any(ResponseMessage.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        SmgApiException exception = assertThrows(SmgApiException.class, 
                () -> responseService.createResponse(sampleRequest));
        
        assertEquals("500", exception.getCode());
        assertEquals("Failed to create response message", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve all response messages successfully")
    void shouldRetrieveAllResponseMessagesSuccessfully() {
        // Arrange
        List<ResponseMessage> entities = Arrays.asList(sampleEntity);
        when(repository.findAll()).thenReturn(entities);

        // Act
        List<ResponseMessageDto> result = responseService.getAllResponses();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleEntity.getId(), result.get(0).getId());
        assertEquals(sampleEntity.getContent(), result.get(0).getContent());
        
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should retrieve response message by ID successfully")
    void shouldRetrieveResponseMessageByIdSuccessfully() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(sampleEntity));

        // Act
        ResponseMessageDto result = responseService.getResponseById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(sampleEntity.getId(), result.getId());
        assertEquals(sampleEntity.getContent(), result.getContent());
        assertEquals(sampleEntity.getCreatedAt(), result.getCreatedAt());
        assertEquals(sampleEntity.getUpdatedAt(), result.getUpdatedAt());
        
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when response message not found")
    void shouldThrowExceptionWhenResponseMessageNotFound() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SmgApiException exception = assertThrows(SmgApiException.class, 
                () -> responseService.getResponseById(1L));
        
        assertEquals("404", exception.getCode());
        assertEquals("Response message not found", exception.getMessage());
        
        verify(repository).findById(1L);
    }
}
