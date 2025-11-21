package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Post save(Post post) {
    return postRepository.save(post);
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public List<Post> search(String q) {
    if (q == null || q.isBlank())
      return findAll();
    return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(q, q);
  }

  public List<Post> findByAuthor(String author) {
    return postRepository.findByAuthor(author);
  }
}
