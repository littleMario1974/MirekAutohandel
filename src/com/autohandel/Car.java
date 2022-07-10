package com.autohandel;

import java.text.MessageFormat;

public class Car {
    //properties
    long value;
    String brand;
    long mileage;
    String color;
    String segment;
    long capacity;

    //maintainable
    boolean brakes;
    boolean suspension;
    boolean engine;
    boolean body;
    boolean gearbox;

    @Override
    public String toString() {
        /*return String.format("%s: [Cena: %s, Kolor: %s, Przebieg: %s, Segment: %s, Ładowność: %s]. Zepsute: [Hamulce: %s, Zawieszenie: %s, Silnik: %s, Karoseria: %s, Skrzynia biegów: %s]",
                getBrand(),getColor(),getMileage(),getSegment(),getCapacity(),isBrakes(),isSuspension(),isEngine(),isBody(),isGearbox());*/
        return MessageFormat.format("{0}:\t\t\t[Cena: {1}, Kolor: {2}, Przebieg: {3}, Segment: {4}, Ładowność: {5}]. Zepsute: [Hamulce: {6}, Zawieszenie: {7}, Silnik: {8}, Karoseria: {9}, Skrzynia biegów: {10}]",
                getBrand(),getValue(),getColor(),getMileage(),getSegment(),getCapacity(), isBrakesBroken(), isSuspensionBroken(), isEngineBroken(), isBodyBroken(), isGearboxBroken());
    }


    //maintainable
    boolean brakesBroken;
    boolean suspensionBroken;
    boolean engineBroken;
    boolean bodyBroken;
    boolean gearboxBroken;


    public Car() {

    }

    public double getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public boolean isBrakesBroken() {
        return brakesBroken;
    }

    public void setBrakesBroken(boolean brakesBroken) {
        this.brakesBroken = brakesBroken;
    }

    public boolean isSuspensionBroken() {
        return suspensionBroken;
    }

    public void setSuspensionBroken(boolean suspensionBroken) {
        this.suspensionBroken = suspensionBroken;
    }

    public boolean isEngineBroken() {
        return engineBroken;
    }

    public void setEngineBroken(boolean engineBroken) {
        this.engineBroken = engineBroken;
    }

    public boolean isBodyBroken() {
        return bodyBroken;
    }

    public void setBodyBroken(boolean bodyBroken) {
        this.bodyBroken = bodyBroken;
    }

    public boolean isGearboxBroken() {
        return gearboxBroken;
    }

    public void setGearboxBroken(boolean gearboxBroken) {
        this.gearboxBroken = gearboxBroken;
    }

    public Car(long value, String brand, long mileage, String color, String segment, long capacity, boolean brakesBroken, boolean suspensionBroken, boolean engineBroken, boolean bodyBroken, boolean gearboxBroken) {
        this.value = value;
        this.brand = brand;
        this.mileage = mileage;
        this.color = color;
        this.segment = segment;
        this.capacity = capacity;
        this.brakesBroken = brakesBroken;
        this.suspensionBroken = suspensionBroken;
        this.engineBroken = engineBroken;
        this.bodyBroken = bodyBroken;
        this.gearboxBroken = gearboxBroken;
    }
}
