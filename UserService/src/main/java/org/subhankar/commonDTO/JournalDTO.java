package org.subhankar.commonDTO;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JournalDTO implements Serializable {
    private String message;
    private String createdBy;
    private String role;
    private Date createdAt;
}
