package com.springboot.services;

import com.springboot.SpringBootApp;
import com.springboot.model.Customer;
import com.springboot.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    CustomerRepository customerRepository;
    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer addCustomer(Customer customer) throws Exception {
        logger.info("Inside add customer function");
        Customer user = findByPhoneNo(customer.getPhoneNo());
        if(user==null) {
                logger.info("No user exist with this number, can create a new one");
              customerRepository.save(customer);
              return customer;

        }
        else {
            logger.info("user exists");
            throw new Exception(String.format("User already exists with phoneNo %s", customer.getPhoneNo()));
        }

    }
    public Customer findByPhoneNo(Long phoneNo) throws Exception {
        logger.info("In function searchByPhoneNo");
        if(phoneNo==null){
            throw new Exception("Phone number cannot be null.");
        }
       Customer user = customerRepository.findByPhoneNo(phoneNo);
       // logger.info("user is " + user);
        return user;
    }
   }
