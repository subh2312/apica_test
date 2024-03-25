package org.subhankar.journalservice.model.DTO;

import lombok.*;
import org.subhankar.journalservice.model.DO.JournalDO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterJournalResponseDTO {
    private List<JournalDO> journalDOList;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;

}
