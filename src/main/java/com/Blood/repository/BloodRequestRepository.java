package com.Blood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Blood.entity.BloodRequest;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Integer> {
}
