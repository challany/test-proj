package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import spittr.config.Spitter;

public class MongoDBIDBImpl {
	@Autowired
	MongoOperations mongoOperations;
	
	long count = mongoOperations.getCollection("order").count();
	
}
