package com.insta.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insta.instagram.modal.Comments;

public interface CommentRepository extends JpaRepository<Comments,Integer> {

}
