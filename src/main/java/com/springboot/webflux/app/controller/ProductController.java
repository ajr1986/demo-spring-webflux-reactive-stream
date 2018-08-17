package com.springboot.webflux.app.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.springboot.webflux.app.model.dao.ProductDAO;
import com.springboot.webflux.app.model.entity.Product;

import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@Autowired
	private ProductDAO productDAO;

	@GetMapping(value = { "/", "/index" })
	public String index(Model model) {

		Flux<Product> products = productDAO.findAll();

		model.addAttribute("products", products);
		model.addAttribute("title", "Products");

		return "index";
	}

	@GetMapping(value = "/index-data-driver")
	public String indexDataDriver(Model model) {

		Flux<Product> products = productDAO.findAll().delayElements(Duration.ofSeconds(1)); // Simulate 1 second of delay by product

		model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 1)); // Reactive data driver mode to show ready products
		model.addAttribute("title", "Products");

		return "index";
	}
	
	@GetMapping(value = "index-full")
	public String indexFull(Model model) {

		Flux<Product> products = productDAO.findAll().repeat(1000);

		model.addAttribute("products", products);
		model.addAttribute("title", "Products");

		return "index";
	}
	
	@GetMapping(value = "index-chuncked")
	public String indexChuncked(Model model) {

		Flux<Product> products = productDAO.findAll().repeat(1000);

		model.addAttribute("products", products);
		model.addAttribute("title", "Products");

		return "index-chuncked";
	}

}
