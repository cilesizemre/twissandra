package com.cilesizemre.twissandra.controller.service.tweet;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.controller.dao.batch.BatchExecutor;
import com.cilesizemre.twissandra.controller.dao.follower.FollowerDao;
import com.cilesizemre.twissandra.controller.dao.timeline.TimelineDao;
import com.cilesizemre.twissandra.controller.dao.tweet.TweetDao;
import com.cilesizemre.twissandra.controller.dao.userline.UserlineDao;
import com.cilesizemre.twissandra.model.Tweet;
import com.cilesizemre.twissandra.model.User;

@Service
public class TweetServiceImpl implements TweetService {
	
	@Autowired
	private BatchExecutor batchExecutor;
	
	@Autowired
	private TweetDao tweetDao;
	
	@Autowired
	private UserlineDao userlineDao;
	
	@Autowired
	private TimelineDao timelineDao;
	
	@Autowired
	private FollowerDao followerDao;
	
	public void sendTweet(String username, String text) {
		MyBatch batch = new MyBatch();
		UUID tweetId = tweetDao.addTweet(username, text, batch);
		userlineDao.addUserline(tweetId, username, text, batch);
		timelineDao.addTimeline(tweetId, username, text, batch);
		
		List<User> followers = followerDao.getFollowers(username);
		for (User user : followers) {
			timelineDao.addTimeline(tweetId, user.getUsername(), text, batch);
		}
		
		batchExecutor.execute(batch);
	}
	
	public Tweet getTweetById(UUID tweetId) {
		Tweet tweet = tweetDao.getTweetById(tweetId);
		return tweet;
	}
	
	public List<Tweet> getTimeline(String username) {
		List<Tweet> timeline = timelineDao.getTimeline(username);
		return timeline;
	}
	
	public List<Tweet> getUserline(String username) {
		List<Tweet> userline = userlineDao.getUserline(username);
		return userline;
	}
	
}
