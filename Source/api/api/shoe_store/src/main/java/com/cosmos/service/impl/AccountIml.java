package com.cosmos.service.impl;

import com.cosmos.entity.Account;
import com.cosmos.model.Login;
import com.cosmos.model.UpdateAccount;
import com.cosmos.repository.AccountRepository;
import com.cosmos.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccountIml implements AccountService {

    private final AccountRepository accRepo;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Account login(Login lg) {
        Account acc = accRepo.findTaiKhoanByEmail(lg.getEmail());
        if (acc == null) {
            System.out.println("Tai Khoan khong ton tại");
            return null;
        }

        if (passwordEncoder.matches(lg.getPassword(), acc.getPassword())) {
            System.out.println("Đúng mat khau");
            return acc;
        } else
            System.out.println("Sai Mật Khẩu");
        return null;
    }

    @Override
    @Transactional
    public void updateAccount(UpdateAccount update) {
        Account acc = accRepo.findTaiKhoanByEmail(update.getEmail());
        if (acc == null) {
            return;
        }
        if (passwordEncoder.matches(update.getOldPassword(), acc.getPassword())) {
            try {
                acc.setPassword(passwordEncoder.encode(update.getEmail()));
                accRepo.save(acc);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        }
    }
}
