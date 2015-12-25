package com.cilesizemre.twissandra.controller.dao.tweet;

import java.util.UUID;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.model.Tweet;

public interface TweetDao {

	UUID addTweet(String username, String text, MyBatch batch);

	Tweet getTweetById(UUID tweetId);
	
}
