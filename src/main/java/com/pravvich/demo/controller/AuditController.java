package com.pravvich.demo.controller;

import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.Company;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/audit")
public class AuditController {

    private final Javers javers;

    @GetMapping("/company")
    public ResponseEntity<String> getCompanyChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Company.class);
        List<Change> changes = javers.findChanges(jqlQuery.build());
        String resp = javers.getJsonConverter().toJson(changes);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/account")
    public String getAccountChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Account.class);
        List<Change> changes = javers.findChanges(jqlQuery.withChangedProperty("name").build());
        return javers.getJsonConverter().toJson(changes);
    }
}