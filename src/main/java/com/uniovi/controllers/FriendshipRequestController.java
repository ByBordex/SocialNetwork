package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entites.User;
import com.uniovi.services.FriendshipRequestService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipRequestController {

	@Autowired
	FriendshipRequestService friendshipRequestService;
	
	@Autowired
	UsersService userService;
	
	@RequestMapping(value="/friendshipRequest/list")
	public String getRequestList(Model model, Principal principal, Pageable pageable)
	{
		User user = userService.getUserByEmail( principal.getName() );
		model.addAttribute("requestList", friendshipRequestService.getRequestToUser(pageable, user.getId() ) );
		return "friendshipRequest/list";
	}
	
	
	@RequestMapping(value="/sendRequest", method = RequestMethod.POST)
	public String sendRequest(Model model, @RequestParam String sender, @RequestParam Long receiver)
	{

		friendshipRequestService.sendRequest(sender, receiver);
		
		return "redirect:/user/list";
	}
}
