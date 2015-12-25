package com.cilesizemre.twissandra.controller.dao.follower;

import java.util.List;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.model.User;

public interface FollowerDao {

	void addFollower(String username, String friend, MyBatch batch);

	List<User> getFollowers(String username);

}
