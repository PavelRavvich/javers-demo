package com.pravvich.demo.service;

import com.pravvich.demo.model.Transfer;
import com.pravvich.demo.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.stereotype.Service;

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
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }
}
