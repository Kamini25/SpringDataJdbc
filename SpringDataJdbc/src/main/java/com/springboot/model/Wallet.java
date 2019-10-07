package com.springboot.model;



import javax.persistence.*;

@Entity
@Table(name="Wallet")
public class Wallet {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   // private Long phoneNo;
    private Double walletAmount;
  //  private Long receiverPhoneNo;

    public Double getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Double walletAmount) {
        this.walletAmount = walletAmount;
    }

  /*  public Long getPhoneNo() {
        return phoneNo;
    }
*/
//    public Long getReceiverPhoneNo() {
//        return receiverPhoneNo;
//    }
//
//    public void setReceiverPhoneNo(Long receiverPhoneNo) {
//        this.receiverPhoneNo = receiverPhoneNo;
//    }

   /* public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }*/

}
