package com.pravvich.demo.service;

import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.Comment;
import com.pravvich.demo.repository.AccountRepository;
import com.pravvich.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.core.commit.Commit;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final Javers javers;
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;

    @Override
    public Account getById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    @JaversAuditable
    public Account save(Account account) {
        Account saved = accountRepository.save(account);
        final Commit commit = javers.commit("unauthorized", saved);
        final long majorId = commit.getId().getMajorId();
        final Comment comment = Comment.builder()
                .id(majorId)
                .text("Important change")
                .build();
        commentRepository.save(comment);
        return saved;
    }

}
