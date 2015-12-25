package com.cilesizemre.twissandra.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cilesizemre.twissandra.controller.service.tweet.TweetService;

@RestController
@RequestMapping(value = "/tweet/")
public class TweetRestService {
	
	@Autowired
	private TweetService tweetService;
	
	@RequestMapping(value = "/send")
	public void sendTweet(@RequestParam String username, @RequestParam String text) {
		tweetService.sendTweet(username, text);
	}
	
}
//http://localhost/twissandra/user/add?username=user1&password=123456
//http://localhost/twissandra/user/follow?username=user1&friend=user2
//http://localhost/twissandra/tweet/send?username=user1&text=tweet3
//http://localhost/twissandra/userline/get?username=user1
//http://localhost/twissandra/timeline/get?username=user1
//http://localhost/twissandra/followings/get?username=user1