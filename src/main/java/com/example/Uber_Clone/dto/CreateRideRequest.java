package com.example.Uber_Clone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRideRequest {

    @NotBlank(message = "Pickup is required")
    private String pickupLocation;

    @NotBlank(message = "Drop is required")
    private String dropLocation;
}
