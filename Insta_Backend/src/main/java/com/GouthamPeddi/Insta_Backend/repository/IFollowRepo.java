package com.GouthamPeddi.Insta_Backend.repository;

import com.GouthamPeddi.Insta_Backend.model.Follow;
import com.GouthamPeddi.Insta_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFollowRepo extends JpaRepository<Follow,Integer> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User followTargetUser, User follower);

}
