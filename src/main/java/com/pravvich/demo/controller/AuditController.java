package com.pravvich.demo.controller;

import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.Company;
import com.pravvich.demo.model.Transfer;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        JqlQuery transferQuery = QueryBuilder.byClass(Transfer.class)
                .withCommitProperty("senderId", id.toString())
                .withNewObjectChanges()
                .build();
        Changes transferChanges = javers.findChanges(transferQuery);

        List<Change> changes = Stream.of(accountChanges, transferChanges)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return javers.getJsonConverter().toJson(changes);
    }

    private long getId(Change change) {
        return change.getCommitMetadata().orElseThrow().getId().getMajorId();
    }

    @Builder
    @Data
    static class ChangeButch {
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