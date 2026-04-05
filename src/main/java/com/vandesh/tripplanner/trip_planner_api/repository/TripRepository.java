package com.vandesh.tripplanner.trip_planner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vandesh.tripplanner.trip_planner_api.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> { 
  
}
