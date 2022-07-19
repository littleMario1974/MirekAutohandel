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
        List<String> names = List.of("Adamska", "Andrzejewski", "Baran", "Baranowski", "Bąk", "Błaszczyk", "Borkowski", "Brzeziński", "Chmielewski", "Cieślak", "Czarnecki", "Czerwiński", "Dąbrowski", "Duda", "Gajewska", "Głowacka", "Górecki", "Górski", "Jakubowski", "Jankowski", "Jasiński", "Jaworski", "Kaczmarczyk", "Kalinowski", "Kamiński", "Kaźmierczak", "Kołodziej", "Kowalczyk", "Kowalski", "Kozłowski", "Krajewska", "Krawczyk", "Krupa", "Kubiak", "Kucharski", "Kwiatkowski", "Laskowska", "Lewandowski", "Lis", "Maciejewski", "Makowski", "Malinowski", "Marciniak", "Mazur", "Mazurek", "Michalak", "Mróz", "Nowak", "Ostrowski", "Pawlak", "Pietrzak", "Piotrowski", "Przybylski", "Rutkowski", "Sadowska", "Sawicki", "Sikora", "Sikorska", "Sobczak", "Sokołowski", "Stępień", "Szczepański", "Szewczyk", "Szulc", "Szymański", "Szymczak", "Tomaszewski", "Urbańska", "Walczak", "Wasilewska", "Wiśniewski", "Witkowski", "Włodarczyk", "Wojciechowski", "Woźniak", "Wójcik", "Wróblewski", "Wysocki", "Zakrzewska", "Zalewski", "Zawadzki", "Zieliński", "Ziółkowska");
        List<String> segments = List.of("budget", "standard", "premium", "utility");
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

    public List<Customer> getCustomers() {
        return customers;
    }
}