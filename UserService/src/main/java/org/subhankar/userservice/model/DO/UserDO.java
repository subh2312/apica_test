package org.subhankar.userservice.model.DO;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserDO {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    private String email;
    private String password;
    private String fullName;
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleDO> role=new HashSet<>();
}
