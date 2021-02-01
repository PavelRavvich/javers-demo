package com.pravvich.demo.repository;

import com.pravvich.demo.model.MoneyTransfer;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface TransferRepository
        extends JpaRepository<MoneyTransfer, Long>, JpaSpecificationExecutor<MoneyTransfer> {
}
