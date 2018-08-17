package com.springboot.webflux.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webflux.app.model.dao.ProductDAO;
import com.springboot.webflux.app.model.entity.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/products")
public class ProductRestController {

	@Autowired
	private ProductDAO productDAO;
	
	@GetMapping()
	public Flux<Product> index(){
		
		return productDAO.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Product> getProduct(@PathVariable(name = "id") Long id){
		
		return productDAO.findById(id);
	}
	
}
