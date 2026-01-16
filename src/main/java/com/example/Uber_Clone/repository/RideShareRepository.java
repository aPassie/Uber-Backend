package com.example.Uber_Clone.repository;

import com.example.Uber_Clone.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideShareRepository extends MongoRepository<Ride,String> {
    List<Ride> findByStatus(String status);

    List<Ride> findByUserId(String userId);
}
