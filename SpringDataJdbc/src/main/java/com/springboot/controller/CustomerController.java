package com.springboot.controller;

import com.springboot.SpringBootApp;
import com.springboot.model.Customer;
import com.springboot.model.Wallet;
import com.springboot.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerService dao;

    public CustomerController(CustomerService dao) {
        this.dao = dao;
    }

    @RequestMapping(value="/createUser",method = RequestMethod.POST)
    public Customer storeCustomerInformation(@RequestBody Customer cust) throws Exception {
        logger.info("Inside store customer information function");
        if(cust.getPhoneNo()==null ||  cust.getCustName()==null || cust.getPassword()==null || cust.getUserType()==null){
            throw new Exception("Make sure phoneNo, custName, usertype or password should not be empty.");
        }
       /* if(cust.getIsMerchant()==null){
            cust.setIsMerchant(false);
        }*/
       String userType = cust.getUserType();
       System.out.println("usertype " + userType);
       if(!(userType.equals("bank") || userType.equals("merchant") || userType.equals("user")))
       {
           throw new Exception("UserType can be bank,merchant or user");
       }
        return dao.addCustomer(cust);
    }

   /* @PostMapping("/createWallet")
    public String createCustomerWallet(@RequestBody Wallet wallet) throws Exception {
        try {
            String res = dao.createWallet(wallet);
            return res;
        }
        catch(Exception e){
            throw e;
        }
    }

    @PostMapping("/addMoney")
    public String addMoneyToWallet(@RequestBody Wallet wallet) throws Exception{
        try {
            String res = dao.addMoney(wallet);
            return res;
        }
        catch(Exception e){
            throw e;
        }
    }

    @PostMapping("/sendMoney")
    public String sendMoneyToUser(@RequestBody Wallet wallet) throws Exception{
        try {
            String res = dao.sendMoney(wallet);
            return res;
        }
        catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value="/getPassbook/{phoneNo}",method = GET)
    @ResponseBody
    public List<String> getPassbookDetails(@PathVariable("phoneNo") long phoneNo) throws Exception{
        try {
            List<String> details = dao.getDetails(phoneNo);
            return details;
        }
        catch(Exception e){
            throw e;
        }
    }*/
}
