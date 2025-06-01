//package com.data.service;
//
//import com.data.dto.TripDto;
//import com.data.repository.ArrivalAfterDeparture;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class ArrivalAfterDepartureValidator implements ConstraintValidator<ArrivalAfterDeparture, TripDto> {
//
//    @Override
//    public boolean isValid(TripDto tripDto, ConstraintValidatorContext context) {
//        if (tripDto == null || tripDto.getDepartureTime() == null || tripDto.getArrivalTime() == null) {
//            return true;
//        }
//
//        return tripDto.getArrivalTime().isAfter(tripDto.getDepartureTime());
//    }
//}
