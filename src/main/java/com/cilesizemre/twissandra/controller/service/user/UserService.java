package com.cilesizemre.twissandra.controller.service.user;

import java.util.List;

import com.cilesizemre.twissandra.model.User;

public interface UserService {
	
	void addUser(String username, String password);
	
	void followUser(String fromUsername, String toUsername);
	
	List<User> getFollowers(String followedUsername);
	
	List<User> getFollowingUsers(String followerUsername);

}
