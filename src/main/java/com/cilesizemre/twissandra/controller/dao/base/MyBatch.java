package com.cilesizemre.twissandra.controller.dao.base;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

import java.util.ArrayList;
import java.util.List;

public class MyBatch {

	private BatchStatement batch;
	private List<Statement> afterBatchWorks; // counter icin: counter'lar LOGGED batch ile calisamiyor!

	public MyBatch() {
		batch = new BatchStatement();
		afterBatchWorks = new ArrayList<Statement>(1);
	}

	public void add(Statement statement) {
		batch.add(statement);
	}

	public void execute(Session session) {
		session.execute(batch);
		for (Statement statement : afterBatchWorks) {
			session.execute(statement);
		}
	}

	public void addAfterBatchWork(Statement statement) {
		afterBatchWorks.add(statement);
	}
}
