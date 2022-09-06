package com.example.cinemaroomrestapi;

import java.util.ArrayList;
import java.util.UUID;

public class OrderedSeat {
    private UUID token;
    private Seat ticket;

    public OrderedSeat(Seat ticket,UUID token) {
        this.ticket = ticket;
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

}
