package com.project.airBnbApp.service;

import com.project.airBnbApp.dto.ProfileUpdateRequestDto;
import com.project.airBnbApp.dto.UserDto;
import com.project.airBnbApp.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User getUserById(Long id);

    void updateUser(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
