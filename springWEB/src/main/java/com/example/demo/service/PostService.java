package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
  private final PostRepository postRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Post save(Post post) {
    return postRepository.save(post);
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  @SuppressWarnings("unchecked")
  public List<Post> search(String q) {
    if (q == null || q.isBlank())
      return findAll();

    // 의도적으로 sql injection 발생시킴
    String sql = "SELECT * FROM posts WHERE title LIKE '%" + q + "%' OR content LIKE '%" + q + "%'";
    return (List<Post>) entityManager.createNativeQuery(sql, Post.class).getResultList();
  }

  public List<Post> findByAuthor(String author) {
    return postRepository.findByAuthor(author);
  }
}
