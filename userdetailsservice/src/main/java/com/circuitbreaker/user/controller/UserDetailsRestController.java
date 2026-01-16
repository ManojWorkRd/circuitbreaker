package com.circuitbreaker.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.circuitbreaker.user.dto.MyOrderDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;



@RestController
@RequestMapping("/user-service")
public class UserDetailsRestController {
	
		@Autowired
	    @Lazy
	    private RestTemplate restTemplate;

	    public static final String USER_SERVICE="userDetailsService";

	    private static final String BASEURL = "http://localhost:9191/orders";

	    private int attempt=1;


	    @GetMapping("/displayOrders")
	   @CircuitBreaker(name =USER_SERVICE,fallbackMethod = "getAllAvailableProducts")
	  //  @Retry(name = USER_SERVICE,fallbackMethod = "getAllAvailableProducts")
	    public List<MyOrderDTO> displayOrders(@RequestParam("category") String category) {
	        String url = category == null ? BASEURL : BASEURL + "/" + category;
	        System.out.println("retry method called "+attempt++ +" times "+" at "+new Date());
	        return restTemplate.getForObject(url, ArrayList.class);
	    }


	    public List<MyOrderDTO> getAllAvailableProducts(Exception e){
	        return Stream.of(
	                new MyOrderDTO(119, "Dummy Fallback LED TV", "electronics", "white", 45000)
	        ).collect(Collectors.toList());
	    }

	    

	    @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }

}
