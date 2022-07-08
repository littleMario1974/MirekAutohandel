package com.autohandel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerDb {
    List<Customer> customers;

    public CustomerDb() {
        this.customers = generateCustomers();
    }

    private List<Customer> generateCustomers() {
        List<Customer> result = new ArrayList<>();
        List<String> names = List.of("Kowalski", "Baranowski", "Kwiatkowski", "Zieliński", "Lewandowski", "Kaczyński", "Wojciechowski", "Szymanowski", "Jackowski", "Wieczorek", "Malinowski", "Kaszubowski");
        List<String> segments = List.of("budget", "standard", "premium");
        List<String> brands = List.of("Audi", "Mercedes", "Fiat", "Porsche", "Volkswagen", "BMW", "Citroen", "Opel", "Peugeot", "Dacia", "Hyundai", "Mazda");

            Random random = new Random();

        //generate customers
        for (int i = 0; i < 7; i++) {
            result.add(new Customer(
                    names.get(random.nextInt(names.size())),
                    random.nextLong(10000, 50000),
                    segments.get(random.nextInt(segments.size())),
                    brands.get(random.nextInt(brands.size())),
                    brands.get(random.nextInt(brands.size())),
                    random.nextInt(10) < 3

            ));

        }
        return result;

    }
}