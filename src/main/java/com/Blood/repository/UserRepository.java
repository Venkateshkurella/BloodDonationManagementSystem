package com.Blood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Blood.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}