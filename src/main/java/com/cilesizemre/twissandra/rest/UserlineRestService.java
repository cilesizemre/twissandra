package com.cilesizemre.twissandra.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cilesizemre.twissandra.controller.service.tweet.TweetService;
import com.cilesizemre.twissandra.model.Tweet;

@RestController
@RequestMapping(value = "/userline/")
public class UserlineRestService {
	
	@Autowired
	private TweetService tweetService;
	
	@RequestMapping(value = "/get")
	public List<Tweet> getUserline(@RequestParam String username) {
		List<Tweet> userline = tweetService.getUserline(username);
		return userline;
	}
	
}
