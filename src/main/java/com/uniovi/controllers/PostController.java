package com.uniovi.controllers;

import java.io.File;
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
import org.springframework.validation.BindingResult;
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
	public String sendPost(Model model) {
		model.addAttribute("post", new Post() );
		int postID = postService.getLast();
		model.addAttribute("postID", postID);
		return "post/add";
	}

	@RequestMapping(value="/posts/post", method = RequestMethod.POST)
	public String sendPost(@ModelAttribute Post post, BindingResult b, Principal principal, @RequestParam(required = false)MultipartFile photo ) 
	{
		User author = usersService.getUserByEmail( principal.getName() );
		
		try {
			post.setUser( author );
			post.setCreationStringDate();

			if (photo != null && !photo.isEmpty()) {
				int valor = postService.countPostsFromUser( author );
				File f = new File( "src/main/resources/static/img/posts/" + author.getId() + "/" + valor + ".png" );
				f.getParentFile().mkdirs(); 
				f.createNewFile();
				f = new File( "resources/static/img/posts/" + author.getId() + "/" + valor + ".png" );
				f.getParentFile().mkdirs(); 
				f.createNewFile();
				
				post.setPhoto( "img/posts/" + author.getId() + "/" + valor + ".png");
				
				InputStream is = photo.getInputStream();
				Files.copy(is, Paths.get(f.getPath() ),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		postService.addPost( post );
		return "redirect:/posts/list";
	}

}
