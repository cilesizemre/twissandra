package com.cilesizemre.twissandra.controller.dao.follower;

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
public class FollowerDaoImpl extends BaseDaoImpl implements FollowerDao {
	
	public static final String CREATE_TABLE_SCRIPT = "CREATE TABLE followers (username text, follower text, since timestamp, PRIMARY KEY (username, follower));";
	
	private PreparedStatement addFollowerPreparedStatement;
	private PreparedStatement getFollowersPreparedStatement;
	
	@PostConstruct
	public void init() {
		addFollowerPreparedStatement = getSession().prepare(
				"INSERT INTO followers (username, follower, since) VALUES (?, ?, ?);");
		getFollowersPreparedStatement = getSession().prepare("SELECT * FROM followers WHERE username = ?");
	}
	
	public void addFollower(String username, String friend, MyBatch batch) {
		Date currentTime = new Date();
		batch.add(getAddFollowerBoundStatement(username, friend, currentTime));
	}
	
	public List<User> getFollowers(String username) {
		ResultSet resultSet = getSession().execute(getFollowersBoundStatement(username));
		
		List<User> result = new ArrayList<User>();
		for (Row row : resultSet) {
			User user = new User();
			user.setUsername(row.getString("follower"));
			result.add(user);
		}
		return result;
	}
	
	private BoundStatement getAddFollowerBoundStatement(String username, String friend, Date since) {
		return new BoundStatement(addFollowerPreparedStatement).bind(username, friend, since);
	}
	
	private BoundStatement getFollowersBoundStatement(String username) {
		return new BoundStatement(getFollowersPreparedStatement).bind(username);
	}
	
}
