package com.insta.instagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insta.instagram.modal.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
    
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    List<Post> findByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> findAllPostByUserIds(@Param("userIds") List<Integer> userIds);
}
