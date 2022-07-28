package com.autohandel;

public class Mechanic {
    String name;

    public String getName() {
        return name;
    }

    public long getNonRepairProbability() {
        return nonRepairProbability;
    }

    public long getBreakingAnotherElementProbability() {
        return breakingAnotherElementProbability;
    }

    public long getMargin() {
        return margin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNonRepairProbability(long nonRepairProbability) {
        this.nonRepairProbability = nonRepairProbability;
    }

    public void setBreakingAnotherElementProbability(long breakingAnotherElementProbability) {
        this.breakingAnotherElementProbability = breakingAnotherElementProbability;
    }

    public void setMargin(long margin) {
        this.margin = margin;
    }

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
