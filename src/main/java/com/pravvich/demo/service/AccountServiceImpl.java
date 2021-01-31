package com.pravvich.demo.service;

import com.pravvich.demo.dto.AccountDto;
import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.AuditMetadata;
import com.pravvich.demo.repository.AccountRepository;
import com.pravvich.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;

    @Override
    public Account getById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional

    public AccountDto save(AccountDto dto) {
        final Account account = Account.builder()
                .id(dto.getId())
                .auditMetadata(new AuditMetadata())
                .balance(dto.getBalance())
                .number(dto.getNumber())
                .build();

        Account saved = save(account);

        // todo сохранение комментария c auditGroupId commentRepository...

        return AccountDto.builder()
                .auditGroupId(account.getAuditMetadata().getAuditGroupId())
                .balance(saved.getBalance())
                .id(saved.getId())
                .number(saved.getNumber())
                .build();
    }

    @JaversAuditable
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
