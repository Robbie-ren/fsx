package com.fsx.service;

import com.fsx.dto.*;
import com.fsx.enums.AccountStatus;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.mapper.BusOperatorMapper;
import com.fsx.mapper.PassengerMapper;
import com.fsx.mapper.UserMapper;
import com.fsx.model.BusOperator;
import com.fsx.model.Passenger;
import com.fsx.model.User;
import com.fsx.repository.BusOperatorRepository;
import com.fsx.repository.PassengerRepository;
import com.fsx.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final BusOperatorRepository busOperatorRepository;
    private final PassengerMapper passengerMapper;
    private final BusOperatorMapper busOperatorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public PassengerUserProfileRespDto getPassengerUserProfile(String username) {
        Passenger passenger = passengerRepository.findByUserUsername(username);
        return passengerMapper.mapToProfileDto(passenger);
    }

    public BusOperatorUserProfileRespDto getBusOperatorUserProfile(String username) {
        BusOperator busOperator = busOperatorRepository.findByUserUsername(username);
        return busOperatorMapper.mapToDto(busOperator);
    }

    public void changePassword(String username, ChangePasswordDto dto) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check current password
        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Check new password and confirmation
        if (!dto.newPassword().equals(dto.confirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Prevent same password
        if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
            throw new RuntimeException("New password cannot be same as old password");
        }

        // Save encrypted password
        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        userRepository.save(user);
    }

    public PaginatedUserResponseDto getUsers(int page, int size, AccountStatus status, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if (keyword == null || keyword.isBlank()) {
            users = userRepository.findByAccountStatus(status, pageable);
        }
        else {
            users = userRepository.searchUsers(status, keyword, pageable);
        }
        List<UserDto> list = users.getContent().stream().map(UserMapper::mapToDto).toList();
        return new PaginatedUserResponseDto(users.getTotalElements(),users.getTotalPages(),list);
    }

    public void disable(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.DISABLED);
        userRepository.save(user);
    }

    public void enable(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);
    }
}
