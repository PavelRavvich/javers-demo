package com.pravvich.demo.controller;

import com.pravvich.demo.model.Account;
import com.pravvich.demo.model.Company;
import com.pravvich.demo.model.Transfer;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.core.commit.CommitId;
import org.javers.core.diff.Change;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/audit")
public class AuditController {

    private final Javers javers;

    /**
     * todo
     * Register: create new transfer, edit transfer fields.
     */
    @GetMapping("/transfer")
    public ResponseEntity<String> getCompanyChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Transfer.class);
        List<Change> changes = javers.findChanges(jqlQuery.build());
        String resp = javers.getJsonConverter().toJson(changes);
        return ResponseEntity.ok(resp);
    }

    /**
     * todo
     * Register: create new account & company, edit transfer & account fields.
     * <p>
     * Support group by specific identifier based on javers.
     * Support grouping different requests.
     */
    @GetMapping("/account")
    public String getAccountChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Account.class);
        List<Change> changes = javers.findChanges(jqlQuery.build());
        final Set<ChangeButch> butches = changes.stream().collect(Collectors.groupingBy(this::getId))
                .values().stream()
                .map(item ->
                        ChangeButch.builder().changes(item).comment("My comment").build()
                ).collect(Collectors.toSet());
        return javers.getJsonConverter().toJson(butches);
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
}