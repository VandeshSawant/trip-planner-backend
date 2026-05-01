package com.vandesh.tripplanner.trip_planner_api.controller;

import com.vandesh.tripplanner.trip_planner_api.dto.CreateUserRequest;
import com.vandesh.tripplanner.trip_planner_api.dto.TripResponse;
import com.vandesh.tripplanner.trip_planner_api.entity.User;
import com.vandesh.tripplanner.trip_planner_api.service.TripMemberService;
import com.vandesh.tripplanner.trip_planner_api.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

  private final TripMemberService tripMemberService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserService userService, TripMemberService tripMemberService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.tripMemberService = tripMemberService;
    this.passwordEncoder = passwordEncoder;
  }

  private Long getCurrentUserId() {
    return (Long) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
  }

  @GetMapping
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @PostMapping
  public User createUser(@RequestBody CreateUserRequest request) {
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    return userService.createUser(user);
  }

  @GetMapping("/my-trips")
  public List<TripResponse> getTrips() {
    return tripMemberService.getTrips(getCurrentUserId());
  }

}
