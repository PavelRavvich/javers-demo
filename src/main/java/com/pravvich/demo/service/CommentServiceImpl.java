package com.pravvich.demo.service;

import com.pravvich.demo.model.Comment;
import com.pravvich.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.javers.core.commit.Commit;
import org.javers.core.diff.changetype.PropertyChange;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void save(Commit commit) {
        String message = commit.getChanges().stream()
                .map(change -> ((PropertyChange) change).getPropertyName())
                .collect(Collectors.joining(", "));
        Comment comment = Comment.builder()
                .id(commit.getId().getMajorId())
                .text(message)
                .build();
        commentRepository.save(comment);
    }

    @Override
    public Comment findByAuditGroupId(String auditGroupId) {
        return commentRepository
                .findByAuditGroupId(UUID.fromString(auditGroupId))
                .orElse(new Comment());
    }
}
