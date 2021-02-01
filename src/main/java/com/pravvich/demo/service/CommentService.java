package com.pravvich.demo.service;

import com.pravvich.demo.model.Comment;
import org.javers.core.diff.Change;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment findByAuditGroupId(String auditGroupId);
    List<Comment> findByAuditGroupIdIn(List<UUID> auditGroupIds);
    List<Comment> findByChanges(List<Change> changes);
}
