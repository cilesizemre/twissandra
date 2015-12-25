package com.cilesizemre.twissandra.controller.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;

public class Connector {

	private String node;
	private int port;
	private String keyspace;

	private Cluster cluster;
	private Session session;

	public Connector(final String node, final int port, final String keyspace) {
		this.node = node;
		this.port = port;
		this.keyspace = keyspace;
		connect(node, port, keyspace);
	}

	public void connect(final String node, final int port, final String keyspace) {
		cluster = Cluster.builder().addContactPoint(node).withPort(port)
				.withPoolingOptions(getPoolingOptions()).build();
		this.keyspace = keyspace;
	}

	public void disconnect() {
		cluster.close();
	}

	// http://www.datastax.com/dev/blog/4-simple-rules-when-using-the-datastax-drivers-for-cassandra
	public Session getSession() {
		if (session == null || session.isClosed()) {
			session = cluster.connect(keyspace);
		}
		return session;
	}

	public Session getNoKeyspaceSession() {
		return cluster.connect();
	}

	public String getKeyspace() {
		return keyspace;
	}

	private PoolingOptions getPoolingOptions() {
		final PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setPoolTimeoutMillis(10000); // default: 5000
		poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 100); // default:2
		poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, 32768); // default:8
		poolingOptions.setCoreConnectionsPerHost(HostDistance.REMOTE, 10); // default:1
		poolingOptions.setMaxConnectionsPerHost(HostDistance.REMOTE, 20); // default:2
		return poolingOptions;
	}

}
