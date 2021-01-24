package com.pravvich.demo.controller;

import com.pravvich.demo.model.Company;
import com.pravvich.demo.service.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/company")
public class CompanyController {

    private final CompanyServiceImpl companyService;

    @GetMapping("/id")
    public ResponseEntity<Company> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(companyService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Company> save(@RequestBody Company company) {
        return ResponseEntity.ok(companyService.save(company));
    }

    @DeleteMapping
    public ResponseEntity<Long> delete(@RequestBody Company company) {
        companyService.delete(company);
        return ResponseEntity.ok(company.getId());
    }

}
