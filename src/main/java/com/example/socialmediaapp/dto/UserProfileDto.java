package com.example.socialmediaapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String bio;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String gender;
}
