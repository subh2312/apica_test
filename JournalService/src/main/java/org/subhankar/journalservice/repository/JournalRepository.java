package org.subhankar.journalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.subhankar.journalservice.model.DO.JournalDO;

public interface JournalRepository extends JpaRepository<JournalDO,String> {
}
