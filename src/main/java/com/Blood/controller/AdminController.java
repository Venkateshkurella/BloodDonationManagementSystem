package com.Blood.controller;

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

import com.Blood.entity.Admin;
import com.Blood.service.AdminService;
import com.Blood.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminservice;

	@Autowired
	UserService userservice;

	@GetMapping({"", "/"})
	public String adminHome() {
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/login")
	public String loginForm(HttpSession session) {
		if (session.getAttribute("adminUser") != null) {
			return "redirect:/admin/dashboard";
		}
		return "admin-login";
	}

	@PostMapping("/login")
	public String handleLogin(
			@RequestParam String username,
			@RequestParam String password,
			HttpSession session,
			Model model) {
		if (username != null && password != null 
				&& "admin".equalsIgnoreCase(username.trim()) 
				&& "admin123".equals(password.trim())) {
			session.setAttribute("adminUser", "admin");
			return "redirect:/admin/dashboard";
		}
		model.addAttribute("error", "Invalid username or password");
		return "admin-login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("adminUser");
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/form")
	public String openform(HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return "redirect:/admin/login";
		}
		return "admin";
	}

	@GetMapping("/dashboard")
	public String adminDashboard(HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return "redirect:/admin/login";
		}
		return "admin-dashboard";
	}
	
	@PostMapping("/save")
	public String saveAdmin(Admin admin, HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return "redirect:/admin/login";
		}
		adminservice.saveAdmin(admin);
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/all")
	public String getAllAdmins(Model model, HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("admins", adminservice.openform());
		return "admin-list";
	}

	@PutMapping("/update/{id}")
	@org.springframework.web.bind.annotation.ResponseBody
	public String updateAdmin(@PathVariable int id, Admin admin, HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return "Unauthorized";
		}
		adminservice.updateAdmin(id, admin);
		return "Updated Successfully";
	}

	@DeleteMapping("/delete/{id}")
	@org.springframework.web.bind.annotation.ResponseBody
	public String deleteAdmin(@PathVariable int id, HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return "Unauthorized";
		}
		adminservice.deleteAdmin(id);
		return "Deleted Successfully";
	}

	@GetMapping("/donors")
	public String getAllDonors(Model model, HttpSession session) {
		if (session.getAttribute("adminUser") == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("users", userservice.getAllUsers());
		model.addAttribute("isAdminView", true);
		return "user-list";
	}
}
