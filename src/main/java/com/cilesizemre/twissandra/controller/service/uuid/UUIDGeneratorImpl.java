package com.cilesizemre.twissandra.controller.service.uuid;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGeneratorImpl implements UUIDGenerator {

	public UUID generate() {
		return UUIDs.random();
	}

	public UUID generateTimeBased() {
		return UUIDs.timeBased();
	}

}
