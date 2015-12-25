package com.cilesizemre.twissandra.controller.dao.userline;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.cilesizemre.twissandra.controller.dao.base.BaseDaoImpl;
import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.cilesizemre.twissandra.controller.service.uuid.UUIDGenerator;
import com.cilesizemre.twissandra.model.Tweet;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

@Repository
@DependsOn(value = "schemaCreator")
public class UserlineDaoImpl extends BaseDaoImpl implements UserlineDao {
	
	public static final String CREATE_TABLE_SCRIPT = "CREATE TABLE userline (username text, time timeuuid, tweet_id uuid, tweet_body text, PRIMARY KEY (username, time)) WITH CLUSTERING ORDER BY (time DESC);";
	
	private PreparedStatement addUserlinePreparedStatement;
	private PreparedStatement getUserlinePreparedStatement;
	
	@Autowired
	private UUIDGenerator uuidGenerator;
	
	@PostConstruct
	public void init() {
		addUserlinePreparedStatement = getSession().prepare(
				"INSERT INTO userline (tweet_id, username, tweet_body, time) VALUES (?, ?, ?, ?);");
		getUserlinePreparedStatement = getSession().prepare("SELECT * FROM userline WHERE username = ?");
	}
	
	public void addUserline(UUID tweetId, String username, String text, MyBatch batch) {
		UUID time = uuidGenerator.generateTimeBased();
		batch.add(getAddUserlineBoundStatement(tweetId, username, text, time));
	}
	
	public List<Tweet> getUserline(String username) {
		ResultSet resultSet = getSession().execute(getUserlineBoundStatement(username));
		
		List<Tweet> result = new ArrayList<Tweet>();
		for (Row row : resultSet) {
			UUID tweetId = row.getUUID("tweet_id");
			String tweetBody = row.getString("tweet_body");
			String uname = row.getString("username");
			
			Tweet tweet = new Tweet();
			tweet.setId(tweetId);
			tweet.setText(tweetBody);
			tweet.setUsername(uname);
			result.add(tweet);
		}
		return result;
	}
	
	private BoundStatement getAddUserlineBoundStatement(UUID tweetId, String username, String text, UUID time) {
		return new BoundStatement(addUserlinePreparedStatement).bind(tweetId, username, text, time);
	}
	
	private BoundStatement getUserlineBoundStatement(String username) {
		return new BoundStatement(getUserlinePreparedStatement).bind(username);
	}
	
}
