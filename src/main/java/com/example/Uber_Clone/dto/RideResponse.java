package com.example.Uber_Clone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class RideResponse {

    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private String status;
    private Date createdAt;
}
