package com.data.repository;

import com.data.connection.ConnectionDB;
import com.data.model.Trip;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripRepositoryImp implements TripRepository {

    @Override
    public List<Trip> getAllTrip(int page, int size) {
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call get_all_trips(?, ?)}")) {
            int offset = page * size;
            cstmt.setInt(1, offset);
            cstmt.setInt(2, size);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Trip trip = new Trip();
                trip.setId(rs.getInt("id"));
                trip.setDeparturePoint(rs.getString("departure_point"));
                trip.setDestination(rs.getString("destination"));
                trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
                trip.setBusId(rs.getInt("bus_id"));
                trip.setSeatsAvailable(rs.getInt("seats_available"));
                trip.setImage(rs.getString("image"));
                trips.add(trip);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách chuyến đi: " + e.getMessage(), e);
        }
        return trips;
    }

    @Override
    public List<Trip> searchTrips(String departure, String destination, int page, int size) {
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call search_trips_with_paging(?, ?, ?, ?)}")) {
            int offset = page * size;
            cstmt.setString(1, departure);
            cstmt.setString(2, destination);
            cstmt.setInt(3, offset);
            cstmt.setInt(4, size);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Trip trip = new Trip();
                trip.setId(rs.getInt("id"));
                trip.setDeparturePoint(rs.getString("departure_point"));
                trip.setDestination(rs.getString("destination"));
                trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
                trip.setBusId(rs.getInt("bus_id"));
                trip.setSeatsAvailable(rs.getInt("seats_available"));
                trip.setImage(rs.getString("image"));
                trips.add(trip);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm chuyến đi: " + e.getMessage(), e);
        }
        return trips;
    }

    @Override
    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call get_all_trips_no_paging()}")) {
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Trip trip = new Trip();
                trip.setId(rs.getInt("id"));
                trip.setDeparturePoint(rs.getString("departure_point"));
                trip.setDestination(rs.getString("destination"));
                trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
                trip.setBusId(rs.getInt("bus_id"));
                trip.setSeatsAvailable(rs.getInt("seats_available"));
                trip.setImage(rs.getString("image"));
                trips.add(trip);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy tất cả chuyến đi: " + e.getMessage(), e);
        }
        return trips;
    }

    @Override
    public long countAllTrips() {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call count_all_trips()}")) {
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm tổng số chuyến đi: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public long countTripsByPoint(String departure, String destination) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call count_trips_by_point(?, ?)}")) {
            cstmt.setString(1, departure);
            cstmt.setString(2, destination);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm chuyến đi: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public boolean addTrip(Trip trip) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call sp_add_trip(?, ?, ?, ?, ?, ?, ?)}")) {
            cstmt.setString(1, trip.getDeparturePoint());
            cstmt.setString(2, trip.getDestination());
            cstmt.setTimestamp(3, Timestamp.valueOf(trip.getDepartureTime()));
            cstmt.setTimestamp(4, Timestamp.valueOf(trip.getArrivalTime()));
            cstmt.setInt(5, trip.getBusId());
            cstmt.setInt(6, trip.getSeatsAvailable());
            cstmt.setString(7, trip.getImage());
            int rowsAffected = cstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm chuyến đi: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateTrip(Trip trip) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call sp_update_trip(?, ?, ?, ?, ?, ?, ?, ?)}")) {
            cstmt.setInt(1, trip.getId());
            cstmt.setString(2, trip.getDeparturePoint());
            cstmt.setString(3, trip.getDestination());
            cstmt.setTimestamp(4, Timestamp.valueOf(trip.getDepartureTime()));
            cstmt.setTimestamp(5, Timestamp.valueOf(trip.getArrivalTime()));
            cstmt.setInt(6, trip.getBusId());
            cstmt.setInt(7, trip.getSeatsAvailable());
            cstmt.setString(8, trip.getImage());
            int rowsAffected = cstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật chuyến đi: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteTrip(int id) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call sp_delete_trip(?)}")) {
            cstmt.setInt(1, id);
            int rowsAffected = cstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa chuyến đi: " + e.getMessage(), e);
        }
    }

    @Override
    public Trip getTripById(int id) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("SELECT * FROM trip WHERE id = ?")) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                Trip trip = new Trip();
                trip.setId(rs.getInt("id"));
                trip.setDeparturePoint(rs.getString("departure_point"));
                trip.setDestination(rs.getString("destination"));
                trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
                trip.setBusId(rs.getInt("bus_id"));
                trip.setSeatsAvailable(rs.getInt("seats_available"));
                trip.setImage(rs.getString("image"));
                return trip;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy chuyến đi theo ID: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Trip> searchTripsByDeparture(String departure, int page, int size) {
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call search_trips_by_departure(?, ?, ?)}")) {
            int offset = page * size;
            cstmt.setString(1, departure);
            cstmt.setInt(2, offset);
            cstmt.setInt(3, size);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                trips.add(mapTrip(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm theo điểm khởi hành: " + e.getMessage(), e);
        }
        return trips;
    }

    @Override
    public long countTripsByDeparture(String departure) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call count_trips_by_departure(?)}")) {
            cstmt.setString(1, departure);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) return rs.getLong("total");
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm theo điểm khởi hành: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public List<Trip> searchTripsByDestination(String destination, int page, int size) {
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call search_trips_by_destination(?, ?, ?)}")) {
            int offset = page * size;
            cstmt.setString(1, destination);
            cstmt.setInt(2, offset);
            cstmt.setInt(3, size);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                trips.add(mapTrip(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm theo điểm đến: " + e.getMessage(), e);
        }
        return trips;
    }

    @Override
    public long countTripsByDestination(String destination) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call count_trips_by_destination(?)}")) {
            cstmt.setString(1, destination);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) return rs.getLong("total");
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm theo điểm đến: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public List<Trip> searchTripsByDepartureOrDestination(String departure, String destination, int page, int size) {
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call search_trips_by_departure_or_destination(?, ?, ?, ?)}")) {
            int offset = page * size;
            cstmt.setString(1, departure);
            cstmt.setString(2, destination);
            cstmt.setInt(3, offset);
            cstmt.setInt(4, size);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                trips.add(mapTrip(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm theo điểm khởi hành hoặc điểm đến: " + e.getMessage(), e);
        }
        return trips;
    }

    @Override
    public long countTripsByDepartureOrDestination(String departure, String destination) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cstmt = conn.prepareCall("{call count_trips_by_departure_or_destination(?, ?)}")) {
            cstmt.setString(1, departure);
            cstmt.setString(2, destination);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) return rs.getLong("total");
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm theo điểm khởi hành hoặc điểm đến: " + e.getMessage(), e);
        }
        return 0;
    }

    // Hàm dùng chung để map dữ liệu từ ResultSet sang Trip
    private Trip mapTrip(ResultSet rs) throws SQLException {
        Trip trip = new Trip();
        trip.setId(rs.getInt("id"));
        trip.setDeparturePoint(rs.getString("departure_point"));
        trip.setDestination(rs.getString("destination"));
        trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        trip.setBusId(rs.getInt("bus_id"));
        trip.setSeatsAvailable(rs.getInt("seats_available"));
        trip.setImage(rs.getString("image"));
        return trip;
    }

}