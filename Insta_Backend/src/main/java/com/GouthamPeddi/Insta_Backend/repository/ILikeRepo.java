package com.GouthamPeddi.Insta_Backend.repository;

import com.GouthamPeddi.Insta_Backend.model.Like;
import com.GouthamPeddi.Insta_Backend.model.Post;
import com.GouthamPeddi.Insta_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ILikeRepo extends JpaRepository<Like,Integer> {

    List<Like> findByInstaPostAndLiker(Post instaPost, User liker);


    List<Like> findByInstaPost(Post validPost);
}
