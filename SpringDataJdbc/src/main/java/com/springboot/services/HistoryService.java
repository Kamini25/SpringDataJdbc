package com.springboot.services;

import com.springboot.entity.TransactionEntity;
import com.springboot.kafka.Producer;
import com.springboot.model.Customer;
import com.springboot.model.History;
import com.springboot.model.Wallet;
import com.springboot.repository.CustomerRepository;
import com.springboot.repository.HistoryRepository;
import com.springboot.repository.WalletRepository;

import org.joda.time.DateTimeComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.springboot.utils.*;
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class HistoryService {

    HistoryRepository historyRepository;
    WalletRepository walletRepository;
    CustomerRepository customerRepository;
    Producer producer;

    /*@Autowired
    Client client;
*/
    public HistoryService(HistoryRepository historyRepository, WalletRepository walletRepository, CustomerRepository customerRepository, Producer producer) {
        this.historyRepository = historyRepository;
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.producer = producer;
    }

    public Set<TransactionEntity> getPassbookDetails(Long phoneNo) throws Exception {
        if(phoneNo==null){
            throw new Exception("Number cannot be null");
        }
        Customer user = customerRepository.findByPhoneNo(phoneNo);
        if(user==null){
            throw new Exception(String.format("User %s is not registered.",phoneNo));
        }
        if(user.getWallet()==null){
            throw new Exception(String.format("No wallet associated with user %s", phoneNo));
        }
        try {
            //Set<History> transactions = new HashSet<>();
            Set<History> toList = historyRepository.findByToPhoneNo(phoneNo);
            Set<History> fromList = historyRepository.findByFromPhoneNo(phoneNo);
            Set<TransactionEntity> transList = new HashSet<>();
            for(History history: toList){
                String action = "";
                if(history.getaction().equals("SEND_MONEY")){
                    action = "RECEIVED_MONEY";
                }
                else{
                    action = history.getaction();
                }
                TransactionEntity te = new TransactionEntity(history.gettoPhoneNo(), history.getFromPhoneNo(),history.getAmount(),history.getCreateDateTime(),history.getTransactionStatus(),action);
                transList.add(te);
            }
            for(History history: fromList){
                TransactionEntity te = new TransactionEntity(history.gettoPhoneNo(), history.getFromPhoneNo(),history.getAmount(),history.getCreateDateTime(),history.getTransactionStatus(),history.getaction());
                transList.add(te);
            }

            ArrayList<TransactionEntity> al = new ArrayList<TransactionEntity>(transList);
            Collections.sort(al, new DateSorter());
            Set sortedSet = new LinkedHashSet(al);

            return sortedSet;

        }
        catch (Exception e){
            throw e;
        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public void transferAmount(Customer toUser, double amount, Customer fromUser, String action) throws Exception {


        History t = initializeTransaction(toUser,amount,fromUser,action);
        try {

            Wallet fromWallet = fromUser.getWallet();
            fromWallet.setWalletAmount(fromWallet.getWalletAmount() - amount);
            walletRepository.save(fromWallet);

            double transferredAmount = amount;
            /*
             * Paytm deducts 0.025% as service fee.
             * */
            if (toUser.getUserType().equals("merchant")) {
                transferredAmount = amount - (0.025 * amount);
            }
            Wallet towallet = toUser.getWallet();
            towallet.setWalletAmount(toUser.getWallet().getWalletAmount() + transferredAmount);
            walletRepository.save(towallet);

            t.setTransactionStatus("SUCCESS");

            producer.sendMessage(t);

        }
        catch(Exception e){
            t.setTransactionStatus("FAILED")    ;
            producer.sendMessage(t);
            throw new Exception(String.format("Transaction failed due to %s", e.getMessage()));
        }
    }
    public History initializeTransaction(Customer toUser, Double amount, Customer fromUser, String action){
        long millis=System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = ldt.format(format);

        long x = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

        History t = new History(x,toUser.getPhoneNo(),fromUser.getPhoneNo(),amount, formatDateTime,"INITIALIZED",action);
        return t;
    }


}
