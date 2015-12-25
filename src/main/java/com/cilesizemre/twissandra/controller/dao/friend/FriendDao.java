package com.cilesizemre.twissandra.controller.dao.friend;

import java.util.List;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.model.User;

public interface FriendDao {

	void addFriend(String username, String friend, MyBatch batch);
	
	List<User> getFriends(String username);

}
