package com.pravvich.demo.controller;

import com.pravvich.demo.model.Account;
import com.pravvich.demo.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Account> save(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.save(account));
    }

}
