package com.vandesh.tripplanner.trip_planner_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vandesh.tripplanner.trip_planner_api.dto.TripMemberResponse;
import com.vandesh.tripplanner.trip_planner_api.dto.TripResponse;
import com.vandesh.tripplanner.trip_planner_api.entity.Trip;
import com.vandesh.tripplanner.trip_planner_api.entity.TripMember;
import com.vandesh.tripplanner.trip_planner_api.entity.User;
import com.vandesh.tripplanner.trip_planner_api.repository.TripMemberRepository;
import com.vandesh.tripplanner.trip_planner_api.repository.TripRepository;
import com.vandesh.tripplanner.trip_planner_api.repository.UserRepository;

@Service
public class TripMemberService {
  private final UserRepository userRepository;
  private final TripRepository tripRepository;
  private final TripMemberRepository tripMemberRepository;

  public TripMemberService(UserRepository userRepository, TripRepository tripRepository,
      TripMemberRepository tripMemberRepository) {
    this.userRepository = userRepository;
    this.tripRepository = tripRepository;
    this.tripMemberRepository = tripMemberRepository;
  }

  public TripMember joinTrip(Long tripId, Long userId) {
    Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalArgumentException("Trip not found"));
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (tripMemberRepository.existsByTripIdAndUserId(tripId, userId)) {
      throw new IllegalArgumentException("User already joined this trip");
    }
    TripMember tripMember = new TripMember();
    tripMember.setTrip(trip);
    tripMember.setUser(user);
    return tripMemberRepository.save(tripMember);
  }

  public List<TripMemberResponse> getMembers(Long tripId) {
    List<TripMember> members = tripMemberRepository.findByTripId(tripId);
    return members.stream().map(member -> {
      TripMemberResponse response = new TripMemberResponse();
      response.setUserId(member.getUser().getId());
      response.setName(member.getUser().getName());
      response.setJoinedAt(member.getJoinedAt());
      return response;
    }).toList();
  }

  public List<TripResponse> getTrips(Long userId) {
    List<TripMember> members = tripMemberRepository.findByUserId(userId);
    return members.stream().map(member -> {
      TripResponse response = new TripResponse();
      response.setTripId(member.getTrip().getId());
      response.setTripName(member.getTrip().getTripName());
      response.setDestination(member.getTrip().getDestination());
      response.setStartDate(member.getTrip().getStartDate());
      response.setEndDate(member.getTrip().getEndDate());
      return response;
    }).toList();
  }

  @Transactional
  public void leaveTrip(Long tripId, Long userId) {
    if (!tripMemberRepository.existsByTripIdAndUserId(tripId, userId)) {
      throw new IllegalArgumentException("User is not a member of this trip");
    }
    tripMemberRepository.deleteByTripIdAndUserId(tripId, userId);
  }
}
