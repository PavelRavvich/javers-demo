package com.pravvich.demo.service;

import com.pravvich.demo.model.Company;

public interface CompanyService {
    Company getById(Long companyId);
    Company save(Company company);
    void delete(Company company);
}
