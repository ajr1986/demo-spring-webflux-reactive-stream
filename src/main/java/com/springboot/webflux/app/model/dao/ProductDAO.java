package com.springboot.webflux.app.model.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.webflux.app.model.entity.Product;

public interface ProductDAO extends ReactiveMongoRepository<Product, Long> {

}
