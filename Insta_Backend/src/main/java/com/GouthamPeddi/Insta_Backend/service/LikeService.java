package com.GouthamPeddi.Insta_Backend.service;

import com.GouthamPeddi.Insta_Backend.model.Like;
import com.GouthamPeddi.Insta_Backend.model.Post;
import com.GouthamPeddi.Insta_Backend.model.User;
import com.GouthamPeddi.Insta_Backend.repository.ILikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    ILikeRepo likeRepo;

    public String addLike(Like like) {
        likeRepo.save(like);
        return "like added.";
    }


    public boolean isLikeAllowedOnThisPost(Post instaPost , User liker) {
        List<Like> likeList = likeRepo.findByInstaPostAndLiker(instaPost , liker);
        return (likeList!=null && likeList.isEmpty());
    }

    public Integer getLikeCountForPost(Post validPost) {
        return likeRepo.findByInstaPost(validPost).size();
    }

    public Like findLike(Integer likeId) {
        return likeRepo.findById(likeId).orElse(null);
    }

    public String removeLike(Like like) {
        likeRepo.delete(like);
        return "like removed successfully.";
    }
}
