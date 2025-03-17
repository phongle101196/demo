package com.example.test.service;

import com.example.test.dto.RegisterDTO;
import com.example.test.dto.UpdateUserDTO;
import com.example.test.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Page<UserDTO> getAllUsers(int page, int size);
    UserDTO getCurrentUser(String username);
    UserDTO updateCurrentUser(UpdateUserDTO updateUserDTO, String currentUsername);
    void deleteCurrentUser(String username);
}
