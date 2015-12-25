package com.cilesizemre.twissandra.controller.dao.batch;

import com.cilesizemre.twissandra.controller.dao.base.MyBatch;

public interface BatchExecutor {

	void execute(MyBatch batch);
}
