package com.data.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.data.dto.BusDto;
import com.data.model.Bus;
import com.data.service.BusServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class BusController {

    private final BusServiceImp busServiceImp;
    private final Cloudinary cloudinary;

    @Autowired
    public BusController(BusServiceImp busServiceImp, Cloudinary cloudinary) {
        this.busServiceImp = busServiceImp;
        this.cloudinary = cloudinary;
    }

    @GetMapping("/bus")
    public String bus(Model model) {
        model.addAttribute("busList", busServiceImp.getAllBuses());
        return "list-bus";
    }

    @GetMapping("/addBus")
    public String addBus(Model model) {
        model.addAttribute("bus", new BusDto());
        return "add-bus";
    }

    @PostMapping("/addBus")
    public String addBusPost(@Valid @ModelAttribute("bus") BusDto busDto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Validation errors occurred");
            return "add-bus";
        }

        Bus bus = convertBusDtoToBus(busDto);
        try {
            if (busDto.getImage() != null && !busDto.getImage().isEmpty()) {
                Map<String, Object> uploadResult = cloudinary.uploader()
                        .upload(busDto.getImage().getBytes(), ObjectUtils.emptyMap());
                bus.setImage(uploadResult.get("url").toString());
            } else {
                bus.setImage("image/2.png");
            }
        } catch (IOException e) {
            model.addAttribute("message", "Lỗi khi tải ảnh lên Cloudinary: " + e.getMessage());
            return "add-bus";
        }

        try {
            boolean isAdded = busServiceImp.addBus(bus);
            if (isAdded) {
                return "redirect:/bus";
            } else {
                model.addAttribute("message", "Thêm xe thất bại");
                return "add-bus";
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Lỗi khi thêm xe: " + e.getMessage());
            return "add-bus";
        }
    }

    @GetMapping("/bus/edit/{id}")
    public String editBus(@PathVariable("id") int id, Model model) {
        Bus bus = busServiceImp.getBusById(id);
        if (bus == null) {
            model.addAttribute("message", "Xe không tồn tại");
            model.addAttribute("busList", busServiceImp.getAllBuses());
            return "list-bus";
        }
        BusDto busDto = convertBusToBusDto(bus);
        model.addAttribute("bus", busDto);
        return "edit-bus";
    }

    @PostMapping("/update")
    public String updateBusPost(@Valid @ModelAttribute("bus") BusDto busDto,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Validation errors occurred");
            return "edit-bus";
        }

        Bus bus = convertBusDtoToBus(busDto);
        try {
            if (busDto.getImage() != null && !busDto.getImage().isEmpty()) {
                Map<String, Object> uploadResult = cloudinary.uploader()
                        .upload(busDto.getImage().getBytes(), ObjectUtils.emptyMap());
                bus.setImage(uploadResult.get("url").toString());
            } else {
                bus.setImage(busServiceImp.getBusById(busDto.getId()).getImage());
            }
        } catch (IOException e) {
            model.addAttribute("message", "Lỗi khi tải ảnh lên Cloudinary: " + e.getMessage());
            return "edit-bus";
        }

        try {
            boolean isUpdated = busServiceImp.updateBus(bus);
            if (isUpdated) {
                return "redirect:/bus";
            } else {
                model.addAttribute("message", "Cập nhật xe thất bại");
                return "edit-bus";
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Lỗi khi cập nhật xe: " + e.getMessage());
            return "edit-bus";
        }
    }

    @GetMapping("/bus/delete/{id}")
    public String deleteBus(@PathVariable("id") int id, Model model) {
        try {
            boolean isDeleted = busServiceImp.deleteBus(id);
            if (isDeleted) {
                return "redirect:/bus";
            } else {
                model.addAttribute("message", "Xóa xe thất bại");
                model.addAttribute("busList", busServiceImp.getAllBuses());
                return "list-bus";
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Lỗi khi xóa xe: " + e.getMessage());
            model.addAttribute("busList", busServiceImp.getAllBuses());
            return "list-bus";
        }
    }

    private Bus convertBusDtoToBus(BusDto busDto) {
        Bus bus = new Bus();
        bus.setId(busDto.getId());
        bus.setLicensePlate(busDto.getLicensePlate());
        bus.setBusType(busDto.getBusType());
        bus.setRowSeat(busDto.getRowSeat());
        bus.setColSeat(busDto.getColSeat());
        return bus;
    }

    private BusDto convertBusToBusDto(Bus bus) {
        BusDto busDto = new BusDto();
        busDto.setId(bus.getId());
        busDto.setLicensePlate(bus.getLicensePlate());
        busDto.setBusType(bus.getBusType());
        busDto.setRowSeat(bus.getRowSeat());
        busDto.setColSeat(bus.getColSeat());
        return busDto;
    }
}