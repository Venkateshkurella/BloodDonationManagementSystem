package com.Blood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Blood.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}