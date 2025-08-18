package com.smig.smg.receiver.service;

import com.smig.smg.receiver.dto.request.CreateResponseRequest;
import com.smig.smg.receiver.dto.response.ResponseMessageDto;

import java.util.List;

/**
 * Service interface for response message operations
 * 
 * @author SMIG Development Team
 */
public interface ResponseService {

    /**
     * Create a new response message
     * 
     * @param request the create request
     * @return the created response message
     */
    ResponseMessageDto createResponse(CreateResponseRequest request);

    /**
     * Get all response messages
     * 
     * @return list of all response messages
     */
    List<ResponseMessageDto> getAllResponses();

    /**
     * Get response message by ID
     * 
     * @param id the response message ID
     * @return the response message
     */
    ResponseMessageDto getResponseById(Long id);
}
