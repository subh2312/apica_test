package org.subhankar.authenticationservice.model.DO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDO {
    private String id;
    private String code;
    private String name;
    private String description;
}

