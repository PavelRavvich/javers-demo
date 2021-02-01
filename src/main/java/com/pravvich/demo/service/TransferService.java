package com.pravvich.demo.service;

import com.pravvich.demo.dto.MoneyTransferDto;
import com.pravvich.demo.model.MoneyTransfer;

public interface TransferService {
    MoneyTransfer getById(Long transferId);
    MoneyTransferDto save(MoneyTransferDto transfer);
}
