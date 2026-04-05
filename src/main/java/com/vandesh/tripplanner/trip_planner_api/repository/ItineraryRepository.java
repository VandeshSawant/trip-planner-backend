package com.vandesh.tripplanner.trip_planner_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vandesh.tripplanner.trip_planner_api.entity.Itinerary;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
  List<Itinerary> findByTripId(Long tripId);

  List<Itinerary> findByTripIdOrderByDateAsc(Long tripId);
}
