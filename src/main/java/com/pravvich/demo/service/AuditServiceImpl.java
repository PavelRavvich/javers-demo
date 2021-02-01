package com.pravvich.demo.service;

import com.pravvich.demo.dto.ChangeBunchDto;
import com.pravvich.demo.model.BankAccount;
import com.pravvich.demo.model.Comment;
import com.pravvich.demo.model.MoneyTransfer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final Javers javers;
    private final CommentService commentService;
    private final MessageProviderService messageProviderService;

    @Override
    public List<ChangeBunchDto> getChangesByAccountId(Long accountId) {
        JqlQuery accountQuery = QueryBuilder.byInstanceId(accountId, BankAccount.class)
                .withNewObjectChanges()
                .build();

        Map<String, ChangeBunch> changeBunches = new HashMap<>();
        List<Change> changes = javers.findChanges(accountQuery);
        changes.forEach(accountChange -> {
            String auditGroupId = accountChange.getCommitMetadata().orElseThrow()
                    .getProperties().get("auditGroupId");
            if (changeBunches.containsKey(auditGroupId)) {
                changeBunches.get(auditGroupId).getChanges().add(accountChange);
            } else {
                List<Change> transferChanges = getTransferChanges(auditGroupId);
                transferChanges.add(accountChange);

                ChangeBunch changeBunch = new ChangeBunch();
                changeBunch.setChanges(transferChanges);
                accountChange.getCommitMetadata().ifPresent(data ->
                        changeBunch.setDate(data.getCommitDate()));

                changeBunches.put(auditGroupId, changeBunch);
            }
        });

        return mapToDto(changeBunches, commentService.findByChanges(changes));
    }

    private List<Change> getTransferChanges(String auditGroupId) {
        JqlQuery transferQuery = QueryBuilder.byClass(MoneyTransfer.class)
                .withCommitProperty("auditGroupId", auditGroupId)
                .withNewObjectChanges()
                .build();
        Changes changes = javers.findChanges(transferQuery);
        return new ArrayList<>(changes);
    }

    private List<ChangeBunchDto> mapToDto(Map<String, ChangeBunch> changeBunches, List<Comment> comments) {
        List<ChangeBunchDto> bunches = new ArrayList<>(changeBunches.size());
        changeBunches.keySet().forEach(auditGroupId -> {
            ChangeBunch changeBunch = changeBunches.get(auditGroupId);
            ChangeBunchDto bunchDto = new ChangeBunchDto();
            String comment = comments.stream()
                    .filter(item -> auditGroupId.equals(item.getAuditGroupId().toString()))
                    .map(Comment::getText)
                    .collect(Collectors.joining());
            bunchDto.setComment(comment);
            changeBunch.getChanges().stream()
                    .filter(change -> change instanceof ValueChange)
                    .map(change -> (ValueChange) change)
                    .filter(messageProviderService::hasMessage)
                    .map(messageProviderService::convertToMessage)
                    .forEachOrdered(bunchDto::addChange);
            bunchDto.setAuditGroupId(auditGroupId);
            bunchDto.setDate(changeBunch.getDate());

            bunches.add(bunchDto);
        });
        return bunches;
    }

    @Data
    @Builder
    @NoArgsConstructor
    static class ChangeBunch {
        private LocalDateTime date;
        private Comment comment;
        private List<Change> changes;
    }

}
