package com.vandesh.tripplanner.trip_planner_api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TripMemberResponse {
  private Long userId;
  private String name;
  private LocalDateTime joinedAt;
}
