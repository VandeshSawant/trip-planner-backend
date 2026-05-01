package com.vandesh.tripplanner.trip_planner_api.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vandesh.tripplanner.trip_planner_api.dto.CreateItineraryRequest;
import com.vandesh.tripplanner.trip_planner_api.entity.Itinerary;
import com.vandesh.tripplanner.trip_planner_api.entity.Trip;
import com.vandesh.tripplanner.trip_planner_api.repository.ItineraryRepository;
import com.vandesh.tripplanner.trip_planner_api.repository.TripRepository;

@Service
public class ItineraryService {
  private final TripRepository tripRepository;
  private final ItineraryRepository itineraryRepository;

  public ItineraryService(TripRepository tripRepository, ItineraryRepository itineraryRepository) {
    this.tripRepository = tripRepository;
    this.itineraryRepository = itineraryRepository;
  }

  public Itinerary addItinerary(Long tripId, CreateItineraryRequest request) {

    Trip trip = tripRepository.findById(tripId)
        .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

    if (request.getTitle() == null || request.getTitle().isBlank()) {
      throw new IllegalArgumentException("Title is required");
    }

    if (request.getDate() == null) {
      throw new IllegalArgumentException("Date is required");
    }

    if (request.getDate().isBefore(trip.getStartDate()) ||
        request.getDate().isAfter(trip.getEndDate())) {
      throw new IllegalArgumentException("Date must be within trip duration");
    }

    Itinerary itinerary = new Itinerary();
    itinerary.setTitle(request.getTitle());
    itinerary.setDescription(request.getDescription());
    itinerary.setDate(request.getDate());
    itinerary.setTrip(trip);

    return itineraryRepository.save(itinerary);
  }

  public List<Itinerary> getItinerary(Long tripId) {
    if (!tripRepository.existsById(tripId)) {
      throw new IllegalArgumentException("Trip not found");
    }
    return itineraryRepository.findByTripIdOrderByDateAsc(tripId);
  }

  public Itinerary updateItinerary(Long tripId, Long itineraryId, Long userId, CreateItineraryRequest request) {
    Trip trip = tripRepository.findById(tripId)
        .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

    Itinerary itinerary = itineraryRepository.findById(itineraryId)
        .orElseThrow(() -> new IllegalArgumentException("Itinerary not found"));

    if (trip.getCreatedBy().getId().equals(userId)) {
      if (request.getTitle() != null && !request.getTitle().isBlank()) {
        itinerary.setTitle(request.getTitle());
      }
      if (request.getDescription() != null) {
        itinerary.setDescription(request.getDescription());
      }
      if (request.getDate() != null) {
        if (request.getDate().isBefore(trip.getStartDate()) ||
            request.getDate().isAfter(trip.getEndDate())) {
          throw new IllegalArgumentException("Date must be within trip duration");
        }
        itinerary.setDate(request.getDate());
      }
      return itineraryRepository.save(itinerary);
    } else {
      throw new IllegalArgumentException("Only trip creator can update itinerary");
    }
  }

  public void deleteItinerary(Long tripId, Long itineraryId, Long userId) {
    Trip trip = tripRepository.findById(tripId)
        .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

    Itinerary itinerary = itineraryRepository.findById(itineraryId)
        .orElseThrow(() -> new IllegalArgumentException("Itinerary not found"));

    if (trip.getCreatedBy().getId().equals(userId)) {
      itineraryRepository.delete(itinerary);
    } else {
      throw new IllegalArgumentException("Only trip creator can delete itinerary");
    }
  }
}
