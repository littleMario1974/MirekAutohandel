package com.autohandel;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Math.round;

public class Main {

    static List<String> transactionHistory = new ArrayList<>(); //transakcje kupna /sprzedaży
    static List<String> carRepairHistory = new ArrayList<>(); // historia napraw
    public static int moves; // zlicza ilość ruchów kupno, sprzedaż, naprawa, kupno reklamy


    public static void main(String[] args) throws IOException {

        Mechanic janusz = new Mechanic("Janusz", 0, 0, 200);
        Mechanic marian = new Mechanic("Marian", 10, 0, 100);
        Mechanic adrian = new Mechanic("Adrian", 20, 2, 50);


        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            String lastInput = "";
            AutoHandel autohandel = new AutoHandel();
            autohandel.setCash(50000);
            CarDb dealers = new CarDb();
            CustomerDb potentialCustomers = new CustomerDb(); //potencjalni klienci


            do {
                System.out.println(printMenu());

                System.out.println("Ilość wykonanych ruchów w grze: " + moves);
                if (autohandel.getCash() >=100000) {
                    System.out.println("Brawo !!! Podwoiłeś początkowy zasób gotówki. Ilość ruchów: " + moves);
                }
                lastInput = readInput(bufferedReader);
                //namierz opcję i wykonaj obliczenia pętlą switch

                switch (lastInput) {
                    case "1":
                        System.out.println("Baza samochodów do kupienia");
                        dealers.getCarsForSale().forEach(System.out::println);
                        break;
                    case "2":
                        System.out.println("Kup samochód");
                        System.out.println(buyCar(autohandel, dealers, potentialCustomers, bufferedReader));

                        break;
                    case "3":
                        System.out.println("Baza posiadanych samochodów");
                        if (autohandel.getCars().size() == 0) {
                            System.out.println("Nie masz żadnych samochodów");
                        } else
                            autohandel.getCars().forEach(System.out::println);
                        break;
                    case "4":
                        System.out.println("Naprawa samochodów");
                        System.out.println(repairCar(autohandel, janusz, marian, adrian, bufferedReader));
                        break;
                    case "5":
                        System.out.println("Potencjalni klienci");
                        potentialCustomers.getCustomers().forEach(System.out::println);
                        break;
                    case "6":
                        System.out.println("Sprzedaż samochodu");
                        System.out.println(sellCar(autohandel, potentialCustomers, bufferedReader));


                        break;
                    case "7":
                        System.out.println("Kup reklamę");
                        System.out.println(buyAd(autohandel, potentialCustomers, bufferedReader));
                        break;
                    case "8":
                        System.out.println("Stan konta");
                        System.out.println("Masz na koncie " + round(autohandel.getCash()) + " zł");
                        System.out.println("Brakuje ci " + round(100000 - autohandel.getCash()) + " zł do ukończenia gry.");
                        break;
                    case "9":
                        System.out.println("Historia transakcji " + "(ilość transakcji " + transactionHistory.size() + ")");
                        for (int i = 0; i < transactionHistory.size(); i++) {
                            System.out.println(i + 1 + ".\t" + transactionHistory.get(i));
                        }
                        break;
                    case "10":
                        System.out.println(carRepairHistory(autohandel));


                        break;
                    case "11":
                        System.out.println("Suma kosztów napraw i mycia samochodu");
                        break;

                }
            } while (!lastInput.equalsIgnoreCase("x"));
        }
        System.out.println("Do widzenia...");
    }


    public static String buyCar(AutoHandel autoHandel, CarDb dealers, CustomerDb potentialCustomers, BufferedReader bufferedReader) {
        List<Car> carsForSale = dealers.getCarsForSale();
        List<Customer> customers = potentialCustomers.getCustomers();
        System.out.println("Wybierz auto do kupienia: ");
        for (int i = 0; i < carsForSale.size(); i++) {
            System.out.println(i + 1 + ".\t" + carsForSale.get(i));
        }
        try {
            String choiceString = bufferedReader.readLine();
            int choice = Integer.parseInt(choiceString);
            choice--;
            if (0 > choice || choice > carsForSale.size()) {
                return "Błędny wybór";
            }
            Car chosenCar = carsForSale.get(choice);
            long carValueWithFees = round((chosenCar.getValue() + (chosenCar.getValue() * 2) / 100) + 20); // wartość samochodu z podatkiem i myjnią
            if (carValueWithFees > autoHandel.getCash()) {
                return String.format("Nie masz tyle kasy. Trzeba %s (w tym cena, podatek i myjnia), a masz %s", carValueWithFees, round(autoHandel.getCash()));
            }
            carsForSale.remove(chosenCar); //usuwanie kupionego samochodu z listy do kupienia
            autoHandel.setCash(autoHandel.getCash() - carValueWithFees); //pomniejszenie dostępnej gotówki
            autoHandel.getCars().add(chosenCar); //dodanie samochodu do bazy samochodów Autohandlu

            moves++;
            transactionHistory.add("Zakup   " + chosenCar); //dodanie do listy historii transakicji kupno/sprzedaż




            //import new car in missing segment from german autohaus :)
            switch (chosenCar.getSegment()) {
                case "budget":
                    carsForSale.add(dealers.generateBudgetCar());
                    break;
                case "standard":
                    carsForSale.add(dealers.generateStandardCar());
                    break;
                case "premium":
                    carsForSale.add(dealers.generatePremiumCar());
                    break;
                case "utility":
                    carsForSale.add(dealers.generateUtilityCar());
                    break;
            }
            System.out.println(String.format("Kupiłeś %s za %s. Zostało Ci %s", chosenCar.getBrand(), round(chosenCar.getValue()), round(autoHandel.getCash())));
            // dodanie 2 nowych potencjalnych kupujących:
            customers.add(potentialCustomers.generateCustomers().get(1));
            customers.add(potentialCustomers.generateCustomers().get(2));
            return ("Dodano dwóch nowych potencjalnych kupujących.");

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }
    }


    private static String sellCar(AutoHandel autoHandel, CustomerDb potentialCustomers, BufferedReader bufferedReader) {

        List<Car> autoHandelCars = autoHandel.getCars();
        List<Customer> customers = potentialCustomers.getCustomers();

        if (autoHandelCars.size() == 0) {
            System.out.println("Nie posiadasz samochodów na sprzedaż.");

        } else System.out.println("Wybierz auto, które chcesz sprzedać: ");
        for (int i = 0; i < autoHandelCars.size(); i++) {
            System.out.println(i + 1 + ".\t" + autoHandelCars.get(i));
        }

        try {
            String choiceString = bufferedReader.readLine();
            int choice = Integer.parseInt(choiceString);
            choice--;
            if (0 > choice || choice > autoHandelCars.size()) {
                return "Błędny wybór";
            }
            Car chosenCar = autoHandelCars.get(choice);
            System.out.println("Wybrałeś do sprzedaży: " + chosenCar);


            boolean brokenCar = false;
            if (chosenCar.isBrakesBroken() || chosenCar.isSuspensionBroken() || chosenCar.isEngineBroken() || chosenCar.isBodyBroken() || chosenCar.isGearboxBroken()) {
                brokenCar = true; // jeśli któryś element zepsuty to true
            }

            System.out.println("Wybierz klienta: ");
            for (int i = 0; i < potentialCustomers.getCustomers().size(); i++) {
                System.out.println((i + 1) + ".\t" + customers.get(i)); //listowanie potencjalnego klienta
            }

            Scanner input = new Scanner(System.in);
            String choice2 = input.nextLine();
            try {
                int number = Integer.parseInt(choice2);
                if (0 > number || number > potentialCustomers.getCustomers().size()) {
                    return ("Podaj liczbę z zakresu od 1 do " + potentialCustomers.getCustomers().size());
                }
                //System.out.println(number);
            } catch (NumberFormatException e) {

                System.out.println("To nie jest liczba. Podaj liczbę z zakresu od 1 do " + potentialCustomers.getCustomers().size());


            }
            int potentialCustomerNumber = (Integer.parseInt(choice2) - 1); //parsowanie na liczbę minus jeden, bo od zera się zaczyna
            System.out.println("Wybrałeś klienta nr: " + (potentialCustomerNumber + 1) + ". " + customers.get(potentialCustomerNumber).name);


            //sprawdzenie czy ma kasę, czy odpowaida segment i marka 1 i 2 i czy akcetuje zespsuty samochód jeśli taki wybrałeś

            boolean compatiblePreferences = true;

            if (customers.get(potentialCustomerNumber).getBudget() < chosenCar.value + chosenCar.value * autoHandel.marge) {
                System.out.println("Kupujący jest za biedny. :(");
                compatiblePreferences = false;
            }
            if (customers.get(potentialCustomerNumber).getBudget() < chosenCar.value + chosenCar.value * autoHandel.marge) {
                System.out.println("Musisz doliczyć marżę " + autoHandel.marge * 100 + "%");
                compatiblePreferences = false;
            }
            if (customers.get(potentialCustomerNumber).getDesiredSegment() != chosenCar.getSegment()) {
                System.out.println("Kupujący preferuje inny segment. :(");
                compatiblePreferences = false;

            }
            if ((customers.get(potentialCustomerNumber).getDesiredBrand1() != chosenCar.getBrand()) && (customers.get(potentialCustomerNumber).getDesiredBrand2() != chosenCar.getBrand())) {
                System.out.println("Kupujący preferuje inne marki. :(");
                compatiblePreferences = false;

            }
            if (brokenCar) {
                if (!customers.get(potentialCustomerNumber).acceptsBroken) {
                    System.out.println("Kupujący nie akceptuje zesputych samochodów !!!");
                    compatiblePreferences = false;
                }

            }
            if (compatiblePreferences) {

                System.out.println("Gratulacje !!! Zarobiłeś " + round((chosenCar.getValue() + chosenCar.getValue() * autoHandel.marge - ((chosenCar.getValue()) * 2) / 100) - 20) + " (wartość samochodu: " + round(chosenCar.getValue()) + " + marża " + round(autoHandel.marge * 100) + " % wartości samochodu: " + round(((chosenCar.getValue() * autoHandel.marge) * 100) / 100) + " - podatek 2%: " + round(((chosenCar.getValue()) * 2) / 100) + " - myjnia: 20)");
                moves++;
                transactionHistory.add("Sprzedaż   samochód: " + chosenCar.getBrand() + " za cenę: " + round(chosenCar.getValue() + chosenCar.getValue() * autoHandel.marge) + " klientowi: " + customers.get(potentialCustomerNumber).name); //dodanie do listy historii transakicji kupno/sprzedaż
                autoHandelCars.remove(chosenCar); //usuwanie sprzedanego samochodu z listy AutoHandlu
                autoHandel.setCash(autoHandel.getCash() + round((chosenCar.getValue() + chosenCar.getValue() * autoHandel.marge - ((chosenCar.getValue()) * 2) / 100) - 20));//powiększenie dostępnej gotówki o cenę samochodu i marżę, minus 2% podatku i 20 zł myjnia
                customers.remove(potentialCustomerNumber); //usuwanie klienta, który kupił samochód
                // dodanie 2 nowych potencjalnych kupujących:
                customers.add(potentialCustomers.generateCustomers().get(1));
                customers.add(potentialCustomers.generateCustomers().get(2));
                System.out.println("Dodano dwóch nowych potencjalnych kupujących.");

            } else {

                System.out.println("Nie sprzedałeś samochodu.");
            }
            System.out.println("Czy dopasować potencjalnych klientów do posiadanych samochodów ?   Wciśnij '1' na tak");
            Scanner input1 = new Scanner(System.in);
            String choice3 = input1.nextLine();
            try {
                int number = Integer.parseInt(choice3);
                if (number == 1) {
                    comparePreferences(autoHandel, potentialCustomers, bufferedReader);
                }

            } catch (NumberFormatException e) {

                System.out.println("To nie jest liczba.");

            }

            return "";
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }

    }




    private static String comparePreferences(AutoHandel autoHandel, CustomerDb potentialCustomers, BufferedReader bufferedReader) {

        List<Car> autoHandelCars = autoHandel.getCars();
        List<Customer> customers = potentialCustomers.getCustomers();
        int compatibleQuantity = 0;
        if (autoHandelCars.size() == 0) {
            System.out.println("Nie posiadasz samochodów na sprzedaż.");
        } else {
            System.out.println("Spróbuję sparować klientów z pasującymi samochodami.");


            for (int customer = 0; customer < potentialCustomers.getCustomers().size(); customer++) {

                System.out.println();
                System.out.println(customer + 1 + ".\t" + customers.get(customer));
                System.out.println();
                for (int car = 0; car < autoHandelCars.size(); car++) {
                    System.out.println("\t" + (car + 1) + ".\t" + autoHandelCars.get(car));

                    boolean brokenCar = false;
                    boolean compatiblePreferences = true;

                    if (autoHandelCars.get(car).isBrakesBroken() || autoHandelCars.get(car).isSuspensionBroken() || autoHandelCars.get(car).isEngineBroken() || autoHandelCars.get(car).isBodyBroken() || autoHandelCars.get(car).isGearboxBroken()) {
                        brokenCar = true; // jeśli któryś element zepsuty to true

                    }
                    if (customers.get(customer).getBudget() < autoHandelCars.get(car).getValue()) {

                        compatiblePreferences = false;
                    }
                    if (customers.get(customer).getDesiredSegment() != autoHandelCars.get(car).getSegment()) {

                        compatiblePreferences = false;
                    }
                    if ((customers.get(customer).getDesiredBrand1() != autoHandelCars.get(car).getBrand()) && (customers.get(customer).getDesiredBrand2() != autoHandelCars.get(car).getBrand())) {

                        compatiblePreferences = false;
                    }
                    if (brokenCar == true) {


                        if (!customers.get(customer).acceptsBroken)

                            compatiblePreferences = false;
                    }

                    if (compatiblePreferences == true) {
                        compatibleQuantity++;
                        System.out.println();
                        System.out.println("\t\t*************************************** Ten samochód możesz sprzedać w/w klientowi o nazwisku: " + customers.get(customer).getName() + " ***************************************************");
                        System.out.println();
                    }
                }
            }
        }
        System.out.println("Ilość skojarzonych klientów z samochodami: " + compatibleQuantity);

        return "";
    }

    private static String buyAd(AutoHandel autoHandel, CustomerDb nextCustomers, BufferedReader bufferedReader) {
        //List<Car> carsForSale = dealers.getCarsForSale();

        List<Customer> customers = nextCustomers.getCustomers();
        System.out.println("1 . Ogłoszenie w lokalnej gazecie - 1000 zł.");
        System.out.println("2 . Ogłoszenie w internecie - 200 zł.");


        if (autoHandel.getCash() < 200) {
            System.out.println("Nie stać cię na żadną reklamę. Masz " + autoHandel.getCash() + " zł, a potrzeba min. 200 zł.");
            return "";
        }

        if (autoHandel.getCash() < 1000) {
            System.out.println("Stać cię tylko na reklamę w internecie. Masz tylko " + autoHandel.getCash() + " zł.");
        }

        try {
            String choiceString = bufferedReader.readLine();
            int choice = Integer.parseInt(choiceString);
            choice--;
            if (0 > choice || choice > 1) {
                return "Błędny wybór";
            }
            if ((choice == 0) && (autoHandel.getCash() < 1000)) {
                System.out.println("Przypominam - masz za mało pieniędzy na kupno reklamy w gazecie !!!");
            }


            if ((choice == 0) && (autoHandel.getCash() >= 1000)) {

                Random random = new Random();
                int newCustomers = random.nextInt(2, 7);

                System.out.println("Wykupiłeś ogłoszenie w lokalnej gazecie. Masz " + newCustomers + " nowych klientów.");
                // poniżej procedura dodania nowych klientów

                for (int i = 0; i < newCustomers; i++) {
                    customers.add(nextCustomers.generateCustomers().get(1));
                }
                autoHandel.setCash(autoHandel.getCash() - 1000);
                transactionHistory.add("Zakup reklamy w lokalnej gazecie:   1000 zł."); //dodanie do listy historii transakicji kupno/sprzedaż / reklama
                moves++;

            }

            if (choice == 1) {
                System.out.println("Wykupiłeś ogłoszenie w internecie. Masz 1 nowego klienta.");
                customers.add(nextCustomers.generateCustomers().get(1));
                autoHandel.setCash(autoHandel.getCash() - 200);
                transactionHistory.add("Zakup reklamy w internecie:          200 zł."); //dodanie do listy historii transakicji kupno/sprzedaż / reklama
                moves++;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }
    }

    public static String repairCar(AutoHandel autoHandel, Mechanic janusz, Mechanic marian, Mechanic adrian, BufferedReader bufferedReader) {
        int brakesBrokenCost = 80;
        int suspensionBrokenCost = 40;
        int engineBrokenCost = 30;
        int bodyBrokenCost = 50;
        int gearboxBrokenCost = 20;


        if (autoHandel.getCars().size() == 0) {
            System.out.println("Nie masz żadnych samochodów.");

        } else {

            for (int i = 0; i < autoHandel.getCars().size(); i++) {

                int repairCost = 0; // przypisanie kosztu naprawy do marki z bazy danych
                String chosenBrand;
                // dobranie kosztu naprawy w zależności od marki (niemieckie, inne, pozostałe...)
                chosenBrand = (autoHandel.getCars().get(i).brand);
                if (chosenBrand == "Porsche" || chosenBrand == "Mazda" || chosenBrand == "Hyundai") {
                    repairCost = 400;
                } else if (chosenBrand == "Audi" || chosenBrand == "Mercedes" || chosenBrand == "Volkswagen" || chosenBrand == "BMW" || chosenBrand == "Opel") {
                    repairCost = 200;
                } else {
                    repairCost = 50;
                }

                System.out.println(i + 1 + ".\t" + autoHandel.getCars().get(i));
                if (!autoHandel.getCars().get(i).brakesBroken && !autoHandel.getCars().get(i).suspensionBroken && !autoHandel.getCars().get(i).engineBroken && !autoHandel.getCars().get(i).bodyBroken && !autoHandel.getCars().get(i).gearboxBroken) {
                    System.out.println(" - samochód sprawny");
                }

                if (autoHandel.getCars().get(i).brakesBroken) {
                    System.out.println("\tZepsute hamulce.");
                    System.out.println("\t\tNaprawa u Janusza to: " + (brakesBrokenCost + janusz.margin + repairCost));
                    System.out.println("\t\tNaprawa u Mariana to: " + (brakesBrokenCost + marian.margin + repairCost));
                    System.out.println("\t\tNaprawa u Adriana to: " + (brakesBrokenCost + adrian.margin + repairCost));
                }
                if (autoHandel.getCars().get(i).suspensionBroken) {
                    System.out.println("\tZepsute zawieszenie.");
                    System.out.println("\t\tNaprawa u Janusza to: " + (suspensionBrokenCost + janusz.margin + repairCost));
                    System.out.println("\t\tNaprawa u Mariana to: " + (suspensionBrokenCost + marian.margin + repairCost));
                    System.out.println("\t\tNaprawa u Adriana to: " + (suspensionBrokenCost + adrian.margin + repairCost));
                }
                if (autoHandel.getCars().get(i).engineBroken) {
                    System.out.println("\tZepsuty silnik.");
                    System.out.println("\t\tNaprawa u Janusza to: " + (engineBrokenCost + janusz.margin + repairCost));
                    System.out.println("\t\tNaprawa u Mariana to: " + (engineBrokenCost + marian.margin + repairCost));
                    System.out.println("\t\tNaprawa u Adriana to: " + (engineBrokenCost + adrian.margin + repairCost));
                }
                if (autoHandel.getCars().get(i).bodyBroken) {
                    System.out.println("\tUszkodzona karoseria.");
                    System.out.println("\t\tNaprawa u Janusza to: " + (bodyBrokenCost + janusz.margin + repairCost));
                    System.out.println("\t\tNaprawa u Mariana to: " + (bodyBrokenCost + marian.margin + repairCost));
                    System.out.println("\t\tNaprawa u Adriana to: " + (bodyBrokenCost + adrian.margin + repairCost));
                }
                if (autoHandel.getCars().get(i).gearboxBroken) {
                    System.out.println("\tZepsuta skrzynia biegów");
                    System.out.println("\t\tNaprawa u Janusza to: " + (gearboxBrokenCost + janusz.margin + repairCost));
                    System.out.println("\t\tNaprawa u Mariana to: " + (gearboxBrokenCost + marian.margin + repairCost));
                    System.out.println("\t\tNaprawa u Adriana to: " + (gearboxBrokenCost + adrian.margin + repairCost));
                }
            }

            System.out.println("Wskaż numer samochodu do naprawy:");

            try {
                String choiceString = bufferedReader.readLine();
                int choice = Integer.parseInt(choiceString);
                choice--;

                if (choice + 1 > autoHandel.getCars().size())
                    System.out.println("Nie masz tylu samochodów.");

                else if (!autoHandel.getCars().get(choice).brakesBroken && !autoHandel.getCars().get(choice).suspensionBroken && !autoHandel.getCars().get(choice).engineBroken && !autoHandel.getCars().get(choice).bodyBroken && !autoHandel.getCars().get(choice).gearboxBroken) {
                    System.out.println("W samochodzie nr " + (choice + 1) + " nie ma nic do naprawy");
                    System.out.println(autoHandel.getCars().get(choice));
                } else {
                    //public static String repairCostFactor(CarDb.brands())
                    List<String> brokenElements = new ArrayList<>(); //lista zepsutych elementów w danym samochodzie

                    Car chosenCar = autoHandel.getCars().get(choice);
                    System.out.println("Wybrałeś samochód " + chosenCar);
                    int x = 0;

                    System.out.println("Do naprawy: ");
                    if (autoHandel.getCars().get(choice).brakesBroken) {
                        x++; // 1 element do naprawy itd...
                        brokenElements.add("hamulce");

                    }
                    if (autoHandel.getCars().get(choice).suspensionBroken) {
                        x++;
                        brokenElements.add("zawieszenie");

                    }

                    if (autoHandel.getCars().get(choice).engineBroken) {
                        x++;
                        brokenElements.add("silnik");

                    }
                    if (autoHandel.getCars().get(choice).bodyBroken) {
                        x++;
                        brokenElements.add("karoseria");
                        //System.out.println("\t" + x + " " + "karoseria");

                    }
                    if (autoHandel.getCars().get(choice).gearboxBroken) {
                        x++;
                        brokenElements.add("skrzynia biegów");
                        //System.out.println("\t" + x + " " +"skrzynia biegów");

                    }

                    for (int i = 0; i < brokenElements.size(); i++) {
                        System.out.println(i + 1 + ".\t" + brokenElements.get(i)); // wypisanie uszkodzonych elementów danego samochodu
                    }

                    int repairCost = 0; // przypisanie kosztu naprawy do marki z bazy danych

                    if (chosenCar.brand == "Porsche" || chosenCar.brand == "Mazda" || chosenCar.brand == "Hyundai") {
                        repairCost = 400;
                    } else if (chosenCar.brand == "Audi" || chosenCar.brand == "Mercedes" || chosenCar.brand == "Volkswagen" || chosenCar.brand == "BMW" || chosenCar.brand == "Opel") {
                        repairCost = 200;
                    } else {
                        repairCost = 50;
                    }

                    if (x > 1) {
                        System.out.println("\tCo naprawiamy ? Podaj numer:"); // jeśli jest więcej niż jedna usterka
                        // wybór elementu do naprawy

                        Scanner input = new Scanner(System.in);
                        String choice2 = input.nextLine();

                        int number = 0;
                        try {
                            number = Integer.parseInt(choice2);
                            brokenElements.get(number - 1);


                            if (number > x) {
                                System.out.println("Wybierz numer od 1 do " + x);
                            } else {
                                System.out.println("Wybrałeś do naprawy element: " + brokenElements.get(number - 1));
                            }
                        } catch (NumberFormatException e) {
                        }
                    }

                    String brokenElement = brokenElements.get(x - 1);
                    //System.out.println("Tymczasowo: " + brokenElement);

                    //przypisanie wybranego elementu do zmiennej
                    int brokenElementCost = 0;
                    if (brokenElement == "hamulce") {

                        brokenElementCost = brakesBrokenCost;
                    }
                    if (brokenElement == "zawieszenie") {

                        brokenElementCost = suspensionBrokenCost;
                    }
                    if (brokenElement == "silnik") {

                        brokenElementCost = engineBrokenCost;
                    }
                    if (brokenElement == "karoseria") {

                        brokenElementCost = bodyBrokenCost;
                    }
                    if (brokenElement == "skrzynia biegów") {

                        brokenElementCost = gearboxBrokenCost;
                    }


                    System.out.println("Wybierz mechanika");

                    System.out.println("1. Janusz");
                    System.out.println("2. Marian");
                    System.out.println("3. Adrian");


                    Scanner input2 = new Scanner(System.in);
                    String choice3 = input2.nextLine();

                    int number2 = Integer.parseInt(choice3);

                    if (0 > number2 || number2 > 3) {
                        return ("Podaj liczbę z zakresu od 1 do 3");
                    }

                    String selectedMechanic = null;
                    switch (number2) {
                        case 1:
                            selectedMechanic = janusz.getName();
                            break;
                        case 2:
                            selectedMechanic = marian.getName();
                            break;
                        case 3:
                            selectedMechanic = adrian.getName();
                            break;

                    }

                    System.out.println("Wybrałeś mechanika o imieniu " + selectedMechanic);

                    if (selectedMechanic == "Janusz") {

                        long totalCosts = (brokenElementCost + janusz.margin + repairCost);
                        if (totalCosts > autoHandel.getCash()) {
                            System.out.println("Masz za mało kasy.");
                        }else {
                            System.out.println("Udało się naprawić samochód. Zapłaciłeś Januszowi za naprawę elementu: " + brokenElements.get(x - 1) + " w samochodzie marki " + chosenCar.brand + ": " + totalCosts);
                            autoHandel.setCash(autoHandel.getCash() - totalCosts); // pomniejszenie gotówki

                            // w zależności od elementu zmiana wartości samochodu + element zepsuty jako false
                            if (brokenElements.get(x - 1) == "hamulce") {
                                //System.out.println(brokenElements.get(x-1));
                                chosenCar.value = (chosenCar.value + (chosenCar.value / 10));
                                chosenCar.brakesBroken = false;
                            }
                            if (brokenElements.get(x - 1) == "zawieszenie") {
                                //System.out.println(brokenElements.get(x-1));
                                chosenCar.value = (chosenCar.value + (chosenCar.value / 5));
                                chosenCar.suspensionBroken = false;
                            }
                            if (brokenElements.get(x - 1) == "silnik") {
                                //System.out.println(brokenElements.get(x-1));
                                chosenCar.value = (chosenCar.value * 2);
                                chosenCar.engineBroken = false;
                            }
                            if (brokenElements.get(x - 1) == "karoseria") {
                                //System.out.println(brokenElements.get(x-1));
                                chosenCar.value = (chosenCar.value + (chosenCar.value / 2));
                                chosenCar.bodyBroken = false;
                            }
                            if (brokenElements.get(x - 1) == "skrzynia biegów") {
                                //System.out.println(brokenElements.get(x-1));
                                chosenCar.value = (chosenCar.value + (chosenCar.value / 2));
                                chosenCar.gearboxBroken = false;
                            }
                            carRepairHistory.add("Samochód: " + chosenCar.brand + " o przebiegu " + chosenCar.mileage + ", udało się naprawić element: " + brokenElements.get(x - 1) + " mechanik: Janusz");
                        }
                    }
                    if (selectedMechanic == "Marian") {
                        long totalCosts = (brokenElementCost + marian.margin + repairCost);
                        if (totalCosts > autoHandel.getCash()) {
                            System.out.println("Masz za mało kasy.");
                        }else {
                            Random random = new Random();
                            boolean nonRepair = random.nextInt(100) < marian.nonRepairProbability;
                            if (nonRepair == true) {
                                System.out.println("Niestety Marianowi nie udało się naprawić auta. Musisz naprawić samochód u Janusza."); //todo - wymusić naprawę
                                carRepairHistory.add("Samochód: " + chosenCar.brand + " o przebiegu " + chosenCar.mileage + ", nie udało się naprawić elementu: " + brokenElements.get(x - 1) + " mechanik: Marian");
                                System.out.println("Zapłaciłeś Marianowi " + totalCosts);
                            } else {
                                System.out.println("Udało się naprawić samochód. Zapłaciłeś Marianowi za naprawę elementu: " + brokenElements.get(x - 1) + " w samochodzie marki " + chosenCar.brand + ": " + totalCosts);
                                carRepairHistory.add("Samochód: " + chosenCar.brand + " o przebiegu " + chosenCar.mileage + ", udało się naprawić element: " + brokenElements.get(x - 1) + " mechanik: Marian");
                                autoHandel.setCash(autoHandel.getCash() - totalCosts);


                                if (brokenElements.get(x - 1) == "hamulce") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 10));
                                    chosenCar.brakesBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "zawieszenie") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 5));
                                    chosenCar.suspensionBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "silnik") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value * 2);
                                    chosenCar.engineBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "karoseria") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 2));
                                    chosenCar.bodyBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "skrzynia biegów") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 2));
                                    chosenCar.gearboxBroken = false;
                                }
                            }
                        }
                    }

                    if (selectedMechanic == "Adrian") {
                        long totalCosts = (brokenElementCost + adrian.margin + repairCost);
                        if (totalCosts > autoHandel.getCash()) {
                            System.out.println("Masz za mało kasy.");
                        }else {
                            Random random = new Random();
                            boolean nonRepair = random.nextInt(100) < adrian.nonRepairProbability;

                            if (nonRepair == true) {
                                System.out.println("Niestety Adrianowi nie udało się naprawić auta, ale zapłaciłeś Adrianowi za usługę " + totalCosts);
                                carRepairHistory.add("Samochód: " + chosenCar.brand + " o przebiegu " + chosenCar.mileage + ", nie udało się naprawić elementu: " + brokenElements.get(x - 1) + " mechanik: Adrian");
                            } else {
                                System.out.println("Udało się naprawić samochód. Zapłaciłeś Adrianowi za naprawę elementu: " + brokenElements.get(x - 1) + " w samochodzie marki " + chosenCar.brand + ": " + totalCosts);
                                carRepairHistory.add("Samochód: " + chosenCar.brand + " o przebiegu " + chosenCar.mileage + ", udało się naprawić element: " + brokenElements.get(x - 1) + " mechanik: Adrian");
                                autoHandel.setCash(autoHandel.getCash() - totalCosts);

                                if (brokenElements.get(x - 1) == "hamulce") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 10));
                                    chosenCar.brakesBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "zawieszenie") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 5));
                                    chosenCar.suspensionBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "silnik") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value * 2);
                                    chosenCar.engineBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "karoseria") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 2));
                                    chosenCar.bodyBroken = false;
                                }
                                if (brokenElements.get(x - 1) == "skrzynia biegów") {
                                    //System.out.println(brokenElements.get(x-1));
                                    chosenCar.value = (chosenCar.value + (chosenCar.value / 2));
                                    chosenCar.gearboxBroken = false;
                                }
                            }
                            Random random2 = new Random();
                            boolean breakingAnotherElement = random2.nextInt(100) < adrian.breakingAnotherElementProbability;
                            if (breakingAnotherElement == false) {
                                System.out.println("Na szczęście Adrian nie zepsuł innego elementu.");
                            } else {
                                //ustalenie co jest sprawne i dodanie do listy
                                List<String> nonBrokenElements = new ArrayList<>();

                                if (!chosenCar.brakesBroken) {
                                    nonBrokenElements.add("hamulce");
                                }
                                if (!chosenCar.suspensionBroken) {
                                    nonBrokenElements.add("zawieszenie");
                                }
                                if (!chosenCar.engineBroken) {
                                    nonBrokenElements.add("silnik");
                                }
                                if (!chosenCar.bodyBroken) {
                                    nonBrokenElements.add("karoseria");
                                }
                                if (!chosenCar.gearboxBroken) {
                                    nonBrokenElements.add("skrzynia biegów");
                                }
                                // losowanie elementu "do zepsucia"
                                if (nonBrokenElements.size() < 6) {
                                    Random random3 = new Random();
                                    int nonBrokenElement = random3.nextInt(0, nonBrokenElements.size());

                                    if (nonBrokenElements.get(nonBrokenElement) == "hamulce") {
                                        chosenCar.brakesBroken = true;
                                    }
                                    if (nonBrokenElements.get(nonBrokenElement) == "zawieszenie") {
                                        chosenCar.suspensionBroken = true;
                                    }
                                    if (nonBrokenElements.get(nonBrokenElement) == "silnik") {
                                        chosenCar.engineBroken = true;
                                    }
                                    if (nonBrokenElements.get(nonBrokenElement) == "karoseria") {
                                        chosenCar.bodyBroken = true;
                                    }
                                    if (nonBrokenElements.get(nonBrokenElement) == "skrzynia biegów") {
                                        chosenCar.gearboxBroken = true;
                                    }


                                    System.out.println("Poza tym Adrian zepsuł w samochodzie element: " + nonBrokenElements.get(nonBrokenElement) + ".");
                                    carRepairHistory.add("Samochód: " + chosenCar.brand + " o przebiegu " + chosenCar.mileage + ", zepsuto element: " + brokenElements.get(x - 1) + " mechanik: Adrian");
                                }
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }moves++;
        }
        return "";
    }

    public static String carRepairHistory(AutoHandel autoHandel) {
        System.out.println("Historia naprawy samochodu");
        Collections.sort(carRepairHistory);
        int number = 1;
        int code = 0;
        for (int i = 0; i < carRepairHistory.size(); i++) {

            System.out.println(carRepairHistory.get(i));
        }
        return "";
    }

    private static String readInput(BufferedReader bufferedReader) throws IOException {

        String read = bufferedReader.readLine();
        return read;
    }

    static String printMenu() {
        return """
                Wybierz opcję:
                 1. Przeglądaj bazę samochodów do kupienia
                 2. Kup samochód
                 3. Przeglądaj bazę posiadanych samochodów
                 4. Napraw samochód
                 5. Przejrzyj potencjalnych klientów
                 6. Sprzedaj samochód za określoną cenę potencjalnemu klientowi
                 7. Kup reklamę
                 8. Sprawdź stan konta
                 9. Sprawdź historię transakcji (kupno / sprzedaż)
                10. Sprawdź historię napraw każdego pojazdu
                11. Sprawdź sumę kosztów napraw i mycia dla każdego z posiadanych pojazdów
                 
                 Naciśnij 'x' aby wyjść.""";
    }


}
