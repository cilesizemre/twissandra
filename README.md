# Documentation

This repository is implementation of Twissandra with Spring and Cassandra.

All you need is to configure application properties via applicationContext.xml (for Cassandra IP/port/keyspace name, replication factor, etc.) and then run with a application server/container like Tomcat. Twissandra automatically prepares Cassandra (creates a keyspace) and waits for tweets :).

All functional features serviced by this repository can be seen in <a href="https://github.com/cilesizemre/twissandra/blob/master/src/test/java/com/cilesizemre/twissandra/TestTwissandra.java" target="_blank">TestTwissandra</a>.

If you want to run this application locally, you can use Docker Container. (For creationg a Cassandra cluster with Docker Container locally, you can look over <a href="https://emrecilesiz.wordpress.com/2015/12/29/docker-ile-cassandra-cluster-kurulumu/" target="_blank">this</a> blog post.)

Besides Twissandra presents you REST services, you can access these services like below:<br/>
Add User: http://localhost/twissandra/user/add?username=user1&password=password1 <br/>
Follow User: http://localhost/twissandra/user/follow?username=user1&friend=user2 <br/>
Get Followings: http://localhost/twissandra/followings/get?username=user1 <br/>
Get Followers: http://localhost/twissandra/followers/get?username=user1 <br/>
Send Tweet: http://localhost/twissandra/tweet/send?username=user1&text=tweet1 <br/>
Get Timeline: http://localhost/twissandra/timeline/get?username=user1 <br/>
Get Userline: http://localhost/twissandra/userline/get?username=user1 <br/>

<b>Twissandra Data Modeling:</b>

CREATE TABLE users
  ( 
     username TEXT PRIMARY KEY, 
     password TEXT 
  ); 

CREATE TABLE tweets 
  ( 
     tweet_id UUID PRIMARY KEY, 
     username TEXT, 
     body     TEXT 
  ); 

CREATE TABLE friends
  ( 
     username TEXT, 
     friend   TEXT, 
     since    TIMESTAMP, 
     PRIMARY KEY (username, friend) 
  ); 

CREATE TABLE followers 
  ( 
     username TEXT, 
     follower TEXT, 
     since    TIMESTAMP, 
     PRIMARY KEY (username, follower) 
  ); 

CREATE TABLE userline 
  ( 
     username   TEXT, 
     time       TIMEUUID, 
     tweet_id   UUID, 
     tweet_body TEXT, 
     PRIMARY KEY (username, time) 
  ) WITH CLUSTERING ORDER BY (time DESC);

CREATE TABLE timeline
  ( 
     username   TEXT, 
     time       TIMEUUID, 
     tweet_id   UUID, 
     tweet_body TEXT, 
     PRIMARY KEY (username, time) 
  ) WITH CLUSTERING ORDER BY (time DESC);
