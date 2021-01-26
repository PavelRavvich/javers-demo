package com.pravvich.demo.repository;

import com.pravvich.demo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransferRepository
        extends JpaRepository<Transfer, Long>, JpaSpecificationExecutor<Transfer> {
}
