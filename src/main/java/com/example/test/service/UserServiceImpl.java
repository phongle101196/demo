package com.example.test.service;

import com.example.test.dto.RegisterDTO;
import com.example.test.dto.UpdateUserDTO;
import com.example.test.dto.UserDTO;
import com.example.test.entity.User;
import com.example.test.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO register(RegisterDTO registerDTO) {
        // check username và email xem có chưa
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // create user
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setPhone(registerDTO.getPhone());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setCreateDate(LocalDateTime.now());
        user.setAvatarUrl("avatar.png");
        user.setRole(User.Role.USER);

        // lưu user
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng với username: "+username));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " +id));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(int id, UpdateUserDTO updateUserDTO) {
        // check người dùng đang cần update
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " +id));

        // update
        if (updateUserDTO.getPhone() != null) {
            user.setPhone(updateUserDTO.getPhone());
        }
        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }
        if (updateUserDTO.getAvatarUrl() != null) {
            user.setAvatarUrl(updateUserDTO.getAvatarUrl());
        }

        // lưu
        User updateUser = userRepository.save(user);
        return modelMapper.map(updateUser, UserDTO.class);
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy người dùng với id: " +id);
        }
        userRepository.deleteById(id);
    }


}
