package com.vandesh.tripplanner.trip_planner_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vandesh.tripplanner.trip_planner_api.entity.Trip;
import com.vandesh.tripplanner.trip_planner_api.entity.User;
import com.vandesh.tripplanner.trip_planner_api.repository.TripRepository;
import com.vandesh.tripplanner.trip_planner_api.repository.UserRepository;

@Service
public class TripService {
  private final UserRepository userRepository;
  private final TripRepository tripRepository;

  public TripService(UserRepository userRepository, TripRepository tripRepository) {
    this.userRepository = userRepository;
    this.tripRepository = tripRepository;
  }

  public Trip createTrip(Trip trip, Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    trip.setCreatedBy(user);
    return tripRepository.save(trip);
  }

  public List<Trip> getTrips() {
    return tripRepository.findAll();
  }

  public Trip getTripByID(Long tripId) {
    return tripRepository.findById(tripId).orElseThrow(() -> new IllegalArgumentException("Trip not found"));
  }
}
