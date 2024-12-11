package com.luomsa.feeds.service;

import com.luomsa.feeds.StringUtils;
import com.luomsa.feeds.dto.PostDto;
import com.luomsa.feeds.dto.PagePostDto;
import com.luomsa.feeds.dto.UserDto;
import com.luomsa.feeds.entity.Post;
import com.luomsa.feeds.exception.NotFoundException;
import com.luomsa.feeds.repository.PostRepository;
import com.luomsa.feeds.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostDto createPost(String username, String title, String content) {
        var user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isEmpty()) throw new NotFoundException("User not found");
        var post = new Post(title.trim(), StringUtils.toSlug(title.trim()), content, user.get());
        postRepository.save(post);
        return new PostDto(post.getId(), post.getTitle(), post.getSlug(), post.getContent(),
                new UserDto(post.getAuthor().getId(), post.getAuthor().getUsername()), post.getComments().size(), post.getCreatedAt());
    }

    public PagePostDto getCommentedPosts(int page) {
        var posts = postRepository.findAllByOrderByComments(PageRequest.of(page, 25));
        return new PagePostDto(posts.getContent().stream().map(post ->
                new PostDto(post.getId(), post.getTitle(), post.getSlug(), post.getContent(),
                        new UserDto(post.getAuthor().getId(), post.getAuthor().getUsername()), post.getComments().size(), post.getCreatedAt())).toList(), posts.hasNext(), posts.getTotalPages());
    }

    public PostDto getPost(long id) {
        var postOptional = postRepository.findPostById(id);
        if (postOptional.isEmpty()) throw new NotFoundException("Post not found");
        var post = postOptional.get();
        return new PostDto(post.getId(), post.getTitle(), post.getSlug(), post.getContent(),
                new UserDto(post.getAuthor().getId(), post.getAuthor().getUsername()),
                post.getComments().size(), post.getCreatedAt());
    }

    public PagePostDto getLatestPosts(int page) {
        var posts = postRepository.findAllByOrderByLatestCommentAtDesc(PageRequest.of(page, 25));
        return new PagePostDto(posts.getContent().stream().map(post ->
                new PostDto(post.getId(), post.getTitle(), post.getSlug(), post.getContent(),
                        new UserDto(post.getAuthor().getId(), post.getAuthor().getUsername()), post.getComments().size(), post.getCreatedAt())).toList(),
                posts.hasNext(), posts.getTotalPages());
    }

}
