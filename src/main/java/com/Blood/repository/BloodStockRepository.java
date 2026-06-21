package com.Blood.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Blood.entity.Admin;
import com.Blood.entity.BloodStock;

@Repository
public interface BloodStockRepository extends JpaRepository<BloodStock, Integer> {
    List<BloodStock> findByBloodCenter(Admin bloodCenter);
    List<BloodStock> findByBloodCenterId(int centerId);
}
