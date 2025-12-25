package com.wowfs.customermanagement.mapper;

import com.wowfs.customermanagement.dto.CustomerRequest;
import com.wowfs.customermanagement.dto.CustomerResponse;
import com.wowfs.customermanagement.entity.Customer;

public class CustomerMapper {

    //DTO tp entity
    public static Customer toEntity(CustomerRequest dto) {
        return Customer.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .build();
    }

    //entity to DTO
    public static CustomerResponse toDto(Customer entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .age(entity.getAge())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}