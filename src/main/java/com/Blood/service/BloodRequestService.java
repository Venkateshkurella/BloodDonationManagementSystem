package com.Blood.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blood.entity.BloodRequest;
import com.Blood.repository.BloodRequestRepository;

@Service
public class BloodRequestService {

    @Autowired
    BloodRequestRepository requestRepo;

    private static final Logger logger = LoggerFactory.getLogger(BloodRequestService.class);

    public List<BloodRequest> getAllRequests() {
        return requestRepo.findAll();
    }

    public BloodRequest saveRequest(BloodRequest request) {
        logger.info("Saving blood request details: {}", request);
        if (request.getStatus() == null || request.getStatus().isEmpty()) {
            request.setStatus("Active");
        }
        BloodRequest saved = requestRepo.save(request);
        logger.info("Blood request saved successfully with id: {}", saved.getId());
        return saved;
    }

    public BloodRequest updateRequestStatus(int id, String status) {
        logger.info("Updating request status for id: {} to {}", id, status);
        BloodRequest oldReq = requestRepo.findById(id).orElse(null);
        if (oldReq != null) {
            oldReq.setStatus(status);
            return requestRepo.save(oldReq);
        }
        logger.warn("Request not found for id: {}", id);
        return null;
    }

    public void deleteRequest(int id) {
        logger.info("Deleting blood request with id: {}", id);
        if (requestRepo.existsById(id)) {
            requestRepo.deleteById(id);
            logger.info("Blood request deleted successfully");
        } else {
            logger.warn("Request not found with id: {}", id);
        }
    }
}
