package com.smig.smg.receiver.controller;

import com.smig.smg.receiver.dto.common.ApiResponse;
import com.smig.smg.receiver.dto.request.CreateResponseRequest;
import com.smig.smg.receiver.dto.response.ResponseMessageDto;
import com.smig.smg.receiver.service.ResponseService;
import com.smig.smg.receiver.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST Controller for response message operations
 * 
 * @author SMIG Development Team
 */
@RestController
@RequestMapping(Constants.Api.VERSION_V1 + Constants.Api.RESPONSES)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Response Messages", description = "API for managing response messages")
public class ResponseController {

    private final ResponseService responseService;

    @Operation(summary = "Create new response message", 
               description = "Creates a new response message and stores it in the database")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", 
            description = "Response message created successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid request data",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ResponseMessageDto>> createResponse(
            @Valid @RequestBody CreateResponseRequest request) {
        
        log.info("Received request to create response message");
        
        ResponseMessageDto createdResponse = responseService.createResponse(request);
        ApiResponse<ResponseMessageDto> response = ApiResponse.created(
            Constants.Messages.RESPONSE_CREATED, 
            createdResponse
        );
        
        URI location = URI.create(Constants.Api.VERSION_V1 + Constants.Api.RESPONSES + "/" + createdResponse.getId());
        
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Get all response messages", 
               description = "Retrieves all response messages from the database")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Response messages retrieved successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseMessageDto>>> getAllResponses() {
        
        log.info("Received request to get all response messages");
        
        List<ResponseMessageDto> responses = responseService.getAllResponses();
        ApiResponse<List<ResponseMessageDto>> response = ApiResponse.success(
            Constants.Messages.RESPONSES_RETRIEVED, 
            responses
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get response message by ID", 
               description = "Retrieves a specific response message by its ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Response message retrieved successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Response message not found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseMessageDto>> getResponseById(
            @Parameter(description = "Response message ID", required = true)
            @PathVariable Long id) {
        
        log.info("Received request to get response message with ID: {}", id);
        
        ResponseMessageDto responseDto = responseService.getResponseById(id);
        ApiResponse<ResponseMessageDto> response = ApiResponse.success(
            Constants.Messages.RESPONSE_RETRIEVED, 
            responseDto
        );
        
        return ResponseEntity.ok(response);
    }
}
