package com.pravvich.demo.service;

import com.pravvich.demo.model.Account;

public interface AccountService {
    Account getById(Long accountId);
    Account save(Account account);
    void delete(Account account);
}
