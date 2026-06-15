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
	
	private static final Logger logger=LoggerFactory.getLogger(AdminService.class);
	
	public List<Admin> openform() {
		return adminrepo.findAll();
	}

	public Admin saveAdmin(Admin admin) {
		logger.info("Saving blood details: {}",admin);
	  Admin saveAdmin=adminrepo.save(admin);
	  logger.info("Blood details saved sucessfully");
	  return saveAdmin;
		
	}

	 public Admin updateAdmin(int id, Admin admin) {
		 
		 logger.info("Updating the blood record with id:{}",id);

	        Admin oldAdmin = adminrepo.findById(id).orElse(null);

	        if (oldAdmin != null) {

	            oldAdmin.setBloodCenter(admin.getBloodCenter());
	            oldAdmin.setBloodGroup(admin.getBloodGroup());
	            oldAdmin.setLocation(admin.getLocation());
	            
	            Admin updateAdmin = adminrepo.save(oldAdmin);
	            logger.info("updated blood record succesfully:{}",updateAdmin);
	            return updateAdmin;
	        }
	        return null;
	 }
	 

	public String  deleteAdmin(int  id) {
		if(adminrepo.existsById(id)) {
		logger.info("Deleting the blood record with id:{}",id);
	  adminrepo.deleteById(id);
	  logger.info("Deleted the blood record Succesfully");
		return "deleted Succesfully";
	} else {

        logger.warn("Blood record not found with id: {}", id);

        return "Record Not Found";
    }

}
}
