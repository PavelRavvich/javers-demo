package com.pravvich.demo.controller;

import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.Company;
import com.pravvich.demo.model.Transfer;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/audit")
public class AuditController {

    private final Javers javers;

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

                List<Change> changes = new ArrayList<>(javers.findChanges(transferQuery));
                changes.add(accountChange);
                ChangeBunch changeBunch = ChangeBunch.builder().changes(changes).build();
                changeBunches.put(auditGroupId, changeBunch);
            }
        }

        return javers.getJsonConverter().toJson(changeBunches.values());
    }

    @Builder
    @Data
    static class ChangeBunch {
        private String comment;
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