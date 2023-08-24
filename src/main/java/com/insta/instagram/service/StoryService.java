package com.insta.instagram.service;

import java.util.List;

import com.insta.instagram.exceptions.StoryException;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.Story;

public interface StoryService {
	
     public Story createStory(Story story , Integer userId) throws UserException;
     
     public List<Story> findStoryByUserId(Integer userId)  throws UserException , StoryException;
}
