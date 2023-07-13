package com.example.socialmediaapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;
    private String bio;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String gender;

    @OneToOne(mappedBy = "profile")
    @JsonIgnore
    private User user;
}
