package com.Blood.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Blood.entity.Admin;
import com.Blood.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminrepo;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public List<Admin> openform() {
        return adminrepo.findAll();
    }

    public Admin saveAdmin(Admin admin) {
        logger.info("Saving admin/center details: {}", admin);
        return adminrepo.save(admin);
    }

    public Admin findByUsername(String username) {
        return adminrepo.findByUsername(username);
    }

    public Admin login(String username, String password) {
        Admin admin = adminrepo.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            logger.info("Admin logged in successfully: {}", username);
            return admin;
        }
        logger.warn("Admin login failed for: {}", username);
        return null;
    }

    public Admin updateAdmin(int id, Admin admin) {
        logger.info("Updating the admin record with id: {}", id);
        Admin oldAdmin = adminrepo.findById(id).orElse(null);
        if (oldAdmin != null) {
            oldAdmin.setBloodCenter(admin.getBloodCenter());
            oldAdmin.setLocation(admin.getLocation());
            if (admin.getUsername() != null && !admin.getUsername().isEmpty()) {
                oldAdmin.setUsername(admin.getUsername());
            }
            if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
                oldAdmin.setPassword(admin.getPassword());
            }
            Admin updated = adminrepo.save(oldAdmin);
            logger.info("Updated admin record successfully: {}", updated);
            return updated;
        }
        return null;
    }

    public String deleteAdmin(int id) {
        if (adminrepo.existsById(id)) {
            logger.info("Deleting the admin record with id: {}", id);
            adminrepo.deleteById(id);
            logger.info("Deleted the admin record successfully");
            return "deleted Successfully";
        } else {
            logger.warn("Admin record not found with id: {}", id);
            return "Record Not Found";
        }
    }
}
