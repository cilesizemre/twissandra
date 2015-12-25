package com.cilesizemre.twissandra.controller.dao.timeline;

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
public class TimelineDaoImpl extends BaseDaoImpl implements TimelineDao {

	public static final String CREATE_TABLE_SCRIPT = "CREATE TABLE timeline (username text, time timeuuid, tweet_id uuid, tweet_body text, PRIMARY KEY (username, time)) WITH CLUSTERING ORDER BY (time DESC);";
	
	private PreparedStatement addTimelinePreparedStatement;
	private PreparedStatement getTimelinePreparedStatement;

	@Autowired
	private UUIDGenerator uuidGenerator;
	
	@PostConstruct
	public void init() {
		addTimelinePreparedStatement = getSession().prepare("INSERT INTO timeline (tweet_id, username, tweet_body, time) VALUES (?, ?, ?, ?);");
		getTimelinePreparedStatement = getSession().prepare("SELECT * FROM timeline WHERE username = ?;");
	}
	
	public void addTimeline(UUID tweetId, String username, String text, MyBatch batch) {
		UUID time = uuidGenerator.generateTimeBased();
		batch.add(getAddTimelineBoundStatement(tweetId, username, text, time));
	}

	public List<Tweet> getTimeline(String username) {
		ResultSet resultSet = getSession().execute(getTimelineBoundStatement(username));

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
	
	private BoundStatement getAddTimelineBoundStatement(UUID tweetId, String username, String text, UUID time) {
		return new BoundStatement(addTimelinePreparedStatement).bind(tweetId, username, text, time);
	}
	
	private BoundStatement getTimelineBoundStatement(String username) {
		return new BoundStatement(getTimelinePreparedStatement).bind(username);
	}

}
