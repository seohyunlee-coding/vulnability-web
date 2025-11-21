package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping({ "/", "/home" })
  public String home(@RequestParam(required = false) String q, Model model) {
    List<Post> posts = postService.search(q);
    model.addAttribute("posts", posts);
    model.addAttribute("q", q == null ? "" : q);
    return "board/home";
  }

  @GetMapping("/posts/create")
  public String createForm(Model model) {
    model.addAttribute("post", new Post());
    return "board/create_post";
  }

  @PostMapping("/posts/create")
  public String createPost(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails user) {
    post.setAuthor(user.getUsername());
    postService.save(post);
    return "redirect:/";
  }

  @GetMapping("/my-posts")
  public String myPosts(@AuthenticationPrincipal UserDetails user, Model model) {
    List<Post> posts = postService.findByAuthor(user.getUsername());
    model.addAttribute("posts", posts);
    return "board/my_posts";
  }
}
