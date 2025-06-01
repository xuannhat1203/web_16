package com.data.dto;

import com.data.service.TypeBus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BusDto {
    private int id;

    @NotBlank(message = "Biển số xe không được để trống")
    private String licensePlate;

    @NotNull(message = "Loại xe không được để trống")
    private TypeBus busType;

    @NotNull(message = "Số hàng ghế không được để trống")
    @Min(value = 1, message = "Số hàng ghế phải lớn hơn 0")
    private Integer rowSeat;

    @NotNull(message = "Số cột ghế không được để trống")
    @Min(value = 1, message = "Số cột ghế phải lớn hơn 0")
    private Integer colSeat;

    private MultipartFile image;

    public BusDto() {
    }

    public BusDto(int id, String licensePlate, TypeBus busType, Integer rowSeat, Integer colSeat, MultipartFile image) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.busType = busType;
        this.rowSeat = rowSeat;
        this.colSeat = colSeat;
        this.image = image;
    }

    // Getters và setters
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

    public Integer getRowSeat() {
        return rowSeat;
    }

    public void setRowSeat(Integer rowSeat) {
        this.rowSeat = rowSeat;
    }

    public Integer getColSeat() {
        return colSeat;
    }

    public void setColSeat(Integer colSeat) {
        this.colSeat = colSeat;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}