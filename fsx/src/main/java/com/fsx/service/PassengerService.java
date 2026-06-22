package com.fsx.service;

import com.fsx.dto.PassengerEditProfileDto;
import com.fsx.dto.PassengerReqDto;
import com.fsx.dto.PassengerRespDto;
import com.fsx.enums.AccountStatus;
import com.fsx.enums.PassengerStatus;
import com.fsx.enums.Role;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.mapper.PassengerMapper;
import com.fsx.model.Passenger;
import com.fsx.model.User;
import com.fsx.repository.PassengerRepository;
import com.fsx.utility.FileUtility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
public class PassengerService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final PassengerMapper passengerMapper;
    private final PassengerRepository passengerRepository;
    private static final String UPLOAD_LOC = "C:/Users/ashok/OneDrive/Desktop/FSD_Training_FastX/FastXReact/public/files";

    public Passenger post(PassengerReqDto dto) {
        User user = new User();
        user.setUsername(dto.username());

        String encodedPassword = passwordEncoder.encode(dto.password());
        user.setRole(Role.PASSENGER);
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setPassword(encodedPassword);
        userService.save(user);

        Passenger passenger = passengerMapper.mapToEntity(dto);
        passenger.setUser(user);
        return passengerRepository.save(passenger);
    }


    public Passenger getByUserName(String userName) {
        return passengerRepository.findByUserUsername(userName);
    }

    public List<PassengerRespDto> getAllPassengers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Passenger> list = passengerRepository.findAll(pageable).getContent();
        return list.stream().map(passengerMapper::mapToDto).toList();
    }

    public PassengerRespDto getById(int passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new ResourceNotFoundException("Invalid id..."));
        return passengerMapper.mapToDto(passenger);
    }

    public void upload(int id, MultipartFile file) throws IOException {

        // Fetch Officer by given username
        Passenger passenger = findById(id);
        // this officer coming from DB does not have the ID path attached to it

        FileUtility.validateFile(file);

        // Original filename
        String fileName = file.getOriginalFilename();

        // i am creating the path where i will upload the file: destination
        Path uploadPath =  Paths.get(UPLOAD_LOC);
        // Attach the file name to the upload path
        Path destinationPath =  uploadPath.resolve(fileName);

        // Copy the original file (Multipart) on to destination upload path
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Save the file name in db
        passenger.setIdPath(fileName);
        passenger.setStatus(PassengerStatus.VERIFIED);

        passengerRepository.save(passenger);
    }

    private Passenger findById(int id) {
        return passengerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Passenger not found"));
    }

    public List<Passenger> getPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger editProfile(String username, PassengerEditProfileDto dto) {
        Passenger passenger = passengerRepository.findByUserUsername(username);
        if (dto.name() != null) passenger.setName(dto.name());
        if(dto.age()!=null) passenger.setAge(dto.age());
        if(dto.gender()!=null)passenger.setGender(dto.gender());
        if (dto.email() != null) passenger.setEmail(dto.email());
        if (dto.phoneNumber() != null) passenger.setPhoneNumber(dto.phoneNumber());


        return passengerRepository.save(passenger);
    }
}
