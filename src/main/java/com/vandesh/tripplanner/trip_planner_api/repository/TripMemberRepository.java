package com.vandesh.tripplanner.trip_planner_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vandesh.tripplanner.trip_planner_api.entity.TripMember;

public interface TripMemberRepository extends JpaRepository<TripMember, Long> {
  boolean existsByTripIdAndUserId(Long tripId, Long userId);

  void deleteByTripIdAndUserId(Long tripId, Long userId);

  List<TripMember> findByTripId(Long tripId);

  List<TripMember> findByUserId(Long userId);
}
