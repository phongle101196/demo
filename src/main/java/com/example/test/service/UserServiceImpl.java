package com.example.test.service;

import com.example.test.dto.RegisterDTO;
import com.example.test.dto.UpdateUserDTO;
import com.example.test.dto.UserDTO;
import com.example.test.entity.User;
import com.example.test.exception.NoDataFoundException;
import com.example.test.exception.UserNotFoundException;
import com.example.test.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<UserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        // ném ex khi không có data
        if (userPage.isEmpty()) {
            throw new NoDataFoundException("Không có dữ liệu người dùng!");
        }
        // map user sang userDTO
        Page<UserDTO> userDTOPage = userPage.map(user -> modelMapper.map(user, UserDTO.class));
        return userDTOPage;
    }

    @Override
    public UserDTO getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User không tồn tại!"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateCurrentUser(UpdateUserDTO updateUserDTO, String currentUsername) {
        // tìm user từ db
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("User không tồn tại!"));

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
    public void deleteCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User không tồn tại!"));
        userRepository.delete(user);
    }
}
