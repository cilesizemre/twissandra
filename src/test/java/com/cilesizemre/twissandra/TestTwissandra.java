package com.cilesizemre.twissandra;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cilesizemre.twissandra.controller.service.tweet.TweetService;
import com.cilesizemre.twissandra.controller.service.user.UserService;
import com.cilesizemre.twissandra.model.Tweet;
import com.cilesizemre.twissandra.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
		"file:src/main/webapp/WEB-INF/spring/mvc-dispatcher-servlet.xml" })
public class TestTwissandra {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	@Before
	public void initUsers() {
		addUsers();
		createRelationships();
	}
	
	@Test
	public void testFollowers() {
		
		List<User> followersOfUser1 = getFollowers("u1");
		Assert.assertEquals(0, followersOfUser1.size());
		
		List<User> followersOfUser50 = getFollowers("u50");
		Assert.assertEquals(49, followersOfUser50.size());
		
		List<User> followersOfUser100 = getFollowers("u100");
		Assert.assertEquals(99, followersOfUser100.size());
		
	}
	
	@Test
	public void testFollowings() {
		
		List<User> followingsOfUser1 = getFollowings("u1");
		Assert.assertEquals(99, followingsOfUser1.size());
		
		List<User> followingsOfUser50 = getFollowings("u50");
		Assert.assertEquals(50, followingsOfUser50.size());
		
		List<User> followingsOfUser100 = getFollowings("u100");
		Assert.assertEquals(0, followingsOfUser100.size());
	}
	
	@Test
	public void testSendTweet() {
		sendTweet("u1", 1000);
		sendTweet("u50", 1000);
		sendTweet("u100", 1000);
		
		List<Tweet> timelineOfUser1 = getTimeline("u1");
		Assert.assertEquals(3000, timelineOfUser1.size());
		List<Tweet> userlineOfUser1 = getUserline("u1");
		Assert.assertEquals(1000, userlineOfUser1.size());
		
		List<Tweet> timelineOfUser50 = getTimeline("u50");
		Assert.assertEquals(2000, timelineOfUser50.size());
		List<Tweet> userlineOfUser50 = getUserline("u50");
		Assert.assertEquals(1000, userlineOfUser50.size());
		
		List<Tweet> timelineOfUser100 = getTimeline("u100");
		Assert.assertEquals(1000, timelineOfUser100.size());
		List<Tweet> userlineOfUser100 = getUserline("u100");
		Assert.assertEquals(1000, userlineOfUser100.size());
	}
	
	private void addUsers() {
		for (int i = 1; i < 101; i++) {
			userService.addUser("u" + i, "password" + i);
		}
	}
	
	private void createRelationships() {
		for (int i = 1; i < 101; i++) {
			for (int j = i + 1; j < 101; j++) {
				userService.followUser("u" + i, "u" + j);
			}
		}
	}
	
	private List<Tweet> getUserline(String username) {
		return tweetService.getUserline(username);
	}
	
	private List<Tweet> getTimeline(String username) {
		return tweetService.getTimeline(username);
	}
	
	private void sendTweet(String username, int numberOfTweets) {
		for (int i = 0; i < numberOfTweets; i++) {
			tweetService.sendTweet(username, "text" + i + " from " + username);
		}
	}
	
	private List<User> getFollowings(String username) {
		return userService.getFollowingUsers(username);
	}
	
	private List<User> getFollowers(String username) {
		return userService.getFollowers(username);
	}
	
}
