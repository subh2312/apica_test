package org.subhankar.authenticationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.subhankar.authenticationservice.integration.UserService;
import org.subhankar.authenticationservice.model.DO.UserDO;
import org.subhankar.authenticationservice.model.DTO.GetUserByEmailDTO;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userService.getUserByEmail(GetUserByEmailDTO.builder().email(username).build());
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return user;
    }
}
