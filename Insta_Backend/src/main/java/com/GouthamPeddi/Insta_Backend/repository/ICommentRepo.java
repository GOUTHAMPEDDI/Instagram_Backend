package com.GouthamPeddi.Insta_Backend.repository;

import com.GouthamPeddi.Insta_Backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepo extends JpaRepository<Comment,Integer> {
}
