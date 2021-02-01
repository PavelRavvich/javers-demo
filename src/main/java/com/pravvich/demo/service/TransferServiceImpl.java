package com.pravvich.demo.service;

import com.pravvich.demo.dto.MoneyTransferDto;
import com.pravvich.demo.model.AuditMetadata;
import com.pravvich.demo.model.BankAccount;
import com.pravvich.demo.model.Comment;
import com.pravvich.demo.model.MoneyTransfer;
import com.pravvich.demo.repository.CommentRepository;
import com.pravvich.demo.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final CommentRepository commentRepository;

    @Override
    public MoneyTransfer getById(Long transferId) {
        return transferRepository.findById(transferId)
                .orElseThrow(NoSuchElementException::new);
    }

    // TODO: 2/1/2021 refactor to mapper
    @Override
    public MoneyTransferDto save(MoneyTransferDto dto) {
        MoneyTransfer moneyTransfer = MoneyTransfer.builder()
                .id(dto.getId())
                .volume(dto.getVolume())
                .auditMetadata(
                        nonNull(dto.getAuditGroupId())
                                ? AuditMetadata.builder().auditGroupId(dto.getAuditGroupId()).build()
                                : new AuditMetadata()
                )
                .datetime(new Timestamp(System.currentTimeMillis()))
                .sender(BankAccount.builder().id(dto.getSenderId()).build())
                .recipient(BankAccount.builder().id(dto.getRecipientId()).build())
                .build();

        MoneyTransfer savedMoneyTransfer = save(moneyTransfer);

        // TODO: 2/1/2021 add comment to DTO
        Comment comment = Comment.builder()
                .text("Some comment text")
                .auditGroupId(moneyTransfer.getAuditMetadata().getAuditGroupId())
                .build();
        commentRepository.save(comment);

        return MoneyTransferDto.builder()
                .id(savedMoneyTransfer.getId())
                .volume(savedMoneyTransfer.getVolume())
                .datetime(savedMoneyTransfer.getDatetime())
                .senderId(savedMoneyTransfer.getSender().getId())
                .recipientId(savedMoneyTransfer.getRecipient().getId())
                .auditGroupId(moneyTransfer.getAuditMetadata().getAuditGroupId())
                .build();
    }

    @JaversAuditable
    public MoneyTransfer save(MoneyTransfer moneyTransfer) {
        return transferRepository.save(moneyTransfer);
    }
}
