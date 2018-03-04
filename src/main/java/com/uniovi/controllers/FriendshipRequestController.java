package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.services.FriendshipRequestService;

@Controller
public class FriendshipRequestController {

	@Autowired
	FriendshipRequestService friendshipRequestService;
	
	@RequestMapping(value="/sendRequest", method = RequestMethod.POST)
	public String sendRequest(Model model, @RequestParam String sender, @RequestParam Long receiver)
	{

		friendshipRequestService.sendRequest(sender, receiver);
		
		return "redirect:/user/list";
	}
}
