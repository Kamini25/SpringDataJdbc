package com.springboot.entity;

public class TransactionEntity {
    private Long payee;
    private Long payer;
    private Double amount;

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    private String transaction_date;

    public Long getPayee() {
        return payee;
    }

    public void setPayee(Long payee) {
        this.payee = payee;
    }

    public Long getPayer() {
        return payer;
    }

    public void setPayer(Long payer) {
        this.payer = payer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }



    public TransactionEntity(Long payee, Long payer, Double amount, String createDateTime, String transactionStatus, String action) {
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.transaction_date = createDateTime;
        this.status = transactionStatus;
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String status;
    private String action;
}
