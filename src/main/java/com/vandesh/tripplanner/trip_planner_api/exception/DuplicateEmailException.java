package com.vandesh.tripplanner.trip_planner_api.exception;

public class DuplicateEmailException extends RuntimeException {
  public DuplicateEmailException(String email) {
    super("Email already in use: " + email);
  }
}
