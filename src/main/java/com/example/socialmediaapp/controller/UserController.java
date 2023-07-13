package com.example.socialmediaapp.controller;

import com.example.socialmediaapp.dto.UserProfileDto;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers(){
        List<UserProfileDto> userList = userService.getAllUsers();
        if(userList.size() == 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no users");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @PostMapping("/")
    public ResponseEntity<String> addUser(@RequestBody User user){
        User new_user = userService.addUser(user);
        if(new_user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Object is empty");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    @PutMapping("/")
    public ResponseEntity<String> editUser(@RequestBody User user){
        int status = userService.updateUser(user);
        if(status == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + user.getUserId() + " doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId){
        String message = userService.deleteUser(userId);
        if(message.equals("Fail")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + userId + " doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

}
