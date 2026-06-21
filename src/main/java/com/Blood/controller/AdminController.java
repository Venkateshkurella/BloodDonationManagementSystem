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
import org.springframework.web.bind.annotation.ResponseBody;

import com.Blood.entity.Admin;
import com.Blood.entity.BloodStock;
import com.Blood.service.AdminService;
import com.Blood.service.BloodStockService;
import com.Blood.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminservice;

	@Autowired
	UserService userservice;

	@Autowired
	com.Blood.service.RealtimeService realtimeService;

	@Autowired
	BloodStockService bloodStockService;

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
		Admin center = adminservice.login(username, password);
		if (center != null) {
			session.setAttribute("adminUser", center);
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

	@GetMapping("/register")
	public String registerForm(HttpSession session) {
		if (session.getAttribute("adminUser") != null) {
			return "redirect:/admin/dashboard";
		}
		return "admin-register";
	}

	@PostMapping("/register")
	public String handleRegister(
			@RequestParam String name,
			@RequestParam String location,
			@RequestParam String username,
			@RequestParam String password,
			Model model) {
		if (adminservice.findByUsername(username) != null) {
			model.addAttribute("error", "Username already exists! Choose another one.");
			return "admin-register";
		}
		Admin newCenter = new Admin(0, name, location, username, password);
		adminservice.saveAdmin(newCenter);
		return "redirect:/admin/login";
	}

	@GetMapping("/form")
	public String openform(HttpSession session, Model model) {
		Admin center = (Admin) session.getAttribute("adminUser");
		if (center == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("centerName", center.getBloodCenter());
		model.addAttribute("centerLocation", center.getLocation());
		return "admin";
	}

	@GetMapping("/dashboard")
	public String adminDashboard(HttpSession session, Model model) {
		Admin center = (Admin) session.getAttribute("adminUser");
		if (center == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("centerName", center.getBloodCenter());
		model.addAttribute("centerLocation", center.getLocation());
		return "admin-dashboard";
	}
	
	@PostMapping("/save")
	public String saveAdmin(BloodStock stock, HttpSession session) {
		Admin center = (Admin) session.getAttribute("adminUser");
		if (center == null) {
			return "redirect:/admin/login";
		}
		stock.setBloodCenter(center);
		BloodStock saved = bloodStockService.saveStock(stock);
		try {
			java.util.Map<String, Object> eventData = new java.util.HashMap<>();
			eventData.put("id", saved.getId());
			eventData.put("bloodCenter", center.getBloodCenter());
			eventData.put("bloodGroup", saved.getBloodGroup());
			eventData.put("location", center.getLocation());
			eventData.put("quantity", saved.getQuantity());
			realtimeService.broadcast("stock-update", eventData);
		} catch (Exception e) {
			System.err.println("Failed to broadcast stock-update: " + e.getMessage());
		}
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/all")
	public String getAllAdmins(Model model, HttpSession session) {
		Admin center = (Admin) session.getAttribute("adminUser");
		if (center == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("stocks", bloodStockService.getStocksByCenter(center));
		model.addAttribute("centerName", center.getBloodCenter());
		return "admin-list";
	}

	@PutMapping("/update/{id}")
	@ResponseBody
	public String updateAdmin(@PathVariable int id, BloodStock stock, HttpSession session) {
		Admin center = (Admin) session.getAttribute("adminUser");
		if (center == null) {
			return "Unauthorized";
		}
		bloodStockService.updateStock(id, stock);
		return "Updated Successfully";
	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteAdmin(@PathVariable int id, HttpSession session) {
		Admin center = (Admin) session.getAttribute("adminUser");
		if (center == null) {
			return "Unauthorized";
		}
		bloodStockService.deleteStock(id);
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
