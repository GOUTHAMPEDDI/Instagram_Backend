package com.GouthamPeddi.Insta_Backend.service;

import com.GouthamPeddi.Insta_Backend.model.*;
import com.GouthamPeddi.Insta_Backend.model.dto.SignInInput;
import com.GouthamPeddi.Insta_Backend.model.dto.SignUpOutput;
import com.GouthamPeddi.Insta_Backend.repository.IUserRepo;
import com.GouthamPeddi.Insta_Backend.service.emailUtility.EmailHandler;
import com.GouthamPeddi.Insta_Backend.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowSerivce followSerivce;

    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();
        if (newEmail == null) {
            signUpStatus = false;
            signUpStatusMessage = "invalid email";
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //check if this user email already exists ??

        User existingUser = userRepo.findFirstByuserEmail(newEmail);
        if (existingUser != null) {
            signUpStatus = false;
            signUpStatusMessage = "user already registered";
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //save the user with the new encrypted password
            user.setUserPassword(encryptedPassword);

            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "user successfully registered");

        } catch (Exception e) {
            signUpStatus = false;
            signUpStatusMessage = "internal error occured during sign up";
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

    }

    public String signInUser(SignInInput signInInput){

        String signInStatusMessage = null;
        String signInEmail = signInInput.getEmail();

        if(signInEmail==null){
            signInStatusMessage = "invalid email";
            return signInStatusMessage;
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByuserEmail(signInEmail);

        if(existingUser==null){
            signInStatusMessage = "user not registered";
            return signInStatusMessage;
        }


        //match passwords :

        //hash the password: encrypt the password

        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());

            if(encryptedPassword.equals(existingUser.getUserPassword())){

                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailHandler.sendEmail("<Receiver Mail Id>","Subject",authToken.getTokenValue());
                return "Token sent to your email";

            }
            else{
                signInStatusMessage = "invalid credentials";
                return signInStatusMessage;
            }

        } catch (Exception e) {
            signInStatusMessage = "internal error occured during sign in";
            return signInStatusMessage;
        }

    }

    public String signOutUser(String email){
        User user = userRepo.findFirstByuserEmail(email);
        AuthenticationToken authToken = authenticationService.findFirstByUser(user);
        authenticationService.removeAuthToken(authToken);
        return "session ended";
    }


    public String createInstaPost(Post post , String email) {

        User postOwner = userRepo.findFirstByuserEmail(email);
        post.setPostOwner(postOwner);
        return postService.createInstaPost(post);

    }

    public String removeInstaPost(Integer postId, String email) {
        User user = userRepo.findFirstByuserEmail(email);
        return postService.removeInstaPost(postId , user);
    }

    public String createInstaComment(Comment comment, String commenterEmail) {
        boolean validPost = postService.isValidPost(comment.getInstaPost());
        if(validPost){
            User commenter = userRepo.findFirstByuserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }else{
            return "Cannot comment on Invalid Post!!";
        }
    }

    boolean authorizeCommentRemover(String email , Comment comment){
        String commentOwnerEmail = comment.getCommenter().getUserEmail();
        String postOwnerEmail = comment.getInstaPost().getPostOwner().getUserEmail();

        return (email.equals(commentOwnerEmail) || email.equals(postOwnerEmail));
    }

    public String removeInstaComment(Integer commentId, String email) {
        Comment comment = commentService.findComment(commentId);
        if(comment != null){
            if(authorizeCommentRemover(email , comment)){
                return commentService.removeComment(comment);
            }else{
                return "not an authorised request!!!";
            }
        }else{
            return "comment not exist!!!";
        }
    }


    public String addLike(Like like, String likerEmail) {
        Post instaPost = like.getInstaPost();
        if (postService.isValidPost(instaPost)){
            User liker = userRepo.findFirstByuserEmail(likerEmail);
            if(likeService.isLikeAllowedOnThisPost(instaPost , liker)){
                like.setLiker(liker);
                return likeService.addLike(like);
            }else{
                return "already liked.";
            }
        }else{
            return "cannot like on invalid post";
        }
    }


    public String getLikeCountByPostId(Integer postId, String email) {
        Post validPost = postService.getPostById(postId);
        if(validPost!=null){
            Integer likeCountForPost = likeService.getLikeCountForPost(validPost);
            return String.valueOf(likeCountForPost);
        }else{
            return "cannot like on invalid post";
        }
    }

    boolean authorizeLikeRemover(Like like , String potentialLikeRemover){
        String likeOwner = like.getLiker().getUserEmail();
        return likeOwner.equals(potentialLikeRemover);
    }

    public String romoveLike(Integer likeId, String email) {
        Like like = likeService.findLike(likeId);
        if(like!=null){
            if(authorizeLikeRemover(like , email)){
                return likeService.removeLike(like);
            }else{
                return "Unauthorized delete detected...Not allowed!!!!";
            }
        }else{
            return "invalid like";
        }
    }


    public String followUser(Follow follow, String followerEmail) {
        User followTargetUser = userRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);
        User follower = userRepo.findFirstByuserEmail(followerEmail);

        if(followTargetUser!=null){
            if(followSerivce.isFollowAllowed(followTargetUser , follower)){
                followSerivce.startFollowing(follow , follower);
                return follower.getUserHandle()  + " is now following " + followTargetUser.getUserHandle();
            }else{
                return follower.getUserHandle()  + " already follows " + followTargetUser.getUserHandle();
            }
        }else{
            return "User to be followed is Invalid!!!";
        }
    }

    boolean authorizeFollow(String email , Follow follow){
        String targetUserEmail = follow.getCurrentUser().getUserEmail();
        String followerEmail = follow.getCurrentUserFollower().getUserEmail();
        return targetUserEmail.equals(email) || followerEmail.equals(email);
    }

    public String unFollowUser(Integer followId, String followerEmail) {
        Follow follow = followSerivce.findFollow(followId);
        if(follow!=null){
        if(authorizeFollow(followerEmail,follow)){
            followSerivce.unFollow(follow);
            return follow.getCurrentUser().getUserHandle() + "not followed by " + followerEmail;
        }
        else
        {
            return "Unauthorized unfollow detected...Not allowed!!!!";
        }

    }
        else
    {
        return "Invalid follow mapping";
    }
    }
}
