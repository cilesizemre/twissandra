package com.cilesizemre.twissandra.controller.dao.user;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;

public interface UserDao {

	void addUser(String username, String password, MyBatch batch);

}
