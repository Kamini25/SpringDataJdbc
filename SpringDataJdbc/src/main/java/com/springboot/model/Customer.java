package com.springboot.model;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @Column(name="phone_no")

    private Long phoneNo;

  //  @Column(name="cust_name")
    private String custName;

    private String password;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // @Column(name="is_merchant")
    private String userType;
    @OneToOne
    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Customer() {

        }
   /* public Customer(Long phoneNo, String custName, String password, Boolean isMerchant){
            this.phoneNo=phoneNo;
            this.custName=custName;
            this.password=password;
            this.isMerchant=isMerchant;
    }*/

  /*  public Customer() {

    }*/
//    @OneToOne
//    private Wallet wallet;

  /*  public Boolean getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(Boolean isMerchant) {
        this.isMerchant = isMerchant;
    }
*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", phoneNo=" + phoneNo +
                ", custName='" + custName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
