package com.pravvich.demo.service;

import com.pravvich.demo.dto.ChangeBunchDto;

import java.util.List;

public interface AuditService {

    List<ChangeBunchDto> getChangesById(Long accountId);

}
