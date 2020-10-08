package com.azuka.currencyapp.model;

public class Currency {
    private String currency, value;

    public Currency() {
    }

    public Currency(String currency, String value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
