package com.smig.smg.receiver.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Response DTO for response message
 * 
 * @author SMIG Development Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessageDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
