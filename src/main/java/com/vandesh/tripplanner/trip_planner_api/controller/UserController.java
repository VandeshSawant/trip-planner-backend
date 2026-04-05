package com.vandesh.tripplanner.trip_planner_api.controller;

import com.vandesh.tripplanner.trip_planner_api.dto.TripResponse;
import com.vandesh.tripplanner.trip_planner_api.entity.User;
import com.vandesh.tripplanner.trip_planner_api.service.TripMemberService;
import com.vandesh.tripplanner.trip_planner_api.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

  private final TripMemberService tripMemberService;
  private final UserService userService;

  public UserController(UserService userService, TripMemberService tripMemberService) {
    this.userService = userService;
    this.tripMemberService = tripMemberService;
  }

  @GetMapping
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @GetMapping("/{userId}/trips")
  public List<TripResponse> getTrips(@PathVariable Long userId) {
    return tripMemberService.getTrips(userId);
  }

}
