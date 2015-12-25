package com.cilesizemre.twissandra.controller.dao.base;

import com.datastax.driver.core.Session;

public interface BaseDao {

	Session getSession();

	void query(String query);
}
