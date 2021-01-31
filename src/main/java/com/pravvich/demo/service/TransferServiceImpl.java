package com.pravvich.demo.service;

import com.pravvich.demo.dto.TransferDto;
import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.Transfer;
import com.pravvich.demo.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    @Override
    public Transfer getById(Long transferId) {
        return transferRepository.findById(transferId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @JaversAuditable
    public TransferDto save(TransferDto dto) {
        Transfer transfer = Transfer.builder()
                .id(dto.getId())
                .value(dto.getValue())
                .datetime(new Timestamp(System.currentTimeMillis()))
                .sender(Account.builder().id(dto.getSenderId()).build())
                .recipient(Account.builder().id(dto.getRecipientId()).build())
                .build();

        Transfer savedTransfer = save(transfer);

        return TransferDto.builder()
                .id(savedTransfer.getId())
                .value(savedTransfer.getValue())
                .datetime(savedTransfer.getDatetime())
                .senderId(savedTransfer.getSender().getId())
                .recipientId(savedTransfer.getRecipient().getId())
                .build();
    }

    @JaversAuditable
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }
}
