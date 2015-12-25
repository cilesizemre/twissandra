package com.cilesizemre.twissandra.controller.dao.schemacreator;

public interface SchemaCreator {

	void createKeyspace();

	void dropKeyspace();

	void createTables();

}
