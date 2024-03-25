package org.subhankar.journalservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.subhankar.journalservice.model.DO.JournalDO;

import java.time.LocalDateTime;
import java.util.Date;

public interface JournalRepository extends JpaRepository<JournalDO,String> {
    @Query("SELECT j FROM JournalDO j " +
            "WHERE (:userId IS NULL OR j.createdBy = :userId) " +
            "AND (:message IS NULL OR j.message LIKE %:message%) " +
            "AND (:createdAt IS NULL OR DATE_FORMAT(j.createdAt, '%Y-%m-%d %H:%i:%s') = DATE_FORMAT(:createdAt, '%Y-%m-%d %H:%i:%s')) " +
            "AND ((:startDate IS NULL AND :endDate IS NULL) OR " +
            "(:startDate IS NOT NULL AND :endDate IS NOT NULL AND DATE_FORMAT(j.createdAt, '%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT(:startDate, '%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(:endDate, '%Y-%m-%d %H:%i:%s'))) " +
            "AND (:role IS NULL OR j.role = :role)")
    Page<JournalDO> filter(PageRequest pageRequest, String userId, String message, LocalDateTime createdAt, LocalDateTime startDate, LocalDateTime endDate, String role);
}
