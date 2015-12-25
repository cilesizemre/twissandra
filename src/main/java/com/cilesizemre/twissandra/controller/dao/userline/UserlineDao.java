package com.cilesizemre.twissandra.controller.dao.userline;

import java.util.List;
import java.util.UUID;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.model.Tweet;

public interface UserlineDao {

	void addUserline(UUID tweetId, String username, String text, MyBatch batch);
		
	List<Tweet> getUserline(String username);
	
}
