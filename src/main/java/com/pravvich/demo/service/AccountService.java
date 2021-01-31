package com.pravvich.demo.service;

import com.pravvich.demo.dto.AccountDto;
import com.pravvich.demo.model.Account;

public interface AccountService {
    Account getById(Long accountId);
    AccountDto save(AccountDto account);
}
