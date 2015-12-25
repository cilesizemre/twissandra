package com.cilesizemre.twissandra.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cilesizemre.twissandra.controller.service.user.UserService;

@RestController
@RequestMapping(value = "/user/")
public class UserRestService {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/add")
	public void addUser(@RequestParam String username, @RequestParam String password) {
		userService.addUser(username, password);
	}
	
	@RequestMapping(value = "/follow")
	public void followUser(@RequestParam String username, @RequestParam String friend) {
		userService.followUser(username, friend);
	}
	
}
