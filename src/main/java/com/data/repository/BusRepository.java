package com.data.repository;

import com.data.model.Bus;

import java.util.List;

public interface BusRepository {
    List<Bus> getAllBuses();
    boolean addBus(Bus bus);
    Bus getBusById(int id);
    boolean updateBus(Bus bus);
    boolean deleteBus(int id);
}