package com.example.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "Username không được để trống!")
    @Pattern(regexp = "^(?!\\.)(?!.*\\.\\.)(?:[A-Z]|[a-z0-9])[a-z0-9.]{7,49}(?<!\\.)$", message = "Username không hợp lệ!")
    @Size(min = 6, max = 50, message = "Username phải từ 6-50 ký tự!")
    private String username;

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 8, max = 30, message = "Mật khẩu phải từ 8-30 ký tự!")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$", message = "Mật khẩu không hợp lệ!")
    private String password;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "(\\+84|0)[0-9]{9,10}", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Họ không được để trống")
    @Pattern(regexp = "^(?![ '\\-])(?!.*[ '\\-]{2})[\\p{L} '\\-]{2,50}(?<![ '\\-])$", message = "Họ không hợp lệ!")
    private String firstName;

    @NotBlank(message = "Tên không được để trống")
    @Pattern(regexp = "^(?![ '\\-])(?!.*[ '\\-]{2})[\\p{L} '\\-]{2,50}(?<![ '\\-])$", message = "Tên không hợp lệ!")
    private String lastName;
}
