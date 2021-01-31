package com.pravvich.demo.controller;

import com.pravvich.demo.dto.ChangeBunchDto;
import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.Comment;
import com.pravvich.demo.model.Company;
import com.pravvich.demo.model.Transfer;
import com.pravvich.demo.repository.CommentRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/audit")
public class AuditController {

    private final Javers javers;
    private final CommentRepository commentRepository;

    @GetMapping("/company")
    public ResponseEntity<String> getCompanyChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Company.class)
                .withNewObjectChanges();
        List<Change> changes = javers.findChanges(jqlQuery.build());
        String resp = javers.getJsonConverter().toJson(changes);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/account/{id}")
    public String getAccountChanges(@PathVariable(name = "id") Long id) {
        JqlQuery accountQuery = QueryBuilder.byInstanceId(id, Account.class)
                .withNewObjectChanges()
                .build();
        List<Change> accountChanges = javers.findChanges(accountQuery);

        Map<String, ChangeBunch> changeBunches = new HashMap<>();

        for (Change accountChange : accountChanges) {
            String auditGroupId = accountChange.getCommitMetadata()
                    .orElseThrow()
                    .getProperties()
                    .get("auditGroupId");
            if (changeBunches.containsKey(auditGroupId)) {
                changeBunches.get(auditGroupId).getChanges().add(accountChange);
            } else {
                JqlQuery transferQuery = QueryBuilder.byClass(Transfer.class)
                        .withCommitProperty("auditGroupId", auditGroupId)
                        .withNewObjectChanges()
                        .build();

                Changes changes = javers.findChanges(transferQuery);
                List<Change> additionChanges = new ArrayList<>(changes);
                additionChanges.add(accountChange);

                Comment comment = commentRepository
                        .findByAuditGroupId(UUID.fromString(auditGroupId))
                        .orElse(new Comment());

                ChangeBunch changeBunch = ChangeBunch.builder()
                        .changes(additionChanges)
                        .comment(comment)
                        .build();

                changeBunches.put(auditGroupId, changeBunch);
            }
        }

        List<ChangeBunchDto> bunches = new ArrayList<>();
        for (String auditGroupId : changeBunches.keySet()) {
            ChangeBunchDto butchDto = new ChangeBunchDto();
            butchDto.setAuditGroupId(auditGroupId);
            ChangeBunch changeBunch = changeBunches.get(auditGroupId);
            for (Change change : changeBunch.getChanges()) {
                if (change instanceof ValueChange) {
                    ValueChange updateObjectChange = (ValueChange) change;
                    String changeMessage = extractUpdateObjectChange(updateObjectChange);
                    butchDto.addChange(changeMessage);
                }
            }
            bunches.add(butchDto);
        }

        return javers.getJsonConverter().toJson(bunches);
    }

    String extractUpdateObjectChange(ValueChange valueChange) {
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

    @Builder
    @Data
    static class ChangeBunch {
        private Comment comment;
        private List<Change> changes;
    }

    @GetMapping("/transfer/any")
    public ResponseEntity<String> getAnyChanges() {
        QueryBuilder jqlQuery = QueryBuilder.anyDomainObject()
                .withNewObjectChanges()
                .withChangedProperty("value")
                .withChangedProperty("balance");
        List<Change> changes = javers.findChanges(jqlQuery.build());
        String resp = javers.getJsonConverter().toJson(changes);
        return ResponseEntity.ok(resp);
    }

}