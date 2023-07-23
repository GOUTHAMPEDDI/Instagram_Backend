package com.GouthamPeddi.Insta_Backend.controller;

import com.GouthamPeddi.Insta_Backend.model.*;
import com.GouthamPeddi.Insta_Backend.model.dto.SignInInput;
import com.GouthamPeddi.Insta_Backend.model.dto.SignUpOutput;
import com.GouthamPeddi.Insta_Backend.service.AuthenticationService;
import com.GouthamPeddi.Insta_Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("User/signUp")
    public SignUpOutput signUp(@RequestBody User user){
        return userService.signUpUser(user);
    }

    @PostMapping("User/signIn")
    public String signInUser(@RequestBody @Valid SignInInput signInInput){
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("User/signOut/{email}/{token}")
    public String signOutUser(@PathVariable String email ,@PathVariable String token){
        if(authenticationService.authenticate(email,token)) {
            return userService.signOutUser(email);
        }
        return "Sign out not allowed for non authenticated user";
    }

    @PostMapping("Post/{email}/{tokenVal}")
    public String createInstaPost(@RequestBody Post post , @PathVariable String email , @PathVariable String tokenVal){
        if(authenticationService.authenticate(email , tokenVal)){
            return userService.createInstaPost(post , email);
        }else{
            return "Not an authenticated user activity";
        }
    }

    @DeleteMapping("Post/{email}/{tokenVal}")
    public String removeInstaPost(@RequestBody Integer postId , @PathVariable String email , @PathVariable String tokenVal){
        if(authenticationService.authenticate(email , tokenVal)){
            return userService.removeInstaPost(postId , email);
        }else{
            return "Not an authenticated user activity";
        }
    }

    @PostMapping("Comment/{commenterEmail}/{commenterTokenVal}")
    public String createComment(@RequestBody Comment comment , @PathVariable String commenterEmail , @PathVariable String commenterTokenVal){
        if(authenticationService.authenticate(commenterEmail , commenterTokenVal)){
            return userService.createInstaComment(comment , commenterEmail);
        }else{
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("Comment")
    public String removeInstaComment(@RequestParam Integer commentId , @RequestParam String email , @RequestParam String tokenVal){
        if(authenticationService.authenticate(email,tokenVal)){
            return userService.removeInstaComment(commentId,email);
        }else{
            return "Not an Authenticated user activity!!!";
        }
    }

    @PostMapping("Like")
    public String AddLike(@RequestBody Like like , @RequestParam String likerEmail , @RequestParam String likerTokenVal){
        if(authenticationService.authenticate(likerEmail,likerTokenVal)){
            return userService.addLike(like,likerEmail);
        }else{
            return "Not an authorised user activity!!";
        }
    }


    @GetMapping("Like/count/post/postId")
    public String getLikeCountByPostId(@PathVariable Integer postId , @RequestParam String email , @RequestParam String tokenValue){
        if(authenticationService.authenticate(email , tokenValue)){
            return userService.getLikeCountByPostId(postId , email);
        }else{
            return "Not an authorised user activity!!";
        }
    }

    @DeleteMapping("Like")
    public String removeLike(@RequestParam Integer likeId , @RequestParam String email , @RequestParam String tokenVal){
        if(authenticationService.authenticate(email , tokenVal)){
            return userService.romoveLike(likeId , email);
        }else{
            return "Not an authenticated user activity!!!";
        }
    }

    @PostMapping("Follow")
    public String followUser(@RequestBody Follow follow , @RequestParam String followerEmail , @RequestParam String followerTokenVal){
        if(authenticationService.authenticate(followerEmail , followerTokenVal)){
            return userService.followUser(follow , followerEmail);
        }else{
            return "Not an authenticated user activity!!!";
        }
    }

    @DeleteMapping("unfollow/target/{followId}")
    public String unFollowUser(@PathVariable Integer followId, @RequestParam String followerEmail, @RequestParam String followerToken)
    {
        if(authenticationService.authenticate(followerEmail,followerToken)) {
            return userService.unFollowUser(followId,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

}
