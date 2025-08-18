package com.smig.smg.receiver.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Request DTO for creating response message
 * 
 * @author SMIG Development Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResponseRequest {

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 4000, message = "Content cannot exceed 4000 characters")
    private String content;
}
