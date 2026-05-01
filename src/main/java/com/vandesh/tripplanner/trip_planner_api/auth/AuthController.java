package com.vandesh.tripplanner.trip_planner_api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vandesh.tripplanner.trip_planner_api.entity.User;
import com.vandesh.tripplanner.trip_planner_api.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    // Find user by email
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Check password — plain text for now, we'll add hashing next
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    // Generate and return the token
    String token = jwtUtil.generateToken(user.getId());
    return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getName()));
  }
}
