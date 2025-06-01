package com.data.repository;

import com.data.connection.ConnectionDB;
import com.data.model.Bus;
import com.data.service.TypeBus;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusRepositoryImp implements BusRepository {
    @Override
    public List<Bus> getAllBuses() {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call get_all_buses()}")) {
            ResultSet rs = cstmt.executeQuery();
            List<Bus> buses = new ArrayList<>();
            while (rs.next()) {
                Bus bus = new Bus();
                bus.setId(rs.getInt("id"));
                bus.setLicensePlate(rs.getString("license_plate"));
                bus.setBusType(TypeBus.valueOf(rs.getString("bus_type").toLowerCase()));
                bus.setRowSeat(rs.getInt("rowSeats"));
                bus.setColSeat(rs.getInt("columnSeats"));
                bus.setTotalSeat(rs.getInt("totalSeats"));
                bus.setImage(rs.getString("image"));
                buses.add(bus);
            }
            return buses;
        } catch (Exception e) {
            System.err.println("Error getting buses: " + e.getMessage());
            throw new RuntimeException("Failed to get buses", e);
        }
    }

    @Override
    public Bus getBusById(int id) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("SELECT * FROM Bus WHERE id = ?")) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                Bus bus = new Bus();
                bus.setId(rs.getInt("id"));
                bus.setLicensePlate(rs.getString("license_plate"));
                bus.setBusType(TypeBus.valueOf(rs.getString("bus_type").toLowerCase()));
                bus.setRowSeat(rs.getInt("rowSeats"));
                bus.setColSeat(rs.getInt("columnSeats"));
                bus.setTotalSeat(rs.getInt("totalSeats"));
                bus.setImage(rs.getString("image"));
                return bus;
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error getting bus by ID: " + e.getMessage());
            throw new RuntimeException("Failed to get bus by ID", e);
        }
    }

    @Override
    public boolean addBus(Bus bus) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call sp_add_bus(?, ?, ?, ?, ?)}")) {
            cstmt.setString(1, bus.getLicensePlate());
            cstmt.setString(2, bus.getBusType().name().toLowerCase());
            cstmt.setInt(3, bus.getRowSeat());
            cstmt.setInt(4, bus.getColSeat());
            cstmt.setString(5, bus.getImage());
            int rowsAffected = cstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error adding bus: " + e.getMessage());
            throw new RuntimeException("Failed to add bus", e);
        }
    }

    @Override
    public boolean updateBus(Bus bus) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call sp_update_bus(?, ?, ?, ?, ?, ?)}")) {
            cstmt.setInt(1, bus.getId());
            cstmt.setString(2, bus.getLicensePlate());
            cstmt.setString(3, bus.getBusType().name().toLowerCase());
            cstmt.setInt(4, bus.getRowSeat());
            cstmt.setInt(5, bus.getColSeat());
            cstmt.setString(6, bus.getImage());
            int rowsAffected = cstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error updating bus: " + e.getMessage());
            throw new RuntimeException("Failed to update bus", e);
        }
    }

    @Override
    public boolean deleteBus(int id) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call sp_delete_bus(?)}")) {
            cstmt.setInt(1, id);
            int rowsAffected = cstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error deleting bus: " + e.getMessage());
            throw new RuntimeException("Failed to delete bus", e);
        }
    }
}