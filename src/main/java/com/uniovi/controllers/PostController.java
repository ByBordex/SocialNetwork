package com.uniovi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entites.Post;
import com.uniovi.entites.User;
import com.uniovi.services.PostService;
import com.uniovi.services.UsersService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UsersService usersService;

	@RequestMapping("/posts/list")
	public String getList(Model model, Principal principal) {
		User author = usersService.getUserByEmail( principal.getName() );
		List<Post> posts = postService.findPostsFromUser( author );
		model.addAttribute("postsList", posts);
		return "post/list";
	}

	@RequestMapping("/posts/list/{id}")
	public String getList(Model model, Principal principal, @PathVariable Long id) {
		return "redirect:/posts/list";
	}

	@RequestMapping(value="/posts/post", method = RequestMethod.GET)
	public String sendPost() {
		return "post/add";
	}

	@RequestMapping(value="/posts/post", method = RequestMethod.POST)
	public String sendPost(@ModelAttribute Post post, Principal principal, @RequestParam(value="photo", required=false) MultipartFile photo) 
	{
		User author = usersService.getUserByEmail( principal.getName() );

		try {
			System.out.println("llega aqui");
			post.setUser( author );
			postService.addPost( post );
			if (photo != null) {
				InputStream is = photo.getInputStream();
				Files.copy(is, Paths.get("src/main/resources/static/img/posts/" + post.getId()),
						StandardCopyOption.REPLACE_EXISTING);
				post.setPhoto( true );
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/posts/list";
		}
		return "redirect:/posts/list";
	}

}
