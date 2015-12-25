package com.cilesizemre.twissandra.controller.dao.batch;

import org.springframework.stereotype.Repository;

import com.cilesizemre.twissandra.controller.dao.base.BaseDaoImpl;
import com.cilesizemre.twissandra.controller.dao.base.MyBatch;

@Repository
public class BatchExecutorImpl extends BaseDaoImpl implements BatchExecutor {

	public void execute(MyBatch batch) {
		batch.execute(getSession());
	}

}
