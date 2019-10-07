package com.springboot.controller;

import com.springboot.entity.WalletEntity;
import com.springboot.model.Wallet;
import com.springboot.services.CustomerService;
import com.springboot.services.WalletService;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {
WalletService walletService;
CustomerService customerService;

    public WalletController(WalletService walletService, CustomerService customerService) {
        this.walletService = walletService;
        this.customerService = customerService;
    }

    @RequestMapping(value="/createWallet",method = RequestMethod.POST)
    public WalletEntity createWallet(@RequestParam long phoneNo) throws Exception {
        try {
            System.out.println("Inside createWallet");

           WalletEntity walletResult= walletService.createUserWallet(phoneNo);
           return walletResult;

        }
        catch(Exception e){
            throw  e;
        }

    }

    @RequestMapping(value="/addMoney", method = RequestMethod.POST)
    public WalletEntity addMoney(@RequestParam long toUser, @RequestParam double amount, @RequestParam long bankId) throws Exception{
       try{
           WalletEntity walletResult = walletService.addMoneyToUserWallet(toUser,amount,bankId);
           return walletResult;
       }
       catch(Exception e){
           throw e;
       }
    }

    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    public WalletEntity sendMoney(@RequestParam long fromUser, @RequestParam double amount, @RequestParam long toUser) throws Exception {
        try{
            WalletEntity walletResult = walletService.sendMoneyToReceiver(fromUser,amount,toUser);
            return walletResult;
        }
        catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value  ="/getBalance/{phoneNo}", method = RequestMethod.GET)
    public WalletEntity getBalance(@PathVariable("phoneNo") long phoneNo) throws Exception {
        try {
           return walletService.getUserBalance(phoneNo);
        }
        catch (Exception e){
            throw e;
        }
    }
}
