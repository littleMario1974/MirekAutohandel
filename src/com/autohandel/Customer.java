package com.autohandel;

import java.text.MessageFormat;

public class Customer {

    String name;
    long budget;
    String desiredSegment; //z jakiego segmentu auto chce kupić
    String desiredBrand1;
    String desiredBrand2;
    public boolean acceptsBroken = false;

    @Override
    public String toString() {
        /*return "Customer{" +
                "name='" + name + '\'' +
                ", budget=" + budget +
                ", desiredSegment='" + desiredSegment + '\'' +
                ", desiredBrand1='" + desiredBrand1 + '\'' +
                ", desiredBrand2='" + desiredBrand2 + '\'' +
                ", acceptsBroken=" + isAcceptsBroken() +
                '}';*/
        return MessageFormat.format("Nazwisko: {0}, Ilość gotówki: {1}, Preferowany segment: {2}, Preferowana 1 marka: {3}, Preferowana 2 marka: {4}, Czy akceptuje uszkodzone auto: {5}",
                getName(),getBudget(),getDesiredSegment(),getDesiredBrand1(),getDesiredBrand2(), isAcceptsBroken());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getDesiredSegment() {
        return desiredSegment;
    }

    public void setDesiredSegment(String desiredSegment) {
        this.desiredSegment = desiredSegment;
    }

    public String getDesiredBrand1() {
        return desiredBrand1;
    }

    public void setDesiredBrand1(String desiredBrand1) {
        this.desiredBrand1 = desiredBrand1;
    }

    public String getDesiredBrand2() {
        return desiredBrand2;
    }

    public void setDesiredBrand2(String desiredBrand2) {
        this.desiredBrand2 = desiredBrand2;
    }

    public boolean isAcceptsBroken() {
        return acceptsBroken;
    }

    public void setAcceptsBroken(boolean acceptsBroken) {
        this.acceptsBroken = acceptsBroken;
    }

    public Customer(String name, long budget, String desiredSegment, String desiredBrand1, String desiredBrand2, boolean acceptsBroken) {
        this.name = name;
        this.budget = budget;
        this.desiredSegment = desiredSegment;
        this.desiredBrand1 = desiredBrand1;
        this.desiredBrand2 = desiredBrand2;
        this.setAcceptsBroken(acceptsBroken);
    }
}
