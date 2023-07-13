package com.example.socialmediaapp.repository;

import com.example.socialmediaapp.entities.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Query("UPDATE User u SET u.firstName = ?1 WHERE u.id = ?2")
    public int updateUser(String firstName, int id);
}
