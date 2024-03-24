package org.subhankar.authenticationservice.model.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserByEmailDTO {
    private String email;
}
