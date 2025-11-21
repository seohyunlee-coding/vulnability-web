package com.example.demo.controller;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

  private final CustomUserDetailsService userDetailsService;

  public SignupController(CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @GetMapping("/signup")
  public String signupForm() {
    return "registration/signup";
  }

  @PostMapping("/signup")
  public String signupSubmit(@RequestParam String username, @RequestParam String password, Model model) {
    try {
      userDetailsService.register(username, password);
    } catch (IllegalArgumentException ex) {
      model.addAttribute("error", ex.getMessage());
      return "registration/signup";
    }
    return "redirect:/login?registered";
  }
}
