package org.subhankar.userservice.model.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String id;
    private String email;
    private String fullName;
    private String token;
}
