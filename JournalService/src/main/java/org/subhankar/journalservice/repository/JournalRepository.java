package org.subhankar.journalservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.subhankar.journalservice.model.DO.JournalDO;

import java.util.Date;

public interface JournalRepository extends JpaRepository<JournalDO,String> {
    @Query("SELECT j FROM JournalDO j " +
            "WHERE (:userId IS NULL OR j.createdBy = :userId) " +
            "AND (:message IS NULL OR j.message LIKE %:message%) " +
            "AND (:createdAt IS NULL OR j.createdAt = :createdAt) " +
            "AND ((:startDate IS NULL AND :endDate IS NULL) OR " +
            "(:startDate IS NOT NULL AND :endDate IS NOT NULL AND j.createdAt BETWEEN :startDate AND :endDate)) " +
            "AND (:role IS NULL OR j.role = :role)")
    Page<JournalDO> filter(PageRequest pageRequest, String userId, String message, Date createdAt, Date startDate, Date endDate, String role);
}
