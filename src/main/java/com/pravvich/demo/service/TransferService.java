package com.pravvich.demo.service;

import com.pravvich.demo.model.Transfer;

public interface TransferService {
    Transfer getById(Long transferId);
    Transfer save(Transfer transfer);
}
