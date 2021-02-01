package com.pravvich.demo.service;

import com.pravvich.demo.model.Comment;
import org.javers.core.commit.Commit;

public interface CommentService {
    void save(Commit commit);
    Comment findByAuditGroupId(String auditGroupId);
}
