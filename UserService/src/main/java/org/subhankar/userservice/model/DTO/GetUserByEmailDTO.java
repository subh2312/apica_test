package org.subhankar.userservice.model.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserByEmailDTO {
    private String email;
}
