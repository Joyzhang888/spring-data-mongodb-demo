package spring.mongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import spring.mongodb.domain.Hotel;
import spring.mongodb.domain.QHotel;
import spring.mongodb.repository.HotelRepository;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelRepository repository;

	@GetMapping("/all")
	public List<Hotel> getAllHotel(){
		List<Hotel> hotels = repository.findAll();
		return hotels;
	}
	
	@PutMapping
	public void addHotel(@RequestBody Hotel hotel) {
		repository.insert(hotel);
	}
	
	@PostMapping
	public void updateHotel(@RequestBody Hotel hotel) {
		repository.save(hotel);
	}
	
	@DeleteMapping("/{id}")
	public void deleteHotel(@PathVariable("id") String id) {
		repository.delete(id);
	}
	
	@GetMapping("/{id}")
	public Hotel findById(@PathVariable("id") String id) {
		return repository.findById(id);
	}
	
	@GetMapping("/price/{maxPrice}")
	public List<Hotel> findByPricePerNight(@PathVariable("maxPrice") int price){
		return repository.findByPricePerNightLessThan(price);
	}
	
	/**
	 * Get all the Hotels in a city
	 * @param city
	 * @return
	 */
	@GetMapping("/address/{city}")
	public List<Hotel> findByHotelsByCity(@PathVariable("city") String city){
		return repository.findByCity(city);
	}
	

	/**
	 * Use query language to get all the Hotels in a country
	 * @param country
	 * @return
	 */
	@GetMapping("/address/country/{country}")
	public List<Hotel> findByHotelsByCountry(@PathVariable("country") String country){
		// create a query class (QHotel)
        QHotel qHotel = new QHotel("hotel");

        // using the query class we can create the filters
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);

        // we can then pass the filters to the findAll() method
        List<Hotel> hotels = (List<Hotel>) repository.findAll(filterByCountry);

        return hotels;
	}
	
	/**
	 * Find all the Hotels with maximum priceperNight is 150 and having atleast one rating greater than 5
	 * @return
	 */
	@GetMapping("/recommended")
    public List<Hotel> getRecommended(){
        final int maxPrice = 150;
        final int minRating = 5;

        // create a query class (QHotel)
        QHotel qHotel = new QHotel("hotel");

        // using the query class we can create the filters
        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);

        // we can then pass the filters to the findAll() method
        List<Hotel> hotels = (List<Hotel>) repository.findAll(filterByPrice.and(filterByRating));

        return hotels;
    }

}
