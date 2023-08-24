package com.insta.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.User;
import com.insta.instagram.repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    
   @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(User user) throws UserException {
		Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
		
		if(isEmailExist.isPresent()) {
			throw new UserException("Email is Already Exist");
		}
		
Optional<User> isUsernameExist = userRepository.findByUsername(user.getUsername());
		
		if(isUsernameExist.isPresent()) {
			throw new UserException("Username is Already Taken..");
		}
		
		if(user.getEmail()==null || user.getPassword()==null || user.getUsername()==null || user.getName()==null){
			throw new UserException("All fields are required");
		}
		
			User newUser = new User();
			
			newUser.setEmail(user.getEmail());
		    newUser.setUsername(user.getUsername());
			newUser.setName(user.getName());
			
			
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			
		return userRepository.save(newUser);
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> opt = userRepository.findById(userId);
		
		if(opt.isPresent()) { 
			return opt.get();
		}
		throw new UserException("user will not exist with id :" + userId);
	}

	@Override
	public User findUserProfile(String token) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUsername(String username) throws UserException {
		Optional<User> opt = userRepository.findByUsername(username);
		
		if(opt.isPresent()) {
			return opt.get();
			
		}
		throw new UserException("user not exist with username :" + username);
	}

	@Override
	public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
		
		User reqUser = findUserById(reqUserId);
		User followUser = findUserById(followUserId);
		
		UserDto follower = new UserDto();
		
		follower.setEmail(reqUser.getEmail());
		follower.setId(reqUser.getId());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getUsername());
		follower.setUsername(reqUser.getUsername());
		
        UserDto following = new UserDto();
		
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setUserImage(followUser.getImage());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());
        
        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);
        
        userRepository.save(followUser);
        userRepository.save(reqUser);
        
		
		return "you are following"+followUser.getUsername();
	}

	@Override
	public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {
		
		User reqUser = findUserById(reqUserId);
		User followUser = findUserById(followUserId);
		
		UserDto follower = new UserDto();
		
		follower.setEmail(reqUser.getEmail());
		follower.setId(reqUser.getId());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getUsername());
		follower.setUsername(reqUser.getUsername());
		
        UserDto following = new UserDto();
		
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setUserImage(followUser.getImage());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());
        
        reqUser.getFollowing().remove(follower);
        followUser.getFollower().remove(follower);
        
        userRepository.save(followUser);
        userRepository.save(reqUser);
        
		return "you have unfollowed"+followUser.getUsername();
	}

	@Override
	public List<User> findUserByIds(List<Integer> userIds) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> searchUser(String query) throws UserException {
		List<User> users = userRepository.findByQuery(query);
		if(users.size()==0) {
			throw new UserException("user not found");
		}
		return users;
	}

	@Override
	public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
	
		if(updatedUser.getEmail() != null) {
			existingUser.setEmail(updatedUser.getEmail());
		}
		if(updatedUser.getBio() != null) {
			existingUser.setBio(updatedUser.getBio());
		}
		if(updatedUser.getName() != null) {
			existingUser.setName(updatedUser.getName());
		}
		if(updatedUser.getUsername() != null) {
			existingUser.setUsername(updatedUser.getUsername());
		}
		if(updatedUser.getMobile() != null) {
			existingUser.setMobile(updatedUser.getMobile());
		}
		if(updatedUser.getGender() != null) {
			existingUser.setGender(updatedUser.getGender());
		}
		if(updatedUser.getWebsite() != null) {
			existingUser.setWebsite(updatedUser.getWebsite());
		}
		if(updatedUser.getImage() != null) {
			existingUser.setImage(updatedUser.getImage());
		}
		if(updatedUser.getId().equals(existingUser.getId())) {
			return userRepository.save(existingUser);
		}
		
		
		throw new UserException("you can't update this user");
	}

}
