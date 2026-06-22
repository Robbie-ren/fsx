package com.fsx.service;

import com.fsx.dto.BusOperatorReqDto;
import com.fsx.dto.CombinedStatBusOperatorDto;
import com.fsx.dto.OperatorEditProfileDto;

import com.fsx.enums.AccountStatus;
import com.fsx.enums.Role;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.mapper.BusOperatorMapper;
import com.fsx.model.*;
import com.fsx.repository.BusOperatorRepository;
import com.fsx.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusOperatorService {
    private final BusOperatorRepository busOperatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final BusOperatorMapper busOperatorMapper;
    private final UserRepository userRepository;

    @Value("${operator.password.temp}")
    private String busOperatorPasswordTemp;

    public BusOperator getById(int id) {
        return busOperatorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid bus operator id..."));
    }

    public BusOperator getByUserName(String loggedInUserName) {
        return busOperatorRepository.findByUserUsername(loggedInUserName);
    }

    public void addBusOperator(BusOperatorReqDto dto) {

        User user = new User();
        user.setUsername(dto.username());

        String encodedPassword = passwordEncoder.encode(busOperatorPasswordTemp);
        user.setPassword(encodedPassword);
        user.setRole(Role.BUS_OPERATOR);
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);

        BusOperator busOperator = busOperatorMapper.mapToEntity(dto);
        busOperator.setUser(user);
        busOperatorRepository.save(busOperator);

    }

    public CombinedStatBusOperatorDto getCombinedStats(String username) {

        List<Bus> buses = busOperatorRepository.getAllBusesByBusOperator(username);
        List<Schedule> activeSchedules = busOperatorRepository.getAllActiveSchedules(username);
        List<Booking> bookings = busOperatorRepository.getAllBookingsByOperator(username);
        double revenue = busOperatorRepository.getTotalRevenue(username);

        List<String> label = List.of("Total Buses", "Active Schedules", "Total Bookings", "Total Revenue");
        List<Long> count = List.of((long)buses.size(), (long)activeSchedules.size(), (long)bookings.size(), (long)revenue);
        return new CombinedStatBusOperatorDto(label, count);

    }

    public List<BusOperator> getOperators() {
        return busOperatorRepository.findAll();
    }

    public BusOperator editProfile(String username, OperatorEditProfileDto dto) {
        BusOperator operator  = busOperatorRepository.findByUserUsername(username);
        if (dto.companyName() != null) operator.setCompanyName(dto.companyName());
        if(dto.ownerName()!=null) operator.setOwnerName(dto.ownerName());
        if(dto.officeAddress()!=null) operator.setOfficeAddress(dto.officeAddress());
        if (dto.email() != null) operator.setEmail(dto.email());
        if (dto.phoneNumber() != null) operator.setPhoneNumber(dto.phoneNumber());


        return busOperatorRepository.save(operator);
    }
}
