package com.fsx.mapper;

import com.fsx.dto.PassengerReqDto;
import com.fsx.dto.PassengerRespDto;
import com.fsx.dto.PassengerUserProfileRespDto;
import com.fsx.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public Passenger mapToEntity(PassengerReqDto dto){
        Passenger passenger = new Passenger();
        passenger.setName(dto.name());
        passenger.setGender(dto.gender());
        passenger.setAge(dto.age());
        passenger.setEmail(dto.email());
        passenger.setPhoneNumber(dto.phoneNumber());
        return passenger;
    }

    public PassengerRespDto mapToDto(Passenger passenger){
        return new PassengerRespDto(passenger.getUser().getId(),
                passenger.getId(),
                passenger.getGender(),
                passenger.getName(),
                passenger.getPhoneNumber(),
                passenger.getUser().getAccountStatus(),
                passenger.getUser().getCreatedAt());
    }

    public PassengerUserProfileRespDto mapToProfileDto(Passenger passenger){
        return new PassengerUserProfileRespDto(passenger.getUser().getId(), passenger.getId(), passenger.getUser().getUsername(), passenger.getName(), passenger.getAge(), passenger.getGender(), passenger.getEmail(), passenger.getPhoneNumber(), passenger.getUser().getRole(), passenger.getUser().getCreatedAt(), passenger.getIdPath());
    }
}
