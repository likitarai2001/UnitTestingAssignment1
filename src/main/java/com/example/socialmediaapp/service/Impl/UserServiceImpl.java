package com.example.socialmediaapp.service.Impl;

import com.example.socialmediaapp.dto.UserProfileDto;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.exception.CustomException;
import com.example.socialmediaapp.exception.NotFound;
import com.example.socialmediaapp.mapstruct.MapStructMapper;
import com.example.socialmediaapp.repository.UserRepository;
import com.example.socialmediaapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;

    public UserServiceImpl(UserRepository userRepository, MapStructMapper mapStructMapper) {
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {CustomException.class})
    public User addUser(User user) {
        User savedUser = userRepository.save(user);
        if(user.getFirstName() == null){
            throw new CustomException("First Name is required");
        }
        log.info("User added successfully");
        return savedUser;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDto> getAllUsers() {
        List<UserProfileDto> userProfileDtoList = userRepository.findAll()
                .stream()
                .map(mapStructMapper::userToUserProfileDto)
                .collect(Collectors.toList());
        log.info("User loaded successfully");
        return userProfileDtoList;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {NotFound.class})
    public int updateUser(User user) {
        User fetchedUser = userRepository.findById(user.getUserId()).orElse(null);
        if(fetchedUser == null){
            throw new NotFound("User Not Found");
        }
        int status = userRepository.updateUser(user.getFirstName(), user.getUserId());
        log.info("User updated successfully");
        return status;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {NotFound.class})
    public String deleteUser(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            return "Fail";
        }
        userRepository.deleteById(userId);
        log.info("User deleted successfully");
        return "Success";
    }
}
