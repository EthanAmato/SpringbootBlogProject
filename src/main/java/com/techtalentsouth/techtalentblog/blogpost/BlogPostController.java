package com.techtalentsouth.techtalentblog.blogpost;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//restcontroller is supposed to act as controller for an api - anything you return is typically displayed directly
//@Restcontroller = putting @Controller and then adding @Responsebody in front of a method
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.techtalentsouth.techtalentblog.repositories.BlogPostRepository;

@Controller //works with web pages
public class BlogPostController {
	
	@Autowired //tell springboot to setup blogPostRepository (from an interface which is pretty cool)
	private BlogPostRepository repository;
	
	@GetMapping(path="/")
	public String index(Model model) { //returns main page of our
		List<BlogPost> posts = repository.findAll();
		model.addAttribute("posts",posts);
		return "blogpost/index";
	}
	
	@GetMapping(path="/new")
	public String newPost(Model model) { //returns main page of our
		BlogPost blogPost = new BlogPost();
		model.addAttribute("blogPost",blogPost);

		return "blogpost/new";
	}
	
		
	@GetMapping(value = "/blogposts/new")
	public String newBlog(BlogPost blogPost, Model model) {
		return "blogpost/new";
	}
	
	@PostMapping(path="/blogposts") //sent to / by the form 
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		
		BlogPost dbBlogPost = repository.save(blogPost); // returns the object that is saved - includes the id given by db
				

		model.addAttribute("blogPost",dbBlogPost);
		
		return "blogpost/result";

		
	}
	
	
	//To edit a post, we add a controller that edits a single method and identify which post needs to be edited
	//via specifying the id of the post in the url and the id will specify primary key in db
	
	
	@GetMapping(path="/blogposts/{id}") //sent to / by the form 
	public String editPostWithId(@PathVariable Long id, Model model) {
		
		Optional<BlogPost> post = repository.findById(id); // returns the object that is saved - includes the id given by db
		//optional is just a container to hold the object returned by findById
		//if empty it'll just be null
		if (post.isPresent()) {
			BlogPost actualPost = post.get();
			model.addAttribute("blogPost",actualPost);
		} 
		return "blogpost/edit";

		
	}

	
	@PostMapping(path="/blogposts/{id}")
	public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
		Optional<BlogPost> post = repository.findById(id); // returns the object that is saved - includes the id given by db
		//optional is just a container to hold the object returned by findById
		//if empty it'll just be null
		if (post.isPresent()) {
			BlogPost actualPost = post.get();
			actualPost.setTitle(blogPost.getTitle());
			actualPost.setAuthor(blogPost.getAuthor());
			actualPost.setBlogEntry(blogPost.getBlogEntry());
			repository.save(actualPost);
			model.addAttribute("blogPost",actualPost);
		} 
		return "blogpost/result";
		
	}
	
	
	@GetMapping(path="blogposts/delete/{id}")
	public String deletePostById(@PathVariable Long id) {
		repository.deleteById(id);
		return "blogpost/delete";
	}
	
}

