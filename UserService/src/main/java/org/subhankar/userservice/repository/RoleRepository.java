package org.subhankar.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.subhankar.userservice.model.DO.RoleDO;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleDO, String>{
    Optional<RoleDO> findByName(String role);
}
