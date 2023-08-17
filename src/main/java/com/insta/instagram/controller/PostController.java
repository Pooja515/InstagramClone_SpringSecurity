package com.insta.instagram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insta.instagram.exceptions.PostException;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.Post;
import com.insta.instagram.modal.User;
import com.insta.instagram.response.MessageResponse;
import com.insta.instagram.service.PostService;
import com.insta.instagram.service.UserService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestHeader("Authorization") String token) throws UserException{
		
	    User user = userService.findUserProfile(token);
		Post createdPost = postService.createPost(post, user.getId());
		
		return new ResponseEntity<Post>(createdPost, HttpStatus.OK);
	
}
// @RequestBody Post post, @RequestHeader("Authorization") String token
@GetMapping("/all/{id}")
public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("id") Integer userId)  throws UserException {
	
	List<Post> posts = postService.findPostByUserId(userId);
	
	return new ResponseEntity<List<Post>> (posts,HttpStatus.OK);
}

@GetMapping("/following/{ids}")
public ResponseEntity<List<Post>> findAllPostByUserIdsHandler(@PathVariable("ids") List<Integer> userIds)  throws UserException, PostException {
	
	List<Post> posts = postService.findAllPostByUserIds(userIds);
	
	return new ResponseEntity<List<Post>> (posts,HttpStatus.OK);
}
@GetMapping("/{postId}")
public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId)  throws PostException ,UserException{
	
	Post post = postService.findPostById(postId);
	
	return new ResponseEntity<Post>(post ,HttpStatus.OK);
}

@PutMapping("/like/{postId}")
public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization")String  token)  throws PostException ,UserException{
	User user = userService.findUserProfile(token);
	Post likedPost = postService.likePost(postId, user.getId());
	
	return new ResponseEntity<Post>(likedPost ,HttpStatus.OK);
}

@PutMapping("/unlike/{postId}")
public ResponseEntity<Post> unlikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization")String  token)  throws PostException ,UserException{
	User user = userService.findUserProfile(token);
	Post likedPost = postService.unlikePost(postId, user.getId());
	
	return new ResponseEntity<Post>(likedPost ,HttpStatus.OK);
}

@DeleteMapping("/delete/{postId}")
public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization")String  token)  throws PostException ,UserException{
	
	User user = userService.findUserProfile(token);
	
	String message = postService.deletePost(postId, user.getId());
	
	MessageResponse res = new MessageResponse(message);
	
	return new ResponseEntity<MessageResponse>(res ,HttpStatus.ACCEPTED);
}

@PutMapping("/save_post/{postId}")
public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization")String  token)  throws PostException ,UserException{
	
	User user = userService.findUserProfile(token);
	
	String message = postService.savedPost(postId, user.getId());
	
	MessageResponse res = new MessageResponse(message);
	
	return new ResponseEntity<MessageResponse>(res ,HttpStatus.ACCEPTED);
}
@PutMapping("/unsave_post/{postId}")
public ResponseEntity<MessageResponse> unsavedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization")String  token)  throws PostException ,UserException{
	
	User user = userService.findUserProfile(token);
	
	String message = postService.unSavedPost(postId, user.getId());
	
	MessageResponse res = new MessageResponse(message);
	
	return new ResponseEntity<MessageResponse>(res ,HttpStatus.ACCEPTED);
}
}
