package com.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.springboot.webflux.app.model.dao.ProductDAO;
import com.springboot.webflux.app.model.entity.Product;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {
	
	private final static Logger logger = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		reactiveMongoTemplate.dropCollection(Product.class).subscribe();
		
		Flux.just(
			new Product("Product 1", 100.00),
			new Product("Product 2", 150.25),
			new Product("Product 3", 33.50),
			new Product("Product 4", 67.00),
			new Product("Product 5", 138.90),
			new Product("Product 6", 120.00),
			new Product("Product 7", 25.50),
			new Product("Product 8", 99.99),
			new Product("Product 9", 44.99),
			new Product("Product 0", 10.25)
		)
		.flatMap(
			product -> {
				product.setCreateAt(new Date());
				return productDAO.save(product);
			}
		)
		.subscribe(
			product -> {
				logger.info("Inserted " + product);
			}
		);
	}
}
