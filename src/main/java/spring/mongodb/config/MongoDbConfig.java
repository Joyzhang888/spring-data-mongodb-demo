package spring.mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import spring.mongodb.repository.HotelRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = HotelRepository.class)
public class MongoDbConfig {

}
