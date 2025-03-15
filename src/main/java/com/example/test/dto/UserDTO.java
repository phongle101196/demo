package com.example.test.dto;

import com.example.test.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private LocalDateTime createDate;
    private User.Role role;
}
