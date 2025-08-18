package com.smig.smg.receiver.service.impl;

import com.smig.smg.receiver.dto.request.CreateResponseRequest;
import com.smig.smg.receiver.dto.response.ResponseMessageDto;
import com.smig.smg.receiver.entity.ResponseMessage;
import com.smig.smg.receiver.exception.SmgApiException;
import com.smig.smg.receiver.repository.ResponseMessageRepository;
import com.smig.smg.receiver.service.ResponseService;
import com.smig.smg.receiver.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ResponseService
 * 
 * @author SMIG Development Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ResponseServiceImpl implements ResponseService {

    private final ResponseMessageRepository responseMessageRepository;

    @Override
    public ResponseMessageDto createResponse(CreateResponseRequest request) {
        log.info("Creating new response message with content length: {}", request.getContent().length());
        
        try {
            ResponseMessage entity = ResponseMessage.builder()
                    .content(request.getContent())
                    .build();
            
            ResponseMessage savedEntity = responseMessageRepository.save(entity);
            log.info("Response message created successfully with ID: {}", savedEntity.getId());
            
            return mapToDto(savedEntity);
        } catch (Exception e) {
            log.error("Error creating response message: {}", e.getMessage(), e);
            throw new SmgApiException(Constants.ErrorCodes.INTERNAL_ERROR, "Failed to create response message");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseMessageDto> getAllResponses() {
        log.info("Retrieving all response messages");
        
        try {
            List<ResponseMessage> entities = responseMessageRepository.findAll();
            log.info("Found {} response messages", entities.size());
            
            return entities.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving response messages: {}", e.getMessage(), e);
            throw new SmgApiException(Constants.ErrorCodes.INTERNAL_ERROR, "Failed to retrieve response messages");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseMessageDto getResponseById(Long id) {
        log.info("Retrieving response message with ID: {}", id);
        
        ResponseMessage entity = responseMessageRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Response message not found with ID: {}", id);
                    return new SmgApiException(Constants.ErrorCodes.NOT_FOUND, Constants.Messages.RESPONSE_NOT_FOUND);
                });
        
        log.info("Response message found with ID: {}", id);
        return mapToDto(entity);
    }

    private ResponseMessageDto mapToDto(ResponseMessage entity) {
        return ResponseMessageDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
