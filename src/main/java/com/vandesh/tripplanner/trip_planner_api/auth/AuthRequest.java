// AuthRequest.java
package com.vandesh.tripplanner.trip_planner_api.auth;

import lombok.Data;

@Data
public class AuthRequest {
  private String email;
  private String password;
}