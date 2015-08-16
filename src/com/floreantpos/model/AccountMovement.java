package com.floreantpos.model;
// Generated 15/08/2015 08:56:38 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * AccountMovement generated by hbm2java
 */
public class AccountMovement  implements java.io.Serializable {


     private Integer id;
     private int idAccount;
     private String type;
     private Date date;
     private double amount;

    public AccountMovement() {
    }

    public AccountMovement(int idAccount, String type, Date date, double amount) {
       this.idAccount = idAccount;
       this.type = type;
       this.date = date;
       this.amount = amount;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public int getIdAccount() {
        return this.idAccount;
    }
    
    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }




}


