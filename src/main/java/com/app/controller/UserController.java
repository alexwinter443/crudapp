package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.business.SecurityServiceInterface;
import com.app.model.SearchUsersModel;
import com.app.model.UserEntity;


/*
 * Alex Vergara
 * 5/2/2022
 */

@Controller
@RequestMapping("/")
public class UserController {
	
	// DEPENDENCY INJECTION
	@Autowired
	SecurityServiceInterface securityService;
	
	/** 
	 * Render the homepage
	 * @param model
	 * @return Name of view to be rendered
	 */
	// home route
	@GetMapping("/")
	public String homePage(Model model) {
		// Display Login Form View
		model.addAttribute("title", "Vacation Site");
		model.addAttribute("userModel", new UserEntity());
		
		int users=1;
		model.addAttribute("users", users);
		return "homePage";
	}
	
	/** 
	 * Handle the submission of the edit user form
	 * @param user
	 * @param bindingResult
	 * @param model
	 * @return Name of view to be rendered
	 * @throws Exception 
	 */
	// update path
	@RequestMapping(value = "/update", method = RequestMethod.POST, params = "update")
	public String updateUser(@Valid UserEntity user, BindingResult bindingResult, Model model) throws Exception {
		// update the existing user
		// business service
		securityService.updateOne(user.getId(), user);
		// gets all users 
		List<UserEntity> users = securityService.getAllUsers();
		System.out.println("Just updated user with ID" + user.getId());
		// display all users
		model.addAttribute("users", users); 
		return "usersImages";
	}
	
	
	
	/** 
	 * Render a search form to the user
	 * @param model
	 * @return Name of view to be rendered
	 */
	// controller for search form
	@GetMapping("/searchForm") 
	public String displaySearchForm(Model model){
		// Display Search Form View
		model.addAttribute("title", "Search Users");
		model.addAttribute("searchUsersModel", new SearchUsersModel());
		return "userSearchForm";
	}

	
	/** 
	 * Render all the orders to the user, based on search criteria
	 * @param searchModel
	 * @param bindingResult
	 * @param model
	 * @return Name of view to be rendered
	 */
	// used in the form action to display search results
	@PostMapping("/userSearchResults") 
	public String showAllOrders(@Valid SearchUsersModel searchModel, BindingResult bindingResult, Model model) {
		System.out.println("Performing search results for " + searchModel.getSearchTerm());
		// Check for validation errors        
		if (bindingResult.hasErrors()){        
			model.addAttribute("title", "Search Users");            
			return "userSearchForm";        
		}
		// search for the products
		List<UserEntity> users = securityService.searchUsers(searchModel.getSearchTerm());   
		model.addAttribute("title", "Search for Users");
		model.addAttribute("searchModel", searchModel);
		model.addAttribute("users", users);
		// display products that were found
		return "usersImages";
	}
	
	
	
	/** 
	 * Handle the submission of the delete user form
	 * @param user
	 * @param bindingResult
	 * @param model
	 * @return Name of view to be rendered
	 * @throws Exception 
	 */
	// to delete with controller from admin
	@RequestMapping(value = "/update", method = RequestMethod.POST, params = "delete")
	public String deleteOrder(@Valid UserEntity user, BindingResult bindingResult, Model model) throws Exception {
		// delete the user
		securityService.deleteOne(user.getId());
		// gets all users
		List<UserEntity> users = securityService.getAllUsers();
		System.out.println("In Controller: user id is " + user.getId() + " username " + user.getUsername());
		// get updated list of all the users
		// display all users
		model.addAttribute("users", users);
		return "usersImages";
	}
	
	
	/** 
	 * Show a single product to the user
	 * @param productModel
	 * @param model
	 * @return Name of view to be rendered
	 */
	// showing one product
	@GetMapping("/showOne") 
	public String displaySingleProduct(UserEntity userModel, Model model){
		// Display edit form
		model.addAttribute("title", "One Product");
		model.addAttribute("userModel", userModel);
		return "singleUserForm";
	}
	
	
	
	/** 
	 * Render all orders to the user (no search criteria)
	 * @param model
	 * @return Name of view to be rendered
	 * @throws Exception 
	 */
	// controller mapping for all products
	@GetMapping("/all") 
	public String showAllOrders( Model model) throws Exception {  
		List<UserEntity> users = securityService.getAllUsers();
		model.addAttribute("title", "Show All Users");
		model.addAttribute("users", users);
		// show all users
		return "usersImages";
	}
	
	
	/** 
	 * Render the Login form to the user
	 * @param model
	 * @return Name of view to be rendered
	 */
	// login route takes us to login page
	@GetMapping("/login")
	public String display(Model model) {
		// Display Login Form View
		System.out.println("Showing login page in user controller");
		model.addAttribute("userModel", new UserEntity());
		return "login";
	}
	
	
	/** 
	 * Login the user through a database transaction
	 * @param loginModel
	 * @param model
	 * @return Name of view to be rendered
	 */
	// login form post method
	@PostMapping("/doLogin")
	public String doLogin(@Valid UserEntity loginModel, BindingResult bindingResult, Model model) {
		// check for errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Login Form");
			return "login";
		}
		// authenticate the user
		UserEntity uModel = new UserEntity(loginModel.getUsername(), loginModel.getPassword());
		boolean success = securityService.isAuthenticated(uModel, uModel.getUsername(), uModel.getPassword());
		// login succeeded, send to success page
		if (success) {
			// send a user entity for roles
			model.addAttribute("userModel", loginModel);
			return "LoginSuccess";
		}
		else {
			// login failed, return login form
			return "login";
		}
	}	
	
	
	/** 
	 * Render the registration form to the user
	 * @param model
	 * @return Name of view to be rendered
	 */
	// this takes us to the register page
	@GetMapping("/register")
	public String showRegister(Model model) {
		// Display Register Form View
		System.out.println("Showing register page in user controller");
		model.addAttribute("userModel", new UserEntity());
	
		return "register";
	}
	
	
	/** 
	 * Handle the submission of the register form
	 * @param userModel
	 * @param bindingResult
	 * @param model
	 * @param response
	 * @return Name of view to be rendered
	 * @throws Exception 
	 */
	// register form post	
	@PostMapping("/doRegister")
	public String doRegister(@Valid UserEntity userModel, BindingResult bindingResult, Model model, HttpServletResponse response) throws Exception {
		
		// check for errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("userModel", new UserEntity());
			return "register";
		}
		
		// encode password before registering user
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String ecodedPsw = passwordEncoder.encode(userModel.getPassword());
		userModel.setPassword(ecodedPsw);
		
		// register user and track them with cookies
		UserEntity usr1 = securityService.registerUser(userModel, response);
		System.out.println(usr1);
		
		// add user model
		UserEntity user = securityService.getByUsername(userModel);
		model.addAttribute("userModel", user);
		
		
		return "RegisterSuccess";
	}
	
	
	
	
}