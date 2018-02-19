package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entites.User;
import com.uniovi.services.UsersService;

@Controller
public class UsersController {

	@Autowired // Inyectar el servicio
	private UsersService usersService;

	@RequestMapping("/user/list")
	public String getList(Model model) {
		model.addAttribute("userList", usersService.getUsers());
		return usersService.getUsers().toString();
	}

	@RequestMapping(value = "/user/add")
	public String getMark() {
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setMark(@ModelAttribute User mark) {
		usersService.addMark(mark);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/details")
	public String getDetail(Model model, @RequestParam Long id) {
		model.addAttribute("mark", usersService.getMark(id));
		return "user/details";

	}

	@RequestMapping("/user/delete/{id}")
	public String deleteMark(@PathVariable Long id) {
		usersService.deleteMark(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getMark(id));
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
		user.setId(id);
		usersService.addMark(user);
		return "redirect:/user/details/" + id;
	}

}
