package org.subhankar.journalservice.model.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterJournalDTO {
    private String userId;
    private String message;
    private String createdDate;
    private String startDate;
    private String endDate;
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private String sortOrder;
    private String role;
}
