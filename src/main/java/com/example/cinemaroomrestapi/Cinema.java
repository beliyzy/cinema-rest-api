package com.example.cinemaroomrestapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private int total_rows;
    private int total_columns;
    private List<Seat> available_seats;
    @JsonIgnore
    private List<OrderedSeat> ordered_seats;

    public Cinema() {}

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(List<Seat> available_seats) {
        this.available_seats = available_seats;
    }

    public List<OrderedSeat> getOrdered_seats() {
        return ordered_seats;
    }

    public void setOrdered_seats(List<OrderedSeat> ordered_seats) {
        this.ordered_seats = ordered_seats;
    }

    public Cinema(int total_columns, int total_rows, List<Seat> available_seats) {
        this.total_columns = total_columns;
        this.total_rows = total_rows;
        this.available_seats = available_seats;
        this.ordered_seats = new ArrayList<>();
    }
    public static Cinema getAllSeats(int rows, int columns) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                seats.add(new Seat(row, column, (row <= 4 ? 10 : 8)));
            }
        }
        return new Cinema(rows, columns, seats);
    }
}
