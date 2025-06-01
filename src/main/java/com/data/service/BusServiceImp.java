package com.data.service;

import com.data.model.Bus;
import com.data.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImp implements BusService {
    private final BusRepository busRepository;

    @Autowired
    public BusServiceImp(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.getAllBuses();
    }

    @Override
    public boolean addBus(Bus bus) {
        return busRepository.addBus(bus);
    }

    @Override
    public Bus getBusById(int id) {
        return busRepository.getBusById(id);
    }

    @Override
    public boolean updateBus(Bus bus) {
        return busRepository.updateBus(bus);
    }

    @Override
    public boolean deleteBus(int id) {
        return busRepository.deleteBus(id);
    }
}