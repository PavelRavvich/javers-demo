package com.pravvich.demo.service;

import com.pravvich.demo.model.Comment;
import com.pravvich.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.javers.core.diff.Change;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment findByAuditGroupId(String auditGroupId) {
        return commentRepository
                .findByAuditGroupId(UUID.fromString(auditGroupId))
                .orElse(new Comment());
    }

    @Override
    public List<Comment> findByAuditGroupIdIn(List<UUID> auditGroupIds) {
        return commentRepository.findByAuditGroupIdIn(auditGroupIds);
    }

    @Override
    public List<Comment> findByChanges(List<Change> changes) {
        List<UUID> auditGroupIds = changes.stream()
                .map(change -> UUID.fromString(
                        change.getCommitMetadata()
                                .orElseThrow()
                                .getProperties()
                                .get("auditGroupId"))
                ).collect(Collectors.toList());
        return findByAuditGroupIdIn(auditGroupIds);
    }
}
