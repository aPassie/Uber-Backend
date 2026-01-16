package com.example.Uber_Clone.controller;

import jakarta.validation.Valid;
import com.example.Uber_Clone.dto.CreateRideRequest;
import com.example.Uber_Clone.model.Ride;
import com.example.Uber_Clone.repository.UserRepository;
import com.example.Uber_Clone.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private UserRepository userRepository;

    private String getLoggedInUserId() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow()
                .getId();
    }

    // USER - Create Ride
    @PostMapping("/rides")
    public Ride createRide(@Valid @RequestBody CreateRideRequest request) {
        return rideService.createRide(request, getLoggedInUserId());
    }

    // USER/DRIVER - Complete Ride
    @PostMapping("/rides/{rideId}/complete")
    public Ride completeRide(@PathVariable String rideId) {
        return rideService.completeRide(rideId);
    }

    // USER - View Own Rides
    @GetMapping("/user/rides")
    public List<Ride> getMyRides() {
        return rideService.getUserRides(getLoggedInUserId());
    }
}
