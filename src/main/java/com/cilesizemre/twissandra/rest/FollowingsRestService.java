package com.cilesizemre.twissandra.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cilesizemre.twissandra.controller.service.user.UserService;
import com.cilesizemre.twissandra.model.User;

@RestController
@RequestMapping(value = "/followings/")
public class FollowingsRestService {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/get")
	public List<User> getFollowings(@RequestParam String username) {
		List<User> followings = userService.getFollowingUsers(username);
		return followings;
	}
	
}
