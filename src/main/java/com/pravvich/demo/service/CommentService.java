package com.pravvich.demo.service;

import org.javers.core.commit.Commit;

public interface CommentService {
    void save(Commit commit);
}
