package com.cilesizemre.twissandra.controller.service.tweet;

import java.util.List;
import java.util.UUID;

import com.cilesizemre.twissandra.model.Tweet;

public interface TweetService {
	
	void sendTweet(String username, String text);
	
	Tweet getTweetById(UUID tweetId);
	
	List<Tweet> getTimeline(String username);
	
	List<Tweet> getUserline(String username);
	
}
