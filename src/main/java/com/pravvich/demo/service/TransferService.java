package com.pravvich.demo.service;

import com.pravvich.demo.dto.TransferDto;
import com.pravvich.demo.model.Transfer;

public interface TransferService {
    Transfer getById(Long transferId);
    TransferDto save(TransferDto transfer);
}
