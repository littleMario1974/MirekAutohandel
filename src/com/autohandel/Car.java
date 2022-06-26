package com.autohandel;

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
        return "Car{" +
                "wartość=" + value +
                ", marka='" + brand + '\'' +
                ", przebieg=" + mileage +
                ", kolor='" + color + '\'' +
                ", segment='" + segment + '\'' +
                ", przestrzeń ładunkowa=" + capacity +
                ", naprawa hamulców=" + brakes +
                ", naprawa zawieszenia=" + suspension +
                ", naprawa silnika=" + engine +
                ", naprawa karoserii=" + body +
                ", naprawa skrzyni biegów=" + gearbox +
                '}';
    }



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

    public boolean isBrakes() {
        return brakes;
    }

    public void setBrakes(boolean brakes) {
        this.brakes = brakes;
    }

    public boolean isSuspension() {
        return suspension;
    }

    public void setSuspension(boolean suspension) {
        this.suspension = suspension;
    }

    public boolean isEngine() {
        return engine;
    }

    public void setEngine(boolean engine) {
        this.engine = engine;
    }

    public boolean isBody() {
        return body;
    }

    public void setBody(boolean body) {
        this.body = body;
    }

    public boolean isGearbox() {
        return gearbox;
    }

    public void setGearbox(boolean gearbox) {
        this.gearbox = gearbox;
    }

    public Car(long value, String brand, long mileage, String color, String segment, long capacity, boolean brakes, boolean suspension, boolean engine, boolean body, boolean gearbox) {
        this.value = value;
        this.brand = brand;
        this.mileage = mileage;
        this.color = color;
        this.segment = segment;
        this.capacity = capacity;
        this.brakes = brakes;
        this.suspension = suspension;
        this.engine = engine;
        this.body = body;
        this.gearbox = gearbox;
    }
}
