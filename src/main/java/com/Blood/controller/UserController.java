package com.Blood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Blood.entity.BloodRequest;
import com.Blood.entity.User;
import com.Blood.service.AdminService;
import com.Blood.service.BloodRequestService;
import com.Blood.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userservice;

	@Autowired
	BloodRequestService bloodRequestService;

	@Autowired
	AdminService adminservice;

	@Autowired
	com.Blood.service.NotificationService notificationService;

	@GetMapping({"", "/"})
	public String userHome() {
		return "redirect:/user/dashboard";
	}

	@GetMapping("/form")
	public String openForm() {
		return "user";
	}

	@GetMapping("/dashboard")
	public String userDashboard() {
		return "user-dashboard";
	}

	@PostMapping("/save")
	public String saveUser(User user) {
		userservice.saveUser(user);
		return "redirect:/user/dashboard";
	}

	@GetMapping("/all")
	public String getAllUsers(Model model) {
		model.addAttribute("users", userservice.getAllUsers());
		model.addAttribute("isAdminView", false);
		return "user-list";
	}

	@PutMapping("update/{id}")
	@ResponseBody
	public org.springframework.http.ResponseEntity<String> updateUser(@PathVariable int id, User user, HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}
		userservice.updateUser(id, user);
		return org.springframework.http.ResponseEntity.ok("Updated Successfully");
	}

	@DeleteMapping("delete/{id}")
	@ResponseBody
	public org.springframework.http.ResponseEntity<String> deleteUser(@PathVariable int id, HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}
		userservice.deleteUser(id);
		return org.springframework.http.ResponseEntity.ok("Deleted Successfully");
	}

	// --- OTP Verification Simulation Endpoints ---

	@PostMapping("/send-otp")
	@ResponseBody
	public Map<String, String> sendOtp(
			@RequestParam String target,
			HttpSession session) {
		
		String otp = String.format("%04d", new Random().nextInt(10000));
		session.setAttribute("verificationOtp", otp);
		session.setAttribute("verificationTarget", target);

		// Dispatch via NotificationService (handles SMTP mail / Console fallback)
		notificationService.sendVerificationCode(target, otp);

		Map<String, String> response = new HashMap<>();
		response.put("status", "SUCCESS");
		return response;
	}

	@PostMapping("/verify-otp")
	@ResponseBody
	public Map<String, String> verifyOtp(
			@RequestParam String otp,
			@RequestParam String Name,
			@RequestParam String mobileNumber,
			@RequestParam String bloodgroup,
			@RequestParam String location,
			@RequestParam String email,
			HttpSession session) {
		
		Map<String, String> response = new HashMap<>();
		String savedOtp = (String) session.getAttribute("verificationOtp");

		if (savedOtp != null && savedOtp.equals(otp)) {
			// OTP is correct - save the user
			User user = new User(0, mobileNumber, Name, bloodgroup, location, email);
			userservice.saveUser(user);

			// Clear session
			session.removeAttribute("verificationOtp");
			session.removeAttribute("verificationTarget");

			response.put("status", "SUCCESS");
		} else {
			response.put("status", "FAILED");
			response.put("message", "Incorrect 4-digit code. Please check console logs.");
		}

		return response;
	}

	// --- Blood Request Endpoints ---

	@GetMapping("/request/form")
	public String bloodRequestForm() {
		return "blood-request-form";
	}

	@PostMapping("/request/save")
	public String saveBloodRequest(BloodRequest request) {
		bloodRequestService.saveRequest(request);
		return "redirect:/user/dashboard";
	}

	@PostMapping("/request/fulfill/{id}")
	@ResponseBody
	public String fulfillRequest(@PathVariable int id) {
		bloodRequestService.updateRequestStatus(id, "Fulfilled");
		return "SUCCESS";
	}

	@DeleteMapping("/request/delete/{id}")
	@ResponseBody
	public String deleteRequest(@PathVariable int id) {
		bloodRequestService.deleteRequest(id);
		return "SUCCESS";
	}

	// --- Unified Filtering & Search Portal ---

	@GetMapping("/find-blood")
	public String findBloodPortal() {
		return "find-blood";
	}

	@GetMapping("/search-results")
	@ResponseBody
	public Map<String, Object> searchResults(
			@RequestParam(required = false) String location,
			@RequestParam(required = false) String bloodgroup) {

		final String locFilter = (location != null) ? location.trim().toLowerCase() : "";
		final String bgFilter = (bloodgroup != null) ? bloodgroup.trim() : "";

		// Stream filter donors
		List<User> donors = userservice.getAllUsers().stream()
				.filter(u -> bgFilter.isEmpty() || bgFilter.equalsIgnoreCase(u.getBloodgroup()))
				.filter(u -> locFilter.isEmpty() || u.getLocation().toLowerCase().contains(locFilter))
				.collect(Collectors.toList());

		// Stream filter blood centers
		List<com.Blood.entity.Admin> centers = adminservice.openform().stream()
				.filter(a -> bgFilter.isEmpty() || bgFilter.equalsIgnoreCase(a.getBloodGroup()))
				.filter(a -> locFilter.isEmpty() || a.getLocation().toLowerCase().contains(locFilter))
				.collect(Collectors.toList());

		// Stream filter blood requests
		List<BloodRequest> requests = bloodRequestService.getAllRequests().stream()
				.filter(r -> bgFilter.isEmpty() || bgFilter.equalsIgnoreCase(r.getBloodGroup()))
				.filter(r -> locFilter.isEmpty() || r.getLocation().toLowerCase().contains(locFilter))
				.collect(Collectors.toList());

		Map<String, Object> results = new HashMap<>();
		results.put("donors", donors);
		results.put("centers", centers);
		results.put("requests", requests);
		return results;
	}
}
