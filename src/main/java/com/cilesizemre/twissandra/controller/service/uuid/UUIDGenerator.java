package com.cilesizemre.twissandra.controller.service.uuid;

import java.util.UUID;

public interface UUIDGenerator {
	
	UUID generate();
	
	UUID generateTimeBased();
	
}
