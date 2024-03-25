package org.subhankar.journalservice.model.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private String message;
    private String status;
    private Object data;
}
