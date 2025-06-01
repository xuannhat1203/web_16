package com.data.model;

public class Seat {
    private int id;
    private String nameSeat;
    private double price;
    private int busId;
    private boolean status;

    public Seat() {
    }
    public Seat(String nameSeat, int busId, boolean status) {
        this.nameSeat = nameSeat;
        this.busId = busId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSeat() {
        return nameSeat;
    }

    public void setNameSeat(String nameSeat) {
        this.nameSeat = nameSeat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
