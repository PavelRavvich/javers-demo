package com.pravvich.demo.repository;

import com.pravvich.demo.model.BankAccount;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface AccountRepository
        extends JpaRepository<BankAccount, Long>, JpaSpecificationExecutor<BankAccount> {
}
