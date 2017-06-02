package spittr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories(basePackages = "order.db")
public class MongoConfig {
@Bean
public MongoFactoryBean mongo()
{
	
	MongoFactoryBean mongo = new MongoFactoryBean();
	mongo.setHost("localhost");
	return  mongo;
	
}

@Bean
public MongoOperations mongoTemplate(Mongo  mongo)
{
	
	return new MongoTemplate(mongo,"ordersDB");
}
}
