package com.vandesh.tripplanner.trip_planner_api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vandesh.tripplanner.trip_planner_api.dto.CreateItineraryRequest;
import com.vandesh.tripplanner.trip_planner_api.dto.CreateTripRequest;
import com.vandesh.tripplanner.trip_planner_api.dto.ItineraryResponse;
import com.vandesh.tripplanner.trip_planner_api.dto.JoinTripRequest;
import com.vandesh.tripplanner.trip_planner_api.dto.LeaveTripRequest;
import com.vandesh.tripplanner.trip_planner_api.dto.TripMemberResponse;
import com.vandesh.tripplanner.trip_planner_api.entity.Itinerary;
import com.vandesh.tripplanner.trip_planner_api.entity.Trip;
import com.vandesh.tripplanner.trip_planner_api.entity.TripMember;
import com.vandesh.tripplanner.trip_planner_api.service.ItineraryService;
import com.vandesh.tripplanner.trip_planner_api.service.TripMemberService;
import com.vandesh.tripplanner.trip_planner_api.service.TripService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final TripMemberService tripMemberService;
    private final ItineraryService itineraryService;

    public TripController(TripService tripService, TripMemberService tripMemberService,
            ItineraryService itineraryService) {
        this.tripService = tripService;
        this.tripMemberService = tripMemberService;
        this.itineraryService = itineraryService;
    }

    @PostMapping
    public Trip createTrip(@RequestBody CreateTripRequest request) {
        Trip trip = new Trip();
        trip.setTripName(request.getTripName());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        return tripService.createTrip(trip, request.getUserId());
    }

    @PostMapping("/{tripId}/join")
    public TripMember joinTrip(
            @PathVariable Long tripId,
            @RequestBody JoinTripRequest request) {
        return tripMemberService.joinTrip(tripId, request.getUserId());
    }

    @GetMapping("/{tripId}/members")
    public List<TripMemberResponse> getMembers(@PathVariable Long tripId) {
        return tripMemberService.getMembers(tripId);
    }

    @DeleteMapping("/{tripId}/leave")
    public void leaveTrip(
            @PathVariable Long tripId,
            @RequestBody LeaveTripRequest request) {
        tripMemberService.leaveTrip(tripId, request.getUserId());
    }

    @PostMapping("/{tripId}/itinerary")
    public ItineraryResponse createItinerary(@PathVariable Long tripId, @RequestBody CreateItineraryRequest request) {
        Itinerary itinerary = itineraryService.addItinerary(tripId, request);
        ItineraryResponse response = new ItineraryResponse();
        response.setId(itinerary.getId());
        response.setTitle(itinerary.getTitle());
        response.setDescription(itinerary.getDescription());
        response.setDate(itinerary.getDate().toString());
        return response;
    }

    @GetMapping("/{tripId}/itinerary")
    public List<ItineraryResponse> getItinerary(@PathVariable Long tripId) {
        List<Itinerary> itinerary = itineraryService.getItinerary(tripId);
        return itinerary.stream().map(i -> {
            ItineraryResponse response = new ItineraryResponse();
            response.setId(i.getId());
            response.setTitle(i.getTitle());
            response.setDescription(i.getDescription());
            response.setDate(i.getDate().toString());
            return response;
        }).toList();
    }

}
