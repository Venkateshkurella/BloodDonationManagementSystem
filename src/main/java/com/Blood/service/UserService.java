package com.Blood.service;

import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blood.entity.User;
import com.Blood.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	UserRepository userrepo;
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

	public User saveUser(User user) {
		logger.info("Saving User details:{}",user);
		User saveUser= userrepo.save(user);
		logger.info("Saved User details Succesfully");
		return saveUser;
		
	}

	public List<User> getAllUsers() {
	
		return userrepo.findAll();
	}

	public User updateUser(int id, User user) {

	    logger.info("Updating user with id: {}", id);

	    User oldUser = userrepo.findById(id).orElse(null);

	    if (oldUser != null) {

	        oldUser.setName(user.getName());
	        oldUser.setMobileNumber(user.getMobileNumber());
	        oldUser.setbloodgroup(user.getBloodgroup());
	        oldUser.setLocation(user.getLocation());

	        User updatedUser = userrepo.save(oldUser);

	        logger.info("User updated successfully");

	        return updatedUser;
	    }

	    logger.warn("User not found with id: {}", id);

	    return null;
	}

	public String deleteUser(int id) {

	    if (userrepo.existsById(id)) {

	        logger.info("Deleting user with id: {}", id);

	        userrepo.deleteById(id);

	        logger.info("User deleted successfully");

	        return "Deleted Successfully";
	    }

	    logger.warn("User not found with id: {}", id);

	    return "User Not Found";
	}

}
