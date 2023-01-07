package com.cosmos.security.service;

import com.cosmos.entity.Account;
import com.cosmos.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Account user = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username-or-password-is-not-correct"));
        return UserPrinciple.build(user);
    }

}
