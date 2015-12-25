package com.cilesizemre.twissandra.controller.dao.tweet;

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
public class TweetDaoImpl extends BaseDaoImpl implements TweetDao {

	public static final String CREATE_TABLE_SCRIPT = "CREATE TABLE tweets (tweet_id uuid PRIMARY KEY, username text, body text);";

	private PreparedStatement addTweetPreparedStatement;
	private PreparedStatement getTweetByIdPreparedStatement;

	@Autowired
	private UUIDGenerator uuidGenerator;
	
	@PostConstruct
	public void init() {
		addTweetPreparedStatement = getSession().prepare("INSERT INTO tweets (tweet_id, username, body) VALUES (?, ?, ?);");
		getTweetByIdPreparedStatement = getSession().prepare("SELECT * FROM tweets WHERE tweet_id = ?");
	}
	
	public UUID addTweet(String username, String text, MyBatch batch) {
		UUID tweetId = uuidGenerator.generate();
		batch.add(getAddTweetBoundStatement(tweetId, username, text));
		return tweetId;
	}
	
	public Tweet getTweetById(UUID tweetId) {
		ResultSet resultSet = getSession().execute(getTweetByIdBoundStatement(tweetId));

		Row row = resultSet.one();
		Tweet tweet = new Tweet();
		tweet.setId(row.getUUID("tweet_id"));
		tweet.setText(row.getString("body"));
		tweet.setUsername(row.getString("username"));
		return tweet;
	}
	
	private BoundStatement getAddTweetBoundStatement(UUID tweetId, String username, String text) {
		return new BoundStatement(addTweetPreparedStatement).bind(tweetId, username, text);
	}

	private BoundStatement getTweetByIdBoundStatement(UUID tweetId) {
		return new BoundStatement(getTweetByIdPreparedStatement).bind(tweetId);
	}

}
