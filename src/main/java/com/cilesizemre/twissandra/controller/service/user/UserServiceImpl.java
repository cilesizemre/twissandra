package com.cilesizemre.twissandra.controller.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.controller.dao.batch.BatchExecutor;
import com.cilesizemre.twissandra.controller.dao.follower.FollowerDao;
import com.cilesizemre.twissandra.controller.dao.friend.FriendDao;
import com.cilesizemre.twissandra.controller.dao.user.UserDao;
import com.cilesizemre.twissandra.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BatchExecutor batchExecutor;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FriendDao friendDao;
	
	@Autowired
	FollowerDao followerDao;
	
	public void addUser(String username, String password) {
		MyBatch batch = new MyBatch();
		userDao.addUser(username, password, batch);
		batchExecutor.execute(batch);
	}
	
	public void followUser(String username, String friend) {
		MyBatch batch = new MyBatch();
		friendDao.addFriend(username, friend, batch);
		followerDao.addFollower(friend, username, batch);
		batchExecutor.execute(batch);
	}
	
	public List<User> getFollowers(String username) {
		List<User> followers = followerDao.getFollowers(username);
		return followers;
	}
	
	public List<User> getFollowingUsers(String username) {
		List<User> friends = friendDao.getFriends(username);
		return friends;
	}
	
}
