package com.cilesizemre.twissandra.controller.dao.base;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.cilesizemre.twissandra.controller.dao.Connector;
import com.cilesizemre.twissandra.controller.service.uuid.UUIDGenerator;
import com.datastax.driver.core.Session;

@Repository
@DependsOn(value = "schemaCreator")
public class BaseDaoImpl implements BaseDao {
	
	@Autowired
	private Connector connector;
	
	@Autowired
	private UUIDGenerator uuidGenerator;
	
	public Session getSession() {
		return connector.getSession();
	}
	
	public Session getNoKeyspaceSession() {
		return connector.getNoKeyspaceSession();
	}
	
	public void query(String query) {
		getSession().execute(query);
	}
	
	public void queryForNoKeyspace(String query) {
		getNoKeyspaceSession().execute(query);
	}
	
	public UUID generateUUID() {
		return uuidGenerator.generate();
	}
	
}
