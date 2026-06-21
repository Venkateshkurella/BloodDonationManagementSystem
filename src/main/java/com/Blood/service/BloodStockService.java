package com.Blood.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Blood.entity.Admin;
import com.Blood.entity.BloodStock;
import com.Blood.repository.BloodStockRepository;

@Service
public class BloodStockService {

    @Autowired
    private BloodStockRepository stockRepo;

    private static final Logger logger = LoggerFactory.getLogger(BloodStockService.class);

    public List<BloodStock> getAllStocks() {
        return stockRepo.findAll();
    }

    public List<BloodStock> getStocksByCenter(Admin bloodCenter) {
        return stockRepo.findByBloodCenter(bloodCenter);
    }

    public BloodStock saveStock(BloodStock stock) {
        logger.info("Saving blood stock details: {}", stock);
        return stockRepo.save(stock);
    }

    public BloodStock updateStock(int id, BloodStock stock) {
        logger.info("Updating blood stock with id: {}", id);
        BloodStock oldStock = stockRepo.findById(id).orElse(null);
        if (oldStock != null) {
            oldStock.setBloodGroup(stock.getBloodGroup());
            oldStock.setQuantity(stock.getQuantity());
            return stockRepo.save(oldStock);
        }
        return null;
    }

    public String deleteStock(int id) {
        if (stockRepo.existsById(id)) {
            logger.info("Deleting blood stock with id: {}", id);
            stockRepo.deleteById(id);
            return "deleted Successfully";
        }
        return "Stock Not Found";
    }
}
