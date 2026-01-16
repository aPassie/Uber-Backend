package com.example.Uber_Clone.controller;

import com.example.Uber_Clone.model.Ride;
import com.example.Uber_Clone.repository.UserRepository;
import com.example.Uber_Clone.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverRideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private UserRepository userRepository;

    private String getDriverId() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow()
                .getId();
    }

    // DRIVER - View Pending Requests
    @GetMapping("/rides/requests")
    public List<Ride> getPendingRides() {
        return rideService.getPendingRides();
    }

    // DRIVER - Accept Ride
    @PostMapping("/rides/{rideId}/accept")
    public Ride acceptRide(@PathVariable String rideId) {
        return rideService.acceptRide(rideId, getDriverId());
    }
}
