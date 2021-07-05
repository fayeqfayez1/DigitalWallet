package com.app.network.firebase.model;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class CreditCard implements Serializable {
    @PropertyName("cardHolderName ")
    private String cardHolderName = "";
    @PropertyName("cardNumber ")
    private String cardNumber = "";
    @PropertyName("cardType ")
    private Integer cardType ;
    @PropertyName("cvv ")
    private String cvv = "";
    @PropertyName("expiredDate ")
    private String expiredDate = "";

    public CreditCard(String cardHolderName, String cardNumber, Integer cardType, String cvv, String expiredDate) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cvv = cvv;
        this.expiredDate = expiredDate;
    }

    public CreditCard() {
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }


    @Override
    public String toString() {
        return "CreditCard{" + "cardType='" + cardType + '\'' +
                ",cardNumber='" + cardNumber + '\'' +
                ",cardHolderName='" + cardHolderName + '\'' +
                ",cvv='" + cvv + '\'' +
                ",expiredDate='" + expiredDate + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cardType", cardType);
        result.put("cardNumber", cardNumber);
        result.put("cardHolderName", cardHolderName);
        result.put("cvv", cvv);
        result.put("expiredDate", expiredDate);
        return result;
    }


}
