package com.cosmos.service;

import com.cosmos.entity.Account;
import com.cosmos.model.Login;
import com.cosmos.model.UpdateAccount;

public interface AccountService {
   Account login(Login lg);

   void updateAccount(UpdateAccount updateAccount);
}
