package com.example.arrowdb.security;

import com.example.arrowdb.entity.Roles;
import com.example.arrowdb.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return usersRepository.findByUserName(username)
                    .map(user -> User.builder()
                            .username(user.getUserName())
                            .password(user.getPassword())
                            .disabled(user.getUserStatus().isStatValue())
                            .authorities(user.getRolesSet().stream()
                                    .map(Roles::getRoleName)
                                    .map(SimpleGrantedAuthority::new)
                                    .toList())
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь %s не найден".formatted(username)));
    }
}