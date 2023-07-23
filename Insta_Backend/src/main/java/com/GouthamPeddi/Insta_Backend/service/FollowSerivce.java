package com.GouthamPeddi.Insta_Backend.service;

import com.GouthamPeddi.Insta_Backend.model.Follow;
import com.GouthamPeddi.Insta_Backend.model.User;
import com.GouthamPeddi.Insta_Backend.repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowSerivce {

    @Autowired
    IFollowRepo followRepo;


    public boolean isFollowAllowed(User followTargetUser, User follower) {
        List<Follow> followList = followRepo.findByCurrentUserAndCurrentUserFollower(followTargetUser , follower);
        return (followList!=null && followList.isEmpty() && !follower.equals(followTargetUser));
    }

    public void startFollowing(Follow follow, User follower) {
        follow.setCurrentUserFollower(follower);
        followRepo.save(follow);
    }


    public Follow findFollow(Integer followId) {
        return followRepo.findById(followId).orElse(null);
    }

    public void unFollow(Follow follow) {
        followRepo.delete(follow);
    }
}
