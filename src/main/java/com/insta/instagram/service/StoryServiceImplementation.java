package com.insta.instagram.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.exceptions.StoryException;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.Story;
import com.insta.instagram.modal.User;
import com.insta.instagram.repository.StoryRepository;
import com.insta.instagram.repository.UserRepository;

@Service
public class StoryServiceImplementation implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService; // Correct UserService or appropriate service interface

   @Autowired
   private UserRepository userRepository;

    public Story createStory(Story story, Integer userId) throws UserException {
     
        User user  = userService.findUserById(userId);
        
        UserDto userDto = new UserDto();
        
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        story.setUser(userDto);
        
        story.setTimestamp(LocalDateTime.now());

        user.getStories().add(story);

        return storyRepository.save(story);
    }

    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
     
    	User user  = userService.findUserById(userId);
    	
        List<Story> stories = user.getStories();

        if (stories.size()==0) {
        	
            throw new StoryException("This user doesn't have any story");
        }

        return stories;
    }

	
}
