package com.pravvich.demo.repository;

import com.pravvich.demo.model.Transfer;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface TransferRepository
        extends JpaRepository<Transfer, Long>, JpaSpecificationExecutor<Transfer> {
}
