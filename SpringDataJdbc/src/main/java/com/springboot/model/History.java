package com.springboot.model;

//import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.time.LocalDateTime;
//import org.hibernate.annotations.CreationTimestamp;
//@Entity
//@Table(name="transactions")
@Document(indexName = "history",type = "History")

public class History {
    @Id
    @Field(type = FieldType.String, store = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long toPhoneNo;
    private Long fromPhoneNo;
    private Double amount;
  //  @CreationTimestamp
    private String createDateTime;

    public Long gettoPhoneNo() {
        return toPhoneNo;
    }

    public void settoPhoneNo(Long toPhoneNo) {
        this.toPhoneNo = toPhoneNo;
    }

    public Long getFromPhoneNo() {
        return fromPhoneNo;
    }

    public void setFromPhoneNo(Long fromPhoneNo) {
        this.fromPhoneNo = fromPhoneNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    private String transactionStatus;



    public History(){}

    public History(long id, Long toPhoneNo, Long fromPhoneNo, Double amount, String currentDate, String transactionStatus, String action) {
        this.id = id;
        this.toPhoneNo = toPhoneNo;
        this.fromPhoneNo = fromPhoneNo;
        this.amount = amount;
        this.createDateTime = currentDate;
        this.transactionStatus = transactionStatus;
        this.action = action;
    }

    private String action;

    public String getaction() {
        return action;
    }

    public void setaction(String action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
