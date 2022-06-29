package com.autohandel;

import java.util.ArrayList;
import java.util.List;

public class AutoHandel {
    List<Car> cars;
    long cash;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public double getCash() {
        return cash/100.0;
    }

    public void setCash(double cash) {
        this.cash = Math.round(cash*100);
    }

    public AutoHandel() {
        this.cars = new ArrayList<>();
    }
}
