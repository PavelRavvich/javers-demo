package com.pravvich.demo.service;

import com.pravvich.demo.dto.ChangeBunchDto;
import com.pravvich.demo.model.BankAccount;
import com.pravvich.demo.model.Comment;
import com.pravvich.demo.model.MoneyTransfer;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final Javers javers;
    private final CommentService commentService;

    @Override
    public List<ChangeBunchDto> getChangesByAccountId(Long accountId) {
        JqlQuery accountQuery = QueryBuilder.byInstanceId(accountId, BankAccount.class)
                .withNewObjectChanges()
                .build();

        Map<String, ChangeBunch> changeBunches = new HashMap<>();

        javers.findChanges(accountQuery).forEach(accountChange -> {
            String auditGroupId = accountChange.getCommitMetadata().orElseThrow()
                    .getProperties().get("auditGroupId");
            if (changeBunches.containsKey(auditGroupId)) {
                changeBunches.get(auditGroupId).getChanges().add(accountChange);
            } else {
                List<Change> transferChanges = getTransferChanges(auditGroupId);
                transferChanges.add(accountChange);

                ChangeBunch changeBunch = ChangeBunch.builder()
                        .comment(commentService.findByAuditGroupId(auditGroupId))
                        .changes(transferChanges)
                        .build();

                changeBunches.put(auditGroupId, changeBunch);
            }
        });

        return mapDto(changeBunches);
    }

    private List<Change> getTransferChanges(String auditGroupId) {
        JqlQuery transferQuery = QueryBuilder.byClass(MoneyTransfer.class)
                .withCommitProperty("auditGroupId", auditGroupId)
                .withNewObjectChanges()
                .build();
        Changes changes = javers.findChanges(transferQuery);
        return new ArrayList<>(changes);
    }

    private List<ChangeBunchDto> mapDto(Map<String, ChangeBunch> changeBunches) {
        List<ChangeBunchDto> bunches = new ArrayList<>(changeBunches.size());
        changeBunches.keySet().forEach(auditGroupId -> {
            ChangeBunchDto butchDto = new ChangeBunchDto();
            butchDto.setAuditGroupId(auditGroupId);
            butchDto.setComment(changeBunches.get(auditGroupId).getComment().getText());
            ChangeBunch changeBunch = changeBunches.get(auditGroupId);
            changeBunch.getChanges().stream()
                    .filter(change -> change instanceof ValueChange)
                    .map(change -> (ValueChange) change)
                    .map(this::extractUpdateObjectChange)
                    .forEachOrdered(butchDto::addChange);
            bunches.add(butchDto);
        });
        return bunches;
    }

    // TODO: 2/1/2021 move to MessageProviderService
    private String extractUpdateObjectChange(ValueChange valueChange) {
        StringBuilder sb = new StringBuilder("Update ");
        String oldValue = nonNull(valueChange.getLeft()) ? valueChange.getLeft().toString() : "";
        String newValue = nonNull(valueChange.getRight()) ? valueChange.getRight().toString() : "";
        sb.append(valueChange.getPropertyName())
                .append(" from ").append(oldValue.isEmpty() ? "null" : oldValue)
                .append(" to ").append(newValue.isEmpty() ? "null" : newValue);
        valueChange.getCommitMetadata().ifPresent(commitMetadata ->
                sb.append(" Date:").append(commitMetadata.getCommitDate())
                        .append(" Author: ").append(commitMetadata.getAuthor()));
        return sb.toString();
    }

    @Data
    @Builder
    static class ChangeBunch {
        private Comment comment;
        private List<Change> changes;
    }

}
