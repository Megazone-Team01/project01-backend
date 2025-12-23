package com.mzcteam01.mzcproject01be.security;

import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).get();

        if (user == null){
            throw new RuntimeException(email + " Not found.");
        }

        // 인증된 사용자 정보를 담음
        AuthUser authUser = new AuthUser(user.getId(),user.getEmail(), user.getName(),
                user.getRole().getName(), user.getType());


        return authUser;
    }

}