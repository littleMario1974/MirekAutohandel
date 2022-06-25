package com.autohandel;

public class Mechanic {
    String name;
    long nonRepairProbability; //prawdopodobieństwo nie naprawienia
    long breakingAnotherElementProbability; //prawdopodobieństwo zepsucia innego elementu
    long margin; // marża zależna od wartości elementu i wartości naprawianego pojazdu

    public Mechanic(String name, long nonRepairProbability, long breakingAnotherElementProbability, long margin) {
        this.name = name;
        this.nonRepairProbability = nonRepairProbability;
        this.breakingAnotherElementProbability = breakingAnotherElementProbability;
        this.margin = margin;
    }
}
