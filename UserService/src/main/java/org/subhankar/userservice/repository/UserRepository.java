package org.subhankar.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.subhankar.userservice.model.DO.UserDO;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDO, String>{
    Optional<UserDO> findByEmail(String email);
    Optional<UserDO> findByEmailAndPassword(String email, String password);
}
