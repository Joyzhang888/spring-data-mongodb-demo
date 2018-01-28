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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import spring.mongodb.domain.Hotel;
import spring.mongodb.domain.QHotel;
import spring.mongodb.repository.HotelRepository;

@RestController
@RequestMapping("/hotels")
@Api(value="hotel", description="Rest end points for get all hotels, add hotel, update hotel, get hotel by Id, get hotel by city, get hotel by country, get hotel by minimum rating and maximum price per night")
public class HotelController {
	
	@Autowired
	private HotelRepository repository;

	@ApiOperation(value = "Get a list of all the hotels", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			 @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@GetMapping(produces = "application/json")
	public List<Hotel> getAllHotel(){
		List<Hotel> hotels = repository.findAll();
		return hotels;
	}
	
	@ApiOperation(value = "Update a hotel", response = Iterable.class)
	@PutMapping
	public void updateHotel(@RequestBody Hotel hotel) {
		repository.insert(hotel);
	}
	
	@ApiOperation(value = "Add a hotel", response = Iterable.class)
	@PostMapping
	public void addHotel(@RequestBody Hotel hotel) {
		repository.save(hotel);
	}
	
	@ApiOperation(value = "Delete a hotel", response = Iterable.class)
	@DeleteMapping("/{id}")
	public void deleteHotel(@PathVariable("id") String id) {
		repository.delete(id);
	}
	
	@ApiOperation(value = "Find a hotel by Id", response = Iterable.class)
	@GetMapping(value = "/{id}", produces = "application/json")
	public Hotel findById(@PathVariable("id") String id) {
		return repository.findById(id);
	}
	
	@ApiOperation(value = "Find all the hotels by given price less than the price per night")
	@GetMapping(value = "/price/{maxPrice}" , produces = "application/json")
	public List<Hotel> findByPricePerNight(@PathVariable("maxPrice") int price){
		return repository.findByPricePerNightLessThan(price);
	}
	
	/**
	 * Get all the Hotels in a city
	 * @param city
	 * @return
	 */
	@ApiOperation(value = "Find all the hotels by given city")
	@GetMapping(value = "/address/{city}", produces = "application/json")
	public List<Hotel> findByHotelsByCity(@PathVariable("city") String city){
		return repository.findByCity(city);
	}
	

	/**
	 * Use query language to get all the Hotels in a country
	 * @param country
	 * @return
	 */
	@ApiOperation(value = "Find all the hotels by given country")
	@GetMapping(value = "/address/country/{country}", produces = "application/json")
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
	@ApiOperation(value = "Find all the Hotels with maximum priceperNight is 150 and having atleast one rating greater than 5")
	@GetMapping(value = "/recommended", produces = "application/json")
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
