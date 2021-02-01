package com.pravvich.demo.service;

import com.pravvich.demo.dto.BankAccountDto;
import com.pravvich.demo.model.BankAccount;

public interface AccountService {
    BankAccount getById(Long accountId);
    BankAccountDto save(BankAccountDto account);
}
