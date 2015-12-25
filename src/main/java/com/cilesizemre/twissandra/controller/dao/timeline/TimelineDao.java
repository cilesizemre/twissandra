package com.cilesizemre.twissandra.controller.dao.timeline;

import java.util.List;
import java.util.UUID;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.model.Tweet;

public interface TimelineDao {

	void addTimeline(UUID tweetId, String username, String text, MyBatch batch); 
	
	List<Tweet> getTimeline(String username);
	
}
