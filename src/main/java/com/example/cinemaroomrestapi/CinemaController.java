package com.example.cinemaroomrestapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

class Token {
    UUID token;

    public Token() {}

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Token(UUID token) {
        this.token = token;
    }
}

@RestController
public class CinemaController {

    private final Cinema cinema;

    public CinemaController() {
        cinema = Cinema.getAllSeats(9, 9);
    }

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody Seat seat) {
        if (seat.getRow() < 1 || seat.getRow() > 9 || seat.getColumn() < 1 || seat.getColumn() > 9) {

            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        for (int i = 0; i < cinema.getAvailable_seats().size(); i++) {
            Seat s = cinema.getAvailable_seats().get(i);
            if (s.equals(seat) && !cinema.getOrdered_seats().contains(seat)) {
                OrderedSeat orderedSeat = new OrderedSeat(s, UUID.randomUUID());
                cinema.getOrdered_seats().add(orderedSeat);
                cinema.getAvailable_seats().remove(i);
                    return new ResponseEntity<>(orderedSeat, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Token token) {
        List<OrderedSeat> orderedSeats = cinema.getOrdered_seats();
        for (OrderedSeat orderedSeat : orderedSeats) {
            if (orderedSeat.getToken().equals(token.getToken())) {
                orderedSeats.remove(orderedSeat);
                cinema.getAvailable_seats().add(orderedSeat.getTicket());
                return new ResponseEntity<>(Map.of("returned_ticket", orderedSeat.getTicket()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false) String password) {
        if (password != null && password.equals("super_secret")) {
            Map<String, Integer> stats = new HashMap<>();
            int total_income = 0;
            for (OrderedSeat orderedSeat : cinema.getOrdered_seats()) {
                total_income += orderedSeat.getTicket().getPrice();
            }
            stats.put("current_income", total_income);
            stats.put("number_of_available_seats", cinema.getAvailable_seats().size());
            stats.put("number_of_purchased_tickets", cinema.getOrdered_seats().size());

            return new ResponseEntity<>(stats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.valueOf(401));
        }
    }
}
