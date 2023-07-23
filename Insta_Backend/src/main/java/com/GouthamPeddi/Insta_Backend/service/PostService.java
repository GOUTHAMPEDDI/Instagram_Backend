package com.GouthamPeddi.Insta_Backend.service;

import com.GouthamPeddi.Insta_Backend.model.Post;
import com.GouthamPeddi.Insta_Backend.model.User;
import com.GouthamPeddi.Insta_Backend.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    IPostRepo postRepo;

    public String createInstaPost(Post post) {
        post.setPostCreatedTimeStamp(LocalDateTime.now());
        postRepo.save(post);
        return "post uploaded";
    }

    public String removeInstaPost(Integer postId, User user) {
        Post post = postRepo.findById(postId).orElse(null);
        if(post == null){
            return "post does not exist with this Id";
        }
        User postOwner = post.getPostOwner();
        if(postOwner.equals(user)){
            postRepo.delete(post);
            return "post deleted";
        }else if(post == null){
            return "post does not exist with this Id";
        }else{
            return "Unauthorised access";
        }
    }


    public boolean isValidPost(Post instaPost) {
        return (instaPost != null && postRepo.existsById(instaPost.getPostId()));
    }


    public Post getPostById(Integer postId) {
        return postRepo.findById(postId).orElse(null);
    }
}
