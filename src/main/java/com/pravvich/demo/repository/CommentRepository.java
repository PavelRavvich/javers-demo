package com.pravvich.demo.repository;

import com.pravvich.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository
        extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
}