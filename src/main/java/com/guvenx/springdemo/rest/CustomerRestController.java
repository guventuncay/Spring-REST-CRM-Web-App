package com.guvenx.springdemo.rest;

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

import com.guvenx.springdemo.entity.Customer;
import com.guvenx.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// autowire the CustomerService
	@Autowired
	private CustomerService customerService;

	// add mapping for GET /customers
	@GetMapping("/customers")
	public List<Customer> getCustomers() {

		return this.customerService.getCustomers();
	}

	// add mapping for GET /customer/{id}
	@GetMapping("/customers/{customer_id}")
	public Customer getCustomer(@PathVariable int customer_id) {

		if (this.customerService.getCustomer(customer_id) == null)
			throw new CustomerNotFoundException("Customer id not found - " + customer_id);

		return this.customerService.getCustomer(customer_id);
	}

	// add mapping for POST /customers - add new customer
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		// also cust in case the pass an id in JSON, set id to 0
		// this is force a save of new item, instead of update
		customer.setId(0);

		this.customerService.saveCustomer(customer);

		return customer;
	}

	// add mapping for PUT /customers - update customer
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		// we already have id

		this.customerService.saveCustomer(customer);

		return customer;
	}

	// add mapping for DELETE /customers - delete customer
	@DeleteMapping("/customers/{customer_id}")
	public String deleteCustomer(@RequestBody int customer_id) {

		// throw exception if customer not found
		
		if (this.customerService.getCustomer(customer_id) == null)
			throw new CustomerNotFoundException("Customer id not found - " + customer_id);

		this.customerService.deleteCustomer(customer_id);

		return "Deleted customer, id: " + customer_id;
	}
}
