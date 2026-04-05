package com.vandesh.tripplanner.trip_planner_api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateTripRequest {
  private String tripName;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long userId;
}
