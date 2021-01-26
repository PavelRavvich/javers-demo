package com.pravvich.demo.service;

import com.pravvich.demo.model.Account;
import com.pravvich.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @JaversAuditable
    public Account save(Account account) {
        return accountRepository.save(account);
    }

}
