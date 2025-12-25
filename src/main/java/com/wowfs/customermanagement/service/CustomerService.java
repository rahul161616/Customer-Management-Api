package com.wowfs.customermanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wowfs.customermanagement.dto.CustomerRequest;
import com.wowfs.customermanagement.entity.Customer;
import com.wowfs.customermanagement.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import java.util.Objects;
import com.wowfs.customermanagement.exception.CustomerNotFoundException;
import com.wowfs.customermanagement.exception.DuplicateEmailException;

@Service 
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    //Register Customer [Checks if email already Exists,Saves Customer to DB]
    @Transactional //Ensures atomicity
    public Customer registerCustomer(Customer customer) {
        customerRepository.findByEmail(customer.getEmail())
        .ifPresent(c->{
            throw new DuplicateEmailException(customer.getEmail());
        });
        return customerRepository.save(customer);
    }
    //Fetch all customers with Pagination
    public Page<Customer> getAllCustomers(Pageable pageable){
        if (pageable == null) {
            pageable = PageRequest.of(0, 10); //Default to first page with 10 records
        }
        return customerRepository.findAll(pageable);
    }
    //Customer by id 
      public Customer getCustomerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Customer id cannot be null");
        }
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    //Update Customer Details and Check for duplicate email
     @Transactional
    public Customer updateCustomer(Long id, CustomerRequest dto) {
        Customer existing = getCustomerById(id);

        //Check if new email is already taken by another customer
        customerRepository.findByEmail(dto.getEmail())
                .filter(c -> !Objects.equals(c.getId(), id))
                .ifPresent(c -> { 
                    throw new DuplicateEmailException(dto.getEmail()); 
                });

        //Update fields
        existing.setFullName(dto.getFullName());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());

        return customerRepository.save(existing);
    }
    //delete a customer by id 
    @Transactional
    public void deleteCustomer(Long id) {
        Customer existing = getCustomerById(id);
        if (existing != null) {
            customerRepository.delete(existing);
        }
    }
}
