package com.example.Uber_Clone.service;

import com.example.Uber_Clone.dto.CreateRideRequest;
import com.example.Uber_Clone.exception.BadRequestException;
import com.example.Uber_Clone.exception.NotFoundException;
import com.example.Uber_Clone.model.Ride;
import com.example.Uber_Clone.repository.RideShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideShareRepository rideRepository;

    private String getLoggedInUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    public Ride createRide(CreateRideRequest request, String userId) {
        Ride ride = Ride.builder()
                .userId(userId)
                .pickupLocation(request.getPickupLocation())
                .dropLocation(request.getDropLocation())
                .status("REQUESTED")
                .createdAt(new Date())
                .build();

        return rideRepository.save(ride);
    }

    public List<Ride> getPendingRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    public Ride acceptRide(String rideId, String driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("REQUESTED")) {
            throw new BadRequestException("Ride not available for acceptance");
        }

        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");

        return rideRepository.save(ride);
    }

    public Ride completeRide(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new BadRequestException("Ride must be ACCEPTED to complete");
        }

        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }

    public List<Ride> getUserRides(String userId) {
        return rideRepository.findByUserId(userId);
    }
}
