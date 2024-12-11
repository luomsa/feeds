package com.luomsa.feeds.repository;

import com.luomsa.feeds.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByLatestCommentAtDesc(Pageable pageable);

    @Query("""
            SELECT p FROM Post p LEFT JOIN p.comments c
            GROUP BY p
            ORDER BY COUNT(c) DESC
            """)
    Page<Post> findAllByOrderByComments(Pageable pageable);

    Optional<Post> findPostById(Long id);
}