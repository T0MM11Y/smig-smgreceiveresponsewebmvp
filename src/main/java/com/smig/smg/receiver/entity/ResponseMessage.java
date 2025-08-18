package com.smig.smg.receiver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Response Message entity
 * 
 * @author SMIG Development Team
 */
@Entity
@Table(name = "RESPONSE_MESSAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resp_seq_generator")
    @SequenceGenerator(name = "resp_seq_generator", sequenceName = "RESP_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENT", nullable = false, length = 4000)
    private String content;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
