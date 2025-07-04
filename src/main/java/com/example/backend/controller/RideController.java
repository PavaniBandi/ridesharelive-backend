package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.Ride;
import com.example.backend.entity.User;
import com.example.backend.service.RideService;
import com.example.backend.service.UserService;

@RestController
@RequestMapping("/rides")
@CrossOrigin(origins = {"http://localhost:5173"})
public class RideController {

    @Autowired
    private RideService rideService;
    @Autowired
    private UserDetails userDetails;
    @Autowired
    private UserService userService;

    @PostMapping("/book")
    public ResponseEntity<?> bookRide(@RequestBody Ride ride, @AuthenticationPrincipal UserDetails userDetails) {
        User rider = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        ride.setRiderId(rider.getId());
        ride.setStatus(Ride.Status.REQUESTED);
        Ride savedRide = rideService.bookRide(ride);

        return ResponseEntity.ok(savedRide);
    }

    //history - Driver/Rider GetMapping
    //UpdateStatus -> Post mapping
    //Get getRidesatus
    // Active Rides - List rides that are not complete Get
}
