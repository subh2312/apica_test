package org.subhankar.authenticationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.subhankar.authenticationservice.integration.UserService;
import org.subhankar.authenticationservice.model.DTO.GetUserByEmailDTO;

@Configuration
@RequiredArgsConstructor
public class DependantBeans {
    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
       return email -> userService.getUserByEmail(GetUserByEmailDTO.builder().email(email).build());    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
