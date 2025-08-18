package com.smig.smg.receiver.repository;

import com.smig.smg.receiver.entity.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ResponseMessageRepository
 * 
 * @author SMIG Development Team
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ResponseMessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResponseMessageRepository repository;

    private ResponseMessage sampleEntity;

    @BeforeEach
    void setUp() {
        sampleEntity = ResponseMessage.builder()
                .content("Sample response content")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should save and find response message")
    void shouldSaveAndFindResponseMessage() {
        // Act
        ResponseMessage saved = repository.save(sampleEntity);
        Optional<ResponseMessage> found = repository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(sampleEntity.getContent(), found.get().getContent());
        assertNotNull(found.get().getId());
        assertNotNull(found.get().getCreatedAt());
        assertNotNull(found.get().getUpdatedAt());
    }

    @Test
    @DisplayName("Should find all response messages")
    void shouldFindAllResponseMessages() {
        // Arrange
        repository.save(sampleEntity);
        
        ResponseMessage anotherEntity = ResponseMessage.builder()
                .content("Another response content")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        repository.save(anotherEntity);

        // Act
        List<ResponseMessage> all = repository.findAll();

        // Assert
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("Should find recent responses")
    void shouldFindRecentResponses() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        
        sampleEntity.setCreatedAt(now);
        repository.save(sampleEntity);

        // Act
        List<ResponseMessage> recent = repository.findRecentResponses(yesterday);

        // Assert
        assertEquals(1, recent.size());
        assertEquals(sampleEntity.getContent(), recent.get(0).getContent());
    }

    @Test
    @DisplayName("Should count responses in period")
    void shouldCountResponsesInPeriod() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        sampleEntity.setCreatedAt(now);
        repository.save(sampleEntity);

        LocalDateTime start = now.minusHours(1);
        LocalDateTime end = now.plusHours(1);

        // Act
        long count = repository.countResponsesInPeriod(start, end);

        // Assert
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Should delete response message")
    void shouldDeleteResponseMessage() {
        // Arrange
        ResponseMessage saved = repository.save(sampleEntity);

        // Act
        repository.delete(saved);
        Optional<ResponseMessage> found = repository.findById(saved.getId());

        // Assert
        assertFalse(found.isPresent());
    }
}
