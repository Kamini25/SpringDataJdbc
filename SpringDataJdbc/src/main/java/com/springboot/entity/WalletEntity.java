package com.springboot.entity;

public class WalletEntity {
   // private long id;
    private long phoneNo;
    private double money;
    public WalletEntity(long phoneNo, double money) {
        this.phoneNo=phoneNo;
        this.money=money;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
