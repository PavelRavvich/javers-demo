package com.pravvich.demo.controller;

import com.pravvich.demo.dto.MoneyTransferDto;
import com.pravvich.demo.model.MoneyTransfer;
import com.pravvich.demo.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/transfer")
public class TransferController {

    private final TransferService transferService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<MoneyTransfer> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(transferService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MoneyTransferDto> save(@RequestBody MoneyTransferDto transfer) {
        return ResponseEntity.ok(transferService.save(transfer));
    }
}
