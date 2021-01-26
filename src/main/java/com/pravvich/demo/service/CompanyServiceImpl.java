package com.pravvich.demo.service;

import com.pravvich.demo.model.Company;
import com.pravvich.demo.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company getById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @JaversAuditable
    public Company save(Company company) {
        return companyRepository.save(company);
    }
}
