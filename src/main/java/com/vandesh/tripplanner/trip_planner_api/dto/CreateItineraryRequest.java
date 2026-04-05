package com.vandesh.tripplanner.trip_planner_api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateItineraryRequest {
  private String title;
  private String description;
  private LocalDate date;
}
