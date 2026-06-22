package com.fsx.mapper;

import com.fsx.dto.UserDto;
import com.fsx.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto mapToDto(User user){
        return new UserDto(user.getId(), user.getUsername(), user.getRole(),user.getAccountStatus());
    }
}
