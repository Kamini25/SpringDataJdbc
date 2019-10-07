import com.springboot.SpringBootApp;
import com.springboot.entity.TransactionEntity;
import com.springboot.entity.WalletEntity;
import com.springboot.model.Customer;
import com.springboot.services.CustomerService;
import com.springboot.services.HistoryService;
import com.springboot.services.WalletService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootApp.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private HistoryService historyService;

    long user1=9990;
    long user2 = 8808;

    @Test
    public  void testACreateUser() throws Exception {
        Customer user = new Customer();

        user.setPhoneNo((long)user1);
        user.setCustName("paytm1");
        user.setPassword("paytm333");
        user.setUserType("user");

        Customer res = customerService.addCustomer(user);
       // System.out.println("result is " + res);
       // assert(res.contains("Successfully created user"));
    }

    @Test
    public void testBCreateWallet() throws Exception {
       // Wallet wallet = new Wallet();
        //wallet.se((long)123);
        WalletEntity res = walletService.createUserWallet((long)user1);
        assert(res.getPhoneNo()==user1);
        //Thread.sleep(1000);
    }

    @Test
    public void testCAddMoney() throws Exception{

        WalletEntity res = walletService.addMoneyToUserWallet((long)user1,10,(long)321);
        assert(res.getPhoneNo()==user1);
        //Thread.sleep(1000);
    }

    @Test
    public void testDSendMoney() throws Exception {     Customer user = new Customer();

        user.setPhoneNo((long)user2);
        user.setCustName("paytm1");
        user.setPassword("paytm333");
        user.setUserType("user");
        customerService.addCustomer(user);
        Thread.sleep(1000);

       walletService.createUserWallet((long)user2);
       walletService.sendMoneyToReceiver((long)user1 , 10, (long)user2);



    }

    @Test
    public void testEGetDetails() throws Exception {
        Set<TransactionEntity> res = historyService.getPassbookDetails((long)user2);
        //  System.out.println("Size1 " + res.size());
        assert(res.size()==1);
        Set<TransactionEntity> res1 = historyService.getPassbookDetails((long)user1);
        //  System.out.println("Size2 " + res1.size());
        assert(res1.size()==2);


    }
}

