package com.data.model;

import com.data.service.TypeBus;

public class Bus {
    private int id;
    private String licensePlate;
    private TypeBus busType;
    private int rowSeat;
    private int colSeat;
    private int totalSeat;
    private String image;

    public Bus() {
    }

    public Bus(String licensePlate, TypeBus busType, int rowSeat, int colSeat, int totalSeat, String image) {
        this.licensePlate = licensePlate;
        this.busType = busType;
        this.rowSeat = rowSeat;
        this.colSeat = colSeat;
        this.totalSeat = totalSeat;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public TypeBus getBusType() {
        return busType;
    }

    public void setBusType(TypeBus busType) {
        this.busType = busType;
    }

    public int getRowSeat() {
        return rowSeat;
    }

    public void setRowSeat(int rowSeat) {
        this.rowSeat = rowSeat;
    }

    public int getColSeat() {
        return colSeat;
    }

    public void setColSeat(int colSeat) {
        this.colSeat = colSeat;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
