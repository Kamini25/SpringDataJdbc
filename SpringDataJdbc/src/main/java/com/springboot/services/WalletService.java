package com.springboot.services;

import com.springboot.entity.WalletEntity;
import com.springboot.model.Customer;
import com.springboot.model.History;
import com.springboot.model.Wallet;
import com.springboot.repository.CustomerRepository;
import com.springboot.repository.HistoryRepository;
import com.springboot.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {
    WalletRepository walletRepository;
    CustomerRepository customerRepository;
    HistoryService historyService;

   /* public WalletService(WalletRepository walletRepository, CustomerRepository customerRepository) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
    }*/

    public WalletService(WalletRepository walletRepository, CustomerRepository customerRepository, HistoryService historyService) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.historyService = historyService;
    }

    Logger logger = LoggerFactory.getLogger(WalletService.class);
    public WalletEntity createUserWallet(long phoneNo) throws Exception {
        //check validation later
       /* if(phoneNo){
            throw new Exception("Phone number cannot be null");
        }
       */
       System.out.println("createuserwallet function");
      Customer user =  customerRepository.findByPhoneNo(phoneNo);
      if(user==null){
          throw new Exception(String.format("User with phoneNo %s is not a registered user ",phoneNo));
      }
      if(user.getWallet()!=null){
          throw new Exception(String.format("Wallet already associated with phoneNo %s ", phoneNo));
      }
      else{
          System.out.println("go to create");
          Wallet wallet = new Wallet();
          wallet.setWalletAmount((double)0);
          user.setWallet(wallet);
          System.out.println("user is " + user.getWallet() + " " + user.getPhoneNo());
          walletRepository.save(wallet);
          customerRepository.save(user);

          return new WalletEntity(user.getPhoneNo(),wallet.getWalletAmount());
      }
    }

    public WalletEntity addMoneyToUserWallet(long userPhoneNo, double money, long bankPhoneNo) throws Exception{
        /*
        * check from is a bank and has wallet associated
        * user is valid with a wallet associated
        * amount is less than max and less than value in bank and should be >0.
        * */
        if(money<=0){
            throw new Exception("Enter value greater than 0.");
        }
        Customer toUser = customerRepository.findByPhoneNo(userPhoneNo);
        if(toUser==null){
            throw new Exception(String.format("User with phoneNo %s is not registered.", userPhoneNo));
        }
        if(toUser.getWallet()==null){
            throw new Exception(String.format("Wallet not associated with user %s ",userPhoneNo));
        }
        Customer bankUser = customerRepository.findByPhoneNo(bankPhoneNo);

        if (bankUser == null) {
                throw new Exception(String.format("Bank with phoneNo %s is not registered.", bankUser));
        }
        if (!(bankUser.getUserType().equals("bank"))) {
                throw new Exception(String.format("%s is not a proper bank Id", bankPhoneNo));
        }
        if (bankUser.getWallet() == null) {
                throw new Exception(String.format("Wallet not associated with Bank %s ", bankPhoneNo));
        }
        double amountInBank = bankUser.getWallet().getWalletAmount();
        if (money > amountInBank) {
                throw new Exception(String.format("Insufficient balance in bank. Balance in bank is %s", amountInBank));
        }

            /*
            * deduct from bank and credit in user account
            * */
         try {

                System.out.println("TESTSSSSS in walletService");
                historyService.transferAmount(toUser, money, bankUser,"ADD_MONEY");
               /* String addMoneyMsg=String.format("Added amount %s",money);
                updatePassbook(addMoneyMsg,userPhoneNo);*/
                return new WalletEntity(toUser.getPhoneNo(),money);
         }
         catch(Exception e)
         {
                throw e;
         }

    }

    public WalletEntity sendMoneyToReceiver(Long fromUserNo, double amount, Long toUserNo) throws Exception {
        if(amount<=0){
            throw new Exception("Enter value greater than 0.");
        }
        if((fromUserNo==null)|| (toUserNo==null)){
            throw new Exception("Please make sure that neither of sender or receiver number is null.");
        }
        Customer toUser = customerRepository.findByPhoneNo(toUserNo);
        if(toUser==null){
            throw new Exception(String.format("User with phoneNo %s is not registered.", toUserNo));
        }
        if(toUser.getWallet()==null){
            throw new Exception(String.format("Wallet not associated with user %s ",toUserNo));
        }
        if(toUser.getUserType().equals("bank")){
            throw new Exception(String.format("Cannot transfer amount to bank"));
        }

        Customer fromUser = customerRepository.findByPhoneNo(fromUserNo);

        if (fromUser == null) {
            throw new Exception(String.format("User with phoneNo %s is not registered.", fromUserNo));
        }

        if (fromUser.getWallet() == null) {
            throw new Exception(String.format("Wallet not associated with Bank %s ", fromUserNo));
        }
        double amountInBank = fromUser.getWallet().getWalletAmount();
        if (amount > amountInBank) {
            throw new Exception(String.format("Insufficient balance in sender's account %s. Balance in account is %s", fromUserNo, amountInBank));
        }
        if(fromUser.getUserType().equals("bank")){
            throw new Exception(String.format("Sender %s cannot be bank",fromUser.getPhoneNo()));
        }

        if(toUserNo.equals(fromUserNo)){
            throw new Exception(String.format("Payer and payee cannot be same."));
        }
        try {
            historyService.transferAmount(toUser, amount, fromUser,"SEND_MONEY");
            return new WalletEntity(toUserNo,amount);
        }
        catch(Exception e){
            throw e;
        }

    }

    public WalletEntity getUserBalance(long phoneNo) throws Exception {
        Customer user = customerRepository.findByPhoneNo(phoneNo);
        if(user==null){
            throw new Exception(String.format("User %s is not registered.",phoneNo));
        }
        if(user.getWallet()==null){
            throw new Exception(String.format("User %s does not have a wallet",phoneNo));
        }
        WalletEntity wallet = new WalletEntity(phoneNo,user.getWallet().getWalletAmount());
        return wallet;
    }
}


