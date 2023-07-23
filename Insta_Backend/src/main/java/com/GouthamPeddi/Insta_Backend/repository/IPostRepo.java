package com.GouthamPeddi.Insta_Backend.repository;

import com.GouthamPeddi.Insta_Backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepo extends JpaRepository<Post,Integer> {
}
