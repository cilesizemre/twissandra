package com.cilesizemre.twissandra.controller.dao.schemacreator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.cilesizemre.twissandra.controller.dao.Connector;
import com.cilesizemre.twissandra.controller.dao.base.BaseDaoImpl;
import com.cilesizemre.twissandra.controller.dao.follower.FollowerDaoImpl;
import com.cilesizemre.twissandra.controller.dao.friend.FriendDaoImpl;
import com.cilesizemre.twissandra.controller.dao.timeline.TimelineDaoImpl;
import com.cilesizemre.twissandra.controller.dao.tweet.TweetDaoImpl;
import com.cilesizemre.twissandra.controller.dao.user.UserDaoImpl;
import com.cilesizemre.twissandra.controller.dao.userline.UserlineDaoImpl;
import com.datastax.driver.core.exceptions.InvalidQueryException;

@SuppressWarnings("restriction")
public class SchemaCreatorImpl extends BaseDaoImpl implements SchemaCreator {

	@Autowired
	private Connector connector;

	private int replicationFactor;
	private boolean rebuildDB;

	public SchemaCreatorImpl(int replicationFactor, boolean rebuildDB) {
		this.replicationFactor = replicationFactor;
		this.rebuildDB = rebuildDB;
	}

	@PostConstruct
	public void init() {
		if (rebuildDB) {
			dropKeyspace();
			createKeyspace();
			createTables();
		}
	}

	public void createKeyspace() {
		try {
			queryForNoKeyspace("CREATE KEYSPACE "
					+ connector.getKeyspace()
					+ " WITH replication = {'class':'SimpleStrategy', 'replication_factor': "
					+ replicationFactor + "};");
		} catch (InvalidQueryException e) {
			System.out.println(e.getLocalizedMessage() + "\n");
		}
	}

	public void dropKeyspace() {
		try {
			queryForNoKeyspace("DROP KEYSPACE " + connector.getKeyspace() + ";");
		} catch (InvalidQueryException e) {
			System.out.println(e.getLocalizedMessage() + "\n");
		}

	}

	public void createTables() {
		try {
			query(UserDaoImpl.CREATE_TABLE_SCRIPT);
			query(FriendDaoImpl.CREATE_TABLE_SCRIPT);
			query(FollowerDaoImpl.CREATE_TABLE_SCRIPT);
			query(TweetDaoImpl.CREATE_TABLE_SCRIPT);
			query(UserlineDaoImpl.CREATE_TABLE_SCRIPT);
			query(TimelineDaoImpl.CREATE_TABLE_SCRIPT);
		} catch (InvalidQueryException e) {
			System.out.println(e.getLocalizedMessage() + "\n");
		}
	}

}
