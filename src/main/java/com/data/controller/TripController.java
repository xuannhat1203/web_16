package com.data.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.data.dto.TripDto;
import com.data.model.Trip;
import com.data.service.TripServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class TripController {
    private final Cloudinary cloudinary;
    public TripServiceImp tripServiceImp = new TripServiceImp();

    public TripController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    @GetMapping("/trips")
    public String getAllTrips(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "departure", required = false) String departure,
            @RequestParam(value = "destination", required = false) String destination,
            Model model) {

        int pageSize = 5;
        int pageIndex = page - 1;
        String dep = (departure != null) ? departure.trim() : "";
        String dest = (destination != null) ? destination.trim() : "";

        List<Trip> trips;
        int totalTrips;
        int totalPages;
        if (!dep.isEmpty() && dest.isEmpty()) {
            trips = tripServiceImp.searchTripsByDeparture(dep, pageIndex, pageSize);
            totalTrips = Math.toIntExact(tripServiceImp.countTripsByDeparture(dep));
        }
        else if (dep.isEmpty() && !dest.isEmpty()) {
            trips = tripServiceImp.searchTripsByDestination(dest, pageIndex, pageSize);
            totalTrips = Math.toIntExact(tripServiceImp.countTripsByDestination(dest));
        }
        else if (!dep.isEmpty() && !dest.isEmpty()) {
            trips = tripServiceImp.searchTripsByDepartureOrDestination(dep, dest, pageIndex, pageSize);
            totalTrips = Math.toIntExact(tripServiceImp.countTripsByDepartureOrDestination(dep, dest));
        }
        else {
            trips = tripServiceImp.getAllTrip(pageIndex, pageSize);
            totalTrips = Math.toIntExact(tripServiceImp.countAllTrips());
        }

        totalPages = (int) Math.ceil((double) totalTrips / pageSize);

        model.addAttribute("trips", trips);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("departure", dep);
        model.addAttribute("destination", dest);

        return "list-trip";
    }



    @GetMapping("/addTrip")
    public String addTrip(Model model) {
        model.addAttribute("trip", new TripDto());
        return "add-trip";
    }
    @PostMapping("/addTrip")
    public String addTripPost(@Valid @ModelAttribute("trip") TripDto tripDto,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Validation errors occurred");
            return "add-trip";
        }

        Trip trip = convertTripDtoToTrip(tripDto);
        try {
            if (tripDto.getImage() != null && !tripDto.getImage().isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(tripDto.getImage().getBytes(), ObjectUtils.emptyMap());
                trip.setImage(uploadResult.get("url").toString());
            } else {
                trip.setImage("image/2.png");
            }
        } catch (IOException e) {
            model.addAttribute("message", "Lỗi khi tải ảnh lên Cloudinary: " + e.getMessage());
            return "add-trip";
        }

        try {
            boolean isAdded = tripServiceImp.addTrip(trip);
            if (isAdded) {
                return "redirect:/trips";
            } else {
                model.addAttribute("message", "Thêm chuyến đi thất bại");
                return "add-trip";
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Lỗi khi thêm chuyến đi: " + e.getMessage());
            return "add-trip";
        }
    }
    @GetMapping("/trip/edit/{id}")
    public String editTrip(@PathVariable("id") int id, Model model) {
        Trip trip = tripServiceImp.getTripById(id);
        if (trip != null) {
            TripDto tripDto = convertTripToDto(trip);
            model.addAttribute("trip", tripDto);
            return "edit-trip";
        } else {
            model.addAttribute("message", "Không tìm thấy chuyến đi");
            return "redirect:/trips";
        }
    }
    @PostMapping("/trip/edit/{id}")
    public String editTripPost(@PathVariable("id") int id,
                               @Valid @ModelAttribute("trip") TripDto tripDto,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trip", tripDto);
            return "edit-trip";
        }

        Trip trip = convertTripDtoToTrip(tripDto);
        trip.setId(id);

        try {
            if (tripDto.getImage() != null && !tripDto.getImage().isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(tripDto.getImage().getBytes(), ObjectUtils.emptyMap());
                trip.setImage(uploadResult.get("url").toString());
            } else {
                Trip existingTrip = tripServiceImp.getTripById(id);
                trip.setImage(existingTrip != null ? existingTrip.getImage() : "image/2.png");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Lỗi khi tải ảnh lên Cloudinary: " + e.getMessage());
            return "edit-trip";
        }

        try {
            boolean isUpdated = tripServiceImp.updateTrip(trip);
            if (isUpdated) {
                return "redirect:/trips";
            } else {
                model.addAttribute("message", "Cập nhật chuyến đi thất bại");
                return "edit-trip";
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Lỗi khi cập nhật chuyến đi: " + e.getMessage());
            return "edit-trip";
        }
    }
    @GetMapping("/trip/delete/{id}")
    public String deleteTrip(@PathVariable("id") int id, Model model) {
        try {
            boolean isDeleted = tripServiceImp.deleteTrip(id);
            if (isDeleted) {
                return "redirect:/trips";
            } else {
                model.addAttribute("message", "Xóa chuyến đi thất bại");
                return "redirect:/trips";
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Lỗi khi xóa chuyến đi: " + e.getMessage());
            return "redirect:/trips";
        }
    }

    public Trip convertTripDtoToTrip(TripDto tripDto) {
        Trip trip = new Trip();
        trip.setId(tripDto.getId());
        trip.setDeparturePoint(tripDto.getDeparturePoint());
        trip.setDestination(tripDto.getDestination());
        trip.setDepartureTime(tripDto.getDepartureTime());
        trip.setArrivalTime(tripDto.getArrivalTime());
        trip.setBusId(tripDto.getBusId());
        trip.setSeatsAvailable(tripDto.getSeatsAvailable());
        return trip;
    }
    public TripDto convertTripToDto(Trip trip) {
        TripDto dto = new TripDto();
        dto.setId(trip.getId());
        dto.setDeparturePoint(trip.getDeparturePoint());
        dto.setDestination(trip.getDestination());
        dto.setDepartureTime(trip.getDepartureTime());
        dto.setArrivalTime(trip.getArrivalTime());
        dto.setBusId(trip.getBusId());
        dto.setSeatsAvailable(trip.getSeatsAvailable());
        return dto;
    }
}
