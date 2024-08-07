package com.example.SpringBootLearning.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String name;
    LocalDate dateofbirth;
    int nationalidno;
    String email;
    String address;
    String drivinglicense;
    String role;
    float wallet;

    @Size(min = 8, message = "PASSWORD_INVALID")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*$", message = "PASSWORD_FORM_INVALID")
    String password;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "PHONENUMER_INVALID")
    String phoneno;
}
