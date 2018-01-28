package spring.mongodb.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import spring.mongodb.domain.Address;
import spring.mongodb.domain.Hotel;
import spring.mongodb.domain.Review;

@Component
public class DbSeeder implements CommandLineRunner{
	
	@Autowired
	private HotelRepository repository;

	/**
	 * Inserts the data into mondo db instance when application starts up
	 */
	@Override
	public void run(String... arg0) throws Exception {
		Hotel marriot = new Hotel("Marriot", 113, new Address("Paris", "France"), Arrays.asList(new Review("John", 2, false),
				new Review("Mary", 7, true)));
		Hotel ibis = new Hotel("IBIS", 100, new Address("Bucharest", "Romania"), Arrays.asList(new Review("Teddy", 2, false),
				new Review("Michael", 5, true)));
		Hotel novotel = new Hotel("Novotel", 200, new Address("Berlin", "Germany"), new ArrayList<>());
		
		//drop all Hotels
		this.repository.deleteAll();
		//add Hotels to database
		List<Hotel> hotels = Arrays.asList(marriot,ibis,novotel);
		this.repository.save(hotels);
	}
	


}
