package com.example.test.service;

import com.example.test.dto.RegisterDTO;
import com.example.test.dto.UpdateUserDTO;
import com.example.test.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO register(RegisterDTO registerDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserByUsername(String username);

    UserDTO getUserById(int id);
    UserDTO updateUser(int id, UpdateUserDTO updateUserDTO);

    void deleteUser(int id);
}
