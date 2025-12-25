package com.wowfs.customermanagement.controller;

import com.wowfs.customermanagement.dto.CustomerRequest;
import com.wowfs.customermanagement.dto.CustomerResponse;
import com.wowfs.customermanagement.entity.Customer;
import com.wowfs.customermanagement.mapper.CustomerMapper;
import com.wowfs.customermanagement.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/customers")
public class CustomerController{

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //CUSTOMER CREATE
    @Operation(summary = "Register a new customer")
    @PostMapping("")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        //Convert DTO to Entity 
        Customer customer = CustomerMapper.toEntity(customerRequest);
        Customer savedCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.status(201).body(CustomerMapper.toDto(savedCustomer));
    }
        //ALL CUSTOMERS (WITH PAGINATION) 
    @Operation(summary = "Get all customers with pagination")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(Pageable pageable) {
        Page<Customer> page = customerService.getAllCustomers(pageable);
        List<CustomerResponse> dtoList = page.stream()
                .map(CustomerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
    //CUSTOMER BY ID
    @Operation(summary = "Get a customer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(CustomerMapper.toDto(customer));
    }

    //UPDATE CUSTOMER
    @Operation(summary = "Update a customer by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                          @Valid @RequestBody CustomerRequest customerRequest) {
        Customer updated = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(CustomerMapper.toDto(updated));
    }
     //DELETE CUSTOMER
    @Operation(summary = "Delete a customer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    //testing bulk data at once for convenience
    @PostMapping("/customers/bulk")
    public ResponseEntity<?> createCustomers(@Valid @RequestBody List<CustomerRequest> customers) {
    List<CustomerResponse> saved = customers.stream()
        .map(customerRequest -> {
            Customer customer = CustomerMapper.toEntity(customerRequest);
            Customer savedCustomer = customerService.registerCustomer(customer);
            return CustomerMapper.toDto(savedCustomer);
        })
        .collect(Collectors.toList());
    return ResponseEntity.ok(saved);
}
}