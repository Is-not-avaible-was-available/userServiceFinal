package com.learning.UserServiceFinal.Security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.learning.UserServiceFinal.Models.User;
import com.learning.UserServiceFinal.Repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@Setter
@JsonDeserialize(as = CustomUserDetailsService.class)
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @JsonIgnore
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("Username not found!");
        }
        User user = userOptional.get();
        return new CustomUserDetails(user);
    }
}
