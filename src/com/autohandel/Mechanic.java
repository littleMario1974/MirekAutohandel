package com.autohandel;

public class Mechanic {
    String name;
    long nonRepairProbability;

    public Mechanic(String name, long nonRepairProbability, long breakingAnotherElementProbability) {
        this.name = name;
        this.nonRepairProbability = nonRepairProbability;
        this.breakingAnotherElementProbability = breakingAnotherElementProbability;
    }

    long breakingAnotherElementProbability;

}
