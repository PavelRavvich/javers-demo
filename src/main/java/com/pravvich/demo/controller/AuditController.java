package com.pravvich.demo.controller;

import com.pravvich.demo.dto.ChangeBunchDto;
import com.pravvich.demo.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/audit")
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/company")
    public ResponseEntity<String> getCompanyChanges() {
        return ok(null);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<ChangeBunchDto>> getAccountChanges(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(auditService.getChangesById(id));
    }

}