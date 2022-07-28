package com.autohandel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarDb {
    List<Car> carsForSale;
    Random random = new Random();
    private static List<String> brands = List.of("Audi", "Mercedes", "Fiat", "Porsche", "Volkswagen", "BMW", "Citroen", "Opel", "Peugeot", "Dacia", "Hyundai", "Mazda");
    List<String> colors = List.of("blue", "red", "green", "white", "yellow", "black", "brown", "pink");

    public CarDb() {
        this.carsForSale = generateCarsForSale();
    }

    public static List<String> getBrands() {
        return brands;
    }

    public static void setBrands(List<String> brands) {
        CarDb.brands = brands;
    }

    public Car generateBudgetCar() {
        return new Car(
                random.nextLong(500, 2000),
                getBrands().get(random.nextInt(getBrands().size())),
                random.nextLong(999000),
                colors.get(random.nextInt(colors.size())),
                "budget",
                0,
                random.nextInt(100)<10,  //10% szans że popsute
                random.nextInt(100)<30,
                random.nextInt(100)<10,
                random.nextInt(100)<20,
                random.nextInt(100)<15
        );
    }

    public Car generateStandardCar() {
        return new Car(
                random.nextLong(5000, 20000),
                getBrands().get(random.nextInt(getBrands().size())),
                random.nextLong(799000),
                colors.get(random.nextInt(colors.size())),
                "standard",
                0,
                random.nextInt(100)<7,
                random.nextInt(100)<20,
                random.nextInt(100)<7,
                random.nextInt(100)<10,
                random.nextInt(100)<10
        );
    }

    public Car generatePremiumCar() {
        return new Car(
                random.nextLong(50000, 200000),
                getBrands().get(random.nextInt(getBrands().size())),
                random.nextLong(399000),
                colors.get(random.nextInt(colors.size())),
                "premium",
                0,
                random.nextInt(100)<2,
                random.nextInt(100)<5,
                random.nextInt(100)<2,
                random.nextInt(100)<10,
                random.nextInt(100)<5
        );
    }

    public Car generateUtilityCar() {
        return new Car(
                random.nextLong(1000, 100000),
                getBrands().get(random.nextInt(getBrands().size())),
                random.nextLong(8799000),
                colors.get(random.nextInt(colors.size())),
                "utility",
                random.nextLong(100, 500),
                random.nextInt(100)<20,
                random.nextInt(100)<80,
                random.nextInt(100)<50,
                random.nextInt(100)<50,
                random.nextInt(100)<20
        );
    }

    private List<Car> generateCarsForSale() {
        List<Car> result = new ArrayList<>();
        List<String> brands = List.of("Audi", "Mercedes", "Fiat", "Porsche", "Volkswagen", "BMW", "Citroen", "Opel", "Peugeot", "Dacia", "Hyundai", "Mazda");
        List<String> colors = List.of("blue", "red", "green", "white", "yellow", "black", "brown", "pink");
        Random random = new Random();


        //generate budget cars
        for (int i = 0; i < 5; i++) {
            result.add(generateBudgetCar());
        }

        //generate standard cars

        for (int i = 0; i < 5; i++) {
            result.add(generateStandardCar());
        }

        //generate premium cars
        for (int i = 0; i < 5; i++) {
            result.add(generatePremiumCar());
        }
        //generate utility cars
        for (int i = 0; i < 5; i++) {
            result.add(generateUtilityCar());
        }
        return result;
    }

    public List<Car> getCarsForSale() {
        return carsForSale;
    }

}

