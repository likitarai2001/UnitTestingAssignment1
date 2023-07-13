package com.example.socialmediaapp.service;

import com.example.socialmediaapp.dto.UserProfileDto;
import com.example.socialmediaapp.entities.User;

import java.util.List;

public interface UserService {
    public User addUser(User user);

    public List<UserProfileDto> getAllUsers();

    public int updateUser(User user);

    public String deleteUser(int userId);
}
