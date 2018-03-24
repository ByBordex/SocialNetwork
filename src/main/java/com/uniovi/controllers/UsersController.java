package com.uniovi.controllers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entites.User;
import com.uniovi.services.FriendshipRequestService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	FriendshipRequestService friendshipRequestService;

	@Autowired
	private RolesService roles;

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);

		Page<User> users = new PageImpl<User>(new LinkedList<User>());

		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchByNameOrEmail(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}

		Set<User> usersRequestedFriendship = usersService.getUsersFriendshipRequiredInList(activeUser, 
				users.getContent());
		Set<User> usersReceivedRequest = friendshipRequestService.getUserSendedRequesToUser(activeUser,
				users.getContent());
		Set<User> friendsInPage = friendshipRequestService.getFriendsInList(activeUser, users.getContent()) ;

		Set<User> pending = new HashSet<User>();
		usersRequestedFriendship.forEach(pending::add);
		usersReceivedRequest.forEach(pending::add);

		model.addAttribute("friendList", friendsInPage);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		model.addAttribute("pending", pending);

		return "user/list";
	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "user/details";
	}

	public String signup() {
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result, Model model) {

		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(roles.getRole(0));
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String adminLogin(Model model) {
		return "adminLogin";
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String adminLoginPost(Model model, @RequestParam String username, @RequestParam String password) {

		User logUser = usersService.getUserByEmail(username);
		if (logUser != null) {
			if (!logUser.getRole().equals(roles.getRole(1))) {
				return "redirect:/admin/login?error=true";
			}
		}

		securityService.autoLogin(username, password);
		return "redirect:/user/list";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = { "/admin/user/list" }, method = RequestMethod.GET)
	public String adminUserList(Model model) {
		List<User> users = usersService.getUsers();
		model.addAttribute( "usersList", users );
		return "user/adminList";
	}

	@RequestMapping(value = { "/admin/removeUser" }, method = RequestMethod.POST)
	public String removeUser(Model model, @RequestParam Long removedUser) {
		User user = usersService.getUser(removedUser);
		usersService.removeUser(user);
		return "redirect:/admin/user/list";
	}

}