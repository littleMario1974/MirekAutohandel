package com.autohandel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarDb {
    List<Car> carsForSale;



    public CarDb(){
        this.carsForSale = generateCarsForSale();
    }

    private List<Car> generateCarsForSale() {
        List<Car> result = new ArrayList<>();
        List<String> brands = List.of("Audi", "Mercedes", "Fiat", "Porsche", "Volkswagen", "BMW", "Citroen", "Opel", "Peugeot", "Dacia", "Hyundai", "Mazda");
        List<String> colors = List.of("blue", "red", "green", "white", "yellow", "black", "brown", "pink");
        Random random = new Random();

        //generate budget cars
        for (int i = 0; i < 5; i++) {
            result.add(new Car(
                    random.nextLong(500,2000),
                    brands.get(random.nextInt(brands.size())),
                    random.nextLong(999000),
                    colors.get(random.nextInt(colors.size())),
                    "budget",
                    0,
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean()
                    ));
        }

        //generate standard cars

        for (int i = 0; i < 5; i++) {
            result.add(new Car(
                    random.nextLong(5000,20000),
                    brands.get(random.nextInt(brands.size())),
                    random.nextLong(799000),
                    colors.get(random.nextInt(colors.size())),
                    "standard",
                    0,
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean()
            ));
        }

        //generate premium cars
        for (int i = 0; i < 5; i++) {
            result.add(new Car(
                    random.nextLong(50000,200000),
                    brands.get(random.nextInt(brands.size())),
                    random.nextLong(399000),
                    colors.get(random.nextInt(colors.size())),
                    "premium",
                    0,
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean()
            ));
        }
        //generate utility cars
        for (int i = 0; i < 5; i++) {
            result.add(new Car(
                    random.nextLong(1000,100000),
                    brands.get(random.nextInt(brands.size())),
                    random.nextLong(8799000),
                    colors.get(random.nextInt(colors.size())),
                    "utility",
                    random.nextLong(100,500),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextBoolean()
            ));
        } return result;
    }

}
