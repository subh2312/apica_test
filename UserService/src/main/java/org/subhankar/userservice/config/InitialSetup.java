package org.subhankar.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.subhankar.userservice.model.DO.RoleDO;
import org.subhankar.userservice.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitialSetup implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        Set<RoleDO> availableRoles = new HashSet<>(roleRepository.findAll());
        if (availableRoles.isEmpty()) {
            roleRepository.save(RoleDO.builder().name("ADMIN").code("ADMN").build());
            roleRepository.save(RoleDO.builder().name("USER").code("USR").build());
        }else if (availableRoles.size() == 1) {
            if (availableRoles.stream().anyMatch(roleDO -> roleDO.getName().equals("ADMIN"))) {
                roleRepository.save(RoleDO.builder().name("USER").code("USR").build());
            }else {
                roleRepository.save(RoleDO.builder().name("ADMIN").code("ADMN").build());
            }
        }

    }
}
