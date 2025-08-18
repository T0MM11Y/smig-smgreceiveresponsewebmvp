package com.smig.smg.receiver.repository;

import com.smig.smg.receiver.entity.ResponseMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for ResponseMessage entity
 * 
 * @author SMIG Development Team
 */
@Repository
public interface ResponseMessageRepository extends JpaRepository<ResponseMessage, Long> {

    @Query("SELECT r FROM ResponseMessage r WHERE r.createdAt >= :startDate ORDER BY r.createdAt DESC")
    List<ResponseMessage> findRecentResponses(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(r) FROM ResponseMessage r WHERE r.createdAt BETWEEN :start AND :end")
    long countResponsesInPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
