package com.vandesh.tripplanner.trip_planner_api.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
  private String name;
  private String email;
  private String password;
}
