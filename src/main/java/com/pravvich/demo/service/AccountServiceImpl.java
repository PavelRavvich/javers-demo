package com.pravvich.demo.service;

import com.pravvich.demo.dto.BankAccountDto;
import com.pravvich.demo.model.AuditMetadata;
import com.pravvich.demo.model.BankAccount;
import com.pravvich.demo.model.Comment;
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
    public BankAccount getById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public BankAccountDto save(BankAccountDto dto) {
        final BankAccount bankAccount = BankAccount.builder()
                .id(dto.getId())
                .auditMetadata(new AuditMetadata())
                .balance(dto.getBalance())
                .number(dto.getNumber())
                .build();

        BankAccount saved = save(bankAccount);

        // TODO: 2/1/2021 add comment to DTO
        Comment comment = Comment.builder()
                .text("Some comment text")
                .auditGroupId(dto.getAuditGroupId())
                .build();
        commentRepository.save(comment);

        return BankAccountDto.builder()
                .auditGroupId(bankAccount.getAuditMetadata().getAuditGroupId())
                .balance(saved.getBalance())
                .id(saved.getId())
                .number(saved.getNumber())
                .build();
    }

    @JaversAuditable
    public BankAccount save(BankAccount bankAccount) {
        return accountRepository.save(bankAccount);
    }
}
