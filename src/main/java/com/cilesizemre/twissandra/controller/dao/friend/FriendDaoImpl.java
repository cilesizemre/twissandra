package com.cilesizemre.twissandra.controller.dao.friend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.cilesizemre.twissandra.controller.dao.base.BaseDaoImpl;
import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.model.User;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

@Repository
@DependsOn(value = "schemaCreator")
public class FriendDaoImpl extends BaseDaoImpl implements FriendDao {

	public static final String CREATE_TABLE_SCRIPT = "CREATE TABLE friends (username text, friend text, since timestamp, PRIMARY KEY (username, friend));";

	private PreparedStatement addFriendPreparedStatement;
	private PreparedStatement getFriendsPreparedStatement;

	@PostConstruct
	public void init() {
		addFriendPreparedStatement = getSession().prepare("INSERT INTO friends (username, friend, since) VALUES (?, ?, ?);");
		getFriendsPreparedStatement = getSession().prepare("SELECT * FROM friends WHERE username = ?");
	}

	public void addFriend(String username, String friend, MyBatch batch) {
		Date currentTime = new Date();
		batch.add(getAddFriendBoundStatement(username, friend, currentTime));
	}

	public List<User> getFriends(String username) {
		ResultSet resultSet = getSession().execute(getFriendsBoundStatement(username));
		
		List<User> result = new ArrayList<User>();
		for(Row row : resultSet){
			User user = new User();
			user.setUsername(row.getString("friend"));
			result.add(user);
		}
		return result;
	}
	
	private BoundStatement getAddFriendBoundStatement(String username, String friend, Date since) {
		return new BoundStatement(addFriendPreparedStatement).bind(username, friend, since);
	}
	
	private BoundStatement getFriendsBoundStatement(String username) {
		return new BoundStatement(getFriendsPreparedStatement).bind(username);
	}

}
