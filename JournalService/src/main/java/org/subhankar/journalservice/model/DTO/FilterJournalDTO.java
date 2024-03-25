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
    private String role;
    private int pageNumber=0;
    private int pageSize=10;
    private String sortBy="createdAt";
    private String sortOrder="DESC";

}
