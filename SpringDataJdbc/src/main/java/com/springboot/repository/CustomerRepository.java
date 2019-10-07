package com.springboot.repository;

import com.springboot.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByPhoneNo(Long phoneNo);
}
