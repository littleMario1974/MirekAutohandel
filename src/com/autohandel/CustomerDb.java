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
        List<String> names = List.of("Woźniak", "Stępień", "Walczak", "Kalinowski", "Jankowski", "Szczepański", "Jankowski", "Gajewska", "Kołodziej", "Dąbrowski", "Kołodziej", "Chmielewski", "Sawicki", "Kowalski", "Maciejewski", "Wiśniewski", "Rutkowski", "Kaczmarczyk", "Mazurek", "Baran", "Mróz", "Makowski", "Wysocki", "Borkowski", "Urbańska", "Kamiński", "Piotrowski", "Chmielewski", "Kaczmarczyk", "Krupa", "Wojciechowski", "Walczak", "Pawlak", "Bąk", "Adamska", "Czarnecki", "Gajewska", "Rutkowski", "Kowalczyk", "Kwiatkowski", "Sobczak", "Szymański", "Kamiński", "Ostrowski", "Rutkowski", "Wróblewski", "Lis", "Szymański", "Laskowska", "Walczak", "Rutkowski", "Duda", "Makowski", "Sokołowski", "Adamska", "Kowalski", "Górecki", "Lewandowski", "Kołodziej", "Czerwiński", "Kowalczyk", "Zieliński", "Sokołowski", "Mazurek", "Pietrzak", "Makowski", "Wasilewska", "Bąk", "Wiśniewski", "Kołodziej", "Chmielewski", "Tomaszewski", "Zakrzewska", "Wojciechowski", "Ostrowski", "Kamiński", "Kucharski", "Wasilewska", "Witkowski", "Adamska", "Rutkowski", "Malinowski", "Zalewski", "Kołodziej", "Sobczak", "Sobczak", "Maciejewski", "Baran", "Jaworski", "Lewandowski", "Sokołowski", "Sobczak", "Sobczak", "Gajewska", "Kozłowski", "Wysocki", "Kaczmarczyk", "Jankowski", "Wójcik", "Lewandowski", "Głowacka", "Brzeziński", "Kowalczyk", "Duda", "Ziółkowska", "Szymański", "Kwiatkowski", "Kołodziej", "Malinowski", "Borkowski", "Wysocki", "Kucharski", "Lewandowski", "Kowalski", "Ziółkowska", "Wojciechowski", "Kozłowski", "Włodarczyk", "Michalak", "Krajewska", "Sikora", "Błaszczyk", "Duda", "Szulc", "Wiśniewski", "Adamska", "Kucharski", "Jankowski", "Andrzejewski", "Wojciechowski", "Baranowski", "Wiśniewski", "Mazurek", "Zalewski", "Maciejewski", "Wróblewski", "Wróblewski", "Gajewska", "Tomaszewski", "Bąk", "Krupa", "Laskowska", "Szulc", "Włodarczyk", "Baran", "Błaszczyk", "Górecki", "Mróz", "Kwiatkowski", "Szymczak", "Sikorska", "Makowski", "Przybylski", "Bąk", "Andrzejewski", "Kamiński", "Jaworski", "Woźniak", "Jaworski", "Duda", "Czerwiński", "Zakrzewska", "Borkowski", "Kwiatkowski", "Górski", "Kubiak", "Michalak", "Czarnecki", "Pawlak", "Sikorska", "Kowalski", "Dąbrowski", "Sawicki", "Górecki", "Czerwiński", "Kamiński", "Baran", "Jaworski", "Nowak", "Mazurek", "Sobczak", "Zawadzki", "Zalewski", "Bąk", "Walczak", "Wasilewska", "Andrzejewski", "Zakrzewska", "Sikorska", "Mazurek", "Kozłowski", "Przybylski", "Urbańska", "Gajewska", "Dąbrowski", "Głowacka", "Sadowska", "Bąk", "Błaszczyk", "Błaszczyk", "Chmielewski", "Zalewski", "Kozłowski", "Sobczak", "Wójcik", "Ostrowski", "Jaworski", "Kamiński", "Ziółkowska", "Rutkowski", "Szymczak", "Czerwiński", "Dąbrowski", "Kozłowski", "Sobczak", "Włodarczyk", "Sawicki", "Sadowska", "Zieliński", "Szulc", "Kamiński", "Kaczmarczyk", "Błaszczyk", "Marciniak", "Krupa", "Michalak", "Kołodziej", "Czerwiński", "Borkowski", "Zieliński", "Błaszczyk", "Kucharski", "Sadowska", "Kamiński", "Sikorska", "Górecki", "Witkowski", "Sawicki", "Wróblewski", "Bąk", "Jankowski", "Ziółkowska", "Sobczak", "Wasilewska", "Wiśniewski", "Szczepański", "Borkowski", "Głowacka", "Kubiak", "Duda", "Cieślak", "Piotrowski", "Duda", "Sobczak", "Czarnecki", "Wysocki", "Szczepański", "Wasilewska", "Kołodziej", "Jakubowski", "Bąk", "Zawadzki", "Stępień", "Zieliński", "Kamiński", "Szulc", "Mróz", "Mazurek", "Zalewski", "Walczak", "Woźniak", "Baranowski", "Górski", "Ostrowski", "Chmielewski", "Szczepański", "Sadowska", "Kalinowski", "Michalak", "Bąk", "Cieślak", "Kwiatkowski", "Nowak", "Sadowska", "Jasiński", "Wojciechowski", "Krupa", "Jakubowski", "Wróblewski", "Sikorska", "Dąbrowski", "Kalinowski", "Szymański", "Gajewska", "Kucharski", "Krupa", "Wojciechowski", "Makowski", "Andrzejewski", "Sokołowski", "Kubiak", "Kamiński", "Kucharski", "Wójcik", "Kowalski", "Szewczyk", "Czerwiński", "Górski", "Górski", "Wójcik", "Baranowski", "Maciejewski", "Kamiński", "Michalak", "Zawadzki", "Kaźmierczak", "Krupa", "Kwiatkowski", "Mazur", "Michalak", "Pietrzak", "Michalak", "Wiśniewski", "Górski", "Witkowski", "Szewczyk", "Zalewski", "Pawlak", "Wasilewska", "Pietrzak", "Kaźmierczak", "Ostrowski", "Górski", "Zawadzki", "Sikorska", "Szulc", "Przybylski", "Witkowski", "Woźniak", "Piotrowski", "Andrzejewski", "Górecki", "Stępień", "Nowak", "Czarnecki", "Jankowski", "Kołodziej", "Pawlak", "Szczepański", "Górski", "Włodarczyk", "Kaczmarczyk", "Ostrowski", "Kamiński", "Sikorska", "Szymczak", "Andrzejewski", "Krajewska", "Jaworski", "Krupa", "Gajewska", "Marciniak", "Wiśniewski", "Kozłowski", "Głowacka", "Andrzejewski", "Głowacka", "Kołodziej", "Malinowski", "Górski", "Mazurek", "Zawadzki", "Woźniak", "Kwiatkowski", "Makowski", "Dąbrowski", "Kamiński", "Wiśniewski", "Przybylski", "Cieślak", "Zalewski", "Cieślak", "Michalak", "Urbańska", "Mróz", "Wiśniewski", "Głowacka", "Lewandowski", "Przybylski", "Pawlak", "Bąk", "Błaszczyk", "Walczak", "Szewczyk", "Zawadzki", "Sikora", "Laskowska", "Woźniak", "Mazurek", "Duda", "Nowak", "Szymański", "Maciejewski", "Szewczyk", "Bąk", "Sawicki", "Sikorska", "Urbańska", "Szymański", "Sokołowski", "Kamiński", "Kalinowski", "Zawadzki", "Kaczmarczyk", "Sobczak", "Mróz", "Gajewska", "Sokołowski", "Pietrzak", "Mazurek", "Adamska", "Włodarczyk", "Czerwiński", "Szymański", "Górecki", "Gajewska", "Kaczmarczyk", "Pawlak", "Górski", "Jasiński", "Jakubowski", "Czarnecki", "Kowalski", "Kowalski", "Jaworski", "Krupa", "Mróz", "Szymański", "Michalak", "Cieślak", "Kucharski", "Kalinowski", "Szczepański", "Zakrzewska", "Przybylski", "Baran", "Borkowski", "Woźniak", "Gajewska", "Wojciechowski", "Szczepański", "Malinowski", "Zalewski", "Kubiak", "Kamiński", "Błaszczyk", "Szewczyk", "Cieślak", "Zakrzewska", "Brzeziński", "Czarnecki", "Kwiatkowski", "Cieślak", "Woźniak", "Sawicki", "Baran", "Pietrzak", "Stępień", "Brzeziński", "Kalinowski", "Makowski", "Sobczak", "Głowacka", "Sikora", "Sikora", "Chmielewski", "Szulc", "Ziółkowska", "Bąk", "Zakrzewska", "Zieliński", "Kubiak", "Kalinowski", "Wojciechowski", "Mazur", "Andrzejewski", "Szulc", "Kalinowski", "Czerwiński", "Lis", "Adamska", "Górecki", "Lewandowski", "Ziółkowska", "Sokołowski", "Borkowski", "Sawicki", "Lewandowski", "Zalewski", "Wróblewski");
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

    public List<Customer> getCustomers() {
        return customers;
    }
}