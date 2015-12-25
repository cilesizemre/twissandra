package com.cilesizemre.twissandra.controller.dao.user;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.cilesizemre.twissandra.controller.dao.base.BaseDaoImpl;
import com.cilesizemre.twissandra.controller.dao.base.MyBatch;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;

@Repository
@DependsOn(value = "schemaCreator")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	public static final String CREATE_TABLE_SCRIPT = "CREATE TABLE users (username text PRIMARY KEY, password text);";

	private PreparedStatement addUserPreparedStatement;

	@PostConstruct
	public void init() {
		addUserPreparedStatement = getSession().prepare("INSERT INTO users (username, password) VALUES (?, ?);");
	}

	public void addUser(String username, String password, MyBatch batch) {
		batch.add(getAddUserBoundStatement(username, password));
	}

	private BoundStatement getAddUserBoundStatement(String username, String password) {
		return new BoundStatement(addUserPreparedStatement).bind(username, password);
	}

}
