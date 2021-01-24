package com.pravvich.demo.repository;

import com.pravvich.demo.model.Company;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface CompanyRepository
        extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
}
