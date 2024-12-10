package com.luomsa.feeds.repository;

import com.luomsa.feeds.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> getAllByPostIdOrderByCreatedAt(long id, Pageable pageable);

    int countCommentsByPostId(Long id);
}
