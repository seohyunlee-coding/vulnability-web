package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    Collection<GrantedAuthority> authorities = Arrays.stream(
        (appUser.getRoles() == null ? "USER" : appUser.getRoles()).split(","))
        .map(String::trim)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    return User.withUsername(appUser.getUsername())
        .password(appUser.getPassword())
        .authorities(authorities)
        .build();
  }

  public AppUser register(String username, String rawPassword) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Username already exists");
    }
    AppUser u = AppUser.builder()
        .username(username)
        .password(passwordEncoder.encode(rawPassword))
        .roles("USER")
        .build();
    return userRepository.save(u);
  }
}
