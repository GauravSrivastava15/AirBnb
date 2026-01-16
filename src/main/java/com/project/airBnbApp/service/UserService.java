package com.project.airBnbApp.service;

import com.project.airBnbApp.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User getUserById(Long id);
}
