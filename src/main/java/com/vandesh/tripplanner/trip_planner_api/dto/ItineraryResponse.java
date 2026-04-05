package com.vandesh.tripplanner.trip_planner_api.dto;

import lombok.Data;

@Data
public class ItineraryResponse {
  private Long id;
  private String title;
  private String description;
  private String date;
}
