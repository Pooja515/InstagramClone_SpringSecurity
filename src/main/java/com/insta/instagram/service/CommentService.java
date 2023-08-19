package com.insta.instagram.service;

import com.insta.instagram.exceptions.CommentException;
import com.insta.instagram.exceptions.PostException;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.Comments;

public interface CommentService {
         public Comments createComment(Comments comment , Integer postId , Integer userId) throws UserException, PostException;
         
         public Comments findCommentById(Integer commentId) throws CommentException;
         
         public Comments likeComment(Integer commentId , Integer userId) throws CommentException , UserException;
         
         public Comments unlikeComment(Integer commentId , Integer userId) throws CommentException , UserException;
} 
