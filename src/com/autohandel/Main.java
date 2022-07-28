package com.autohandel;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.round;

public class Main {

    static List<String> transactionHistory = new ArrayList<>(); //transakcje kupna /sprzedaży
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
                        System.out.println("Masz na koncie " + autohandel.getCash() + " zł");
                        System.out.println("Brakuje ci " + (100000 - autohandel.getCash()) + " zł do ukończenia gry.");
                        break;
                    case "9":
                        System.out.println("Historia transakcji " + "(ilość transakcji " + transactionHistory.size() + ")");
                        for (int i = 0; i < transactionHistory.size(); i++) {
                            System.out.println(i + 1 + ".\t" + transactionHistory.get(i));
                        }
                        break;
                    case "10":
                        System.out.println("Historia naprawy samochodu");
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
            if (chosenCar.getValue() > autoHandel.getCash()) {
                return String.format("Nie masz tyle kasy. Trzeba %s a masz %s", chosenCar.getValue(), autoHandel.getCash());
            }
            carsForSale.remove(chosenCar); //usuwanie kupionego samochodu z listy do kupienia
            autoHandel.setCash(autoHandel.getCash() - chosenCar.getValue()); //pomniejszenie dostępnej gotówki
            autoHandel.getCars().add(chosenCar); //dodanie samochodu do bazy samochodów Autohandlu

            moves++;
            System.out.println(moves);
            transactionHistory.add("Zakup   " + chosenCar); //dodanie do listy historii transakicji kupno/sprzedaż

            //todo Dodatkowo każdy samochód musisz umyć i zapłacić 2% podatku od wartości przy zakupie i przy sprzedaży.


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
            System.out.println(String.format("Kupiłeś %s za %s. Zostało Ci %s", chosenCar.getBrand(), chosenCar.getValue(), autoHandel.getCash()));
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
                //System.out.println("Preferencje :" + compatiblePreferences);
                System.out.println("Gratulacje !!! Zarobiłeś " + (chosenCar.getValue() + chosenCar.getValue() * autoHandel.marge) + " (wartość samochodu: " + chosenCar.getValue() + " + marża " + (autoHandel.marge * 100) + " % wartości samochodu: " + (round((chosenCar.getValue() * autoHandel.marge) * 100) / 100) + ")");
                transactionHistory.add("Sprzedaż   samochód: " + chosenCar.getBrand() + " za cenę: " + (chosenCar.getValue() + chosenCar.getValue() * autoHandel.marge) + " klientowi: " + customers.get(potentialCustomerNumber).name); //dodanie do listy historii transakicji kupno/sprzedaż
                autoHandelCars.remove(chosenCar); //usuwanie sprzedanego samochodu z listy AutoHandlu
                autoHandel.setCash(autoHandel.getCash() + (chosenCar.getValue() + chosenCar.getValue() * autoHandel.marge));//powiększenie dostępnej gotówki o cenę samochodu
                customers.remove(potentialCustomerNumber); //usuwanie klienta, który kupił samochód
                // dodanie 2 nowych potencjalnych kupujących:
                customers.add(potentialCustomers.generateCustomers().get(1));
                customers.add(potentialCustomers.generateCustomers().get(2));
                System.out.println("Dodano dwóch nowych potencjalnych kupujących.");

            } else {

                System.out.println("sprawdzam co jest nie tak: " + compatiblePreferences);
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
                System.out.println(number);
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

    public static String repairCar(AutoHandel autohandel, Mechanic janusz, Mechanic marian, Mechanic adrian, BufferedReader bufferedReader) {

        for (int i = 0; i < autohandel.getCars().size(); i++) {

            int repairCost = 0; // przypisanie kosztu naprawy do marki z bazy danych
            String chosenBrand;
            // dobranie kosztu naprawy w zależności od marki (niemieckie, inne, pozostałe...)
                chosenBrand = (autohandel.getCars().get(i).brand);
                if (chosenBrand == "Porsche" || chosenBrand == "Mazda" || chosenBrand == "Hyundai") {
                    repairCost = 400;
                } else if (chosenBrand == "Audi" || chosenBrand == "Mercedes" || chosenBrand == "Volkswagen" || chosenBrand == "BMW" || chosenBrand == "Opel") {
                    repairCost = 200;
                } else {
                    repairCost = 50;
                }

            System.out.println(i + 1 + ".\t" + autohandel.getCars().get(i));
            if (!autohandel.getCars().get(i).brakesBroken && !autohandel.getCars().get(i).suspensionBroken && !autohandel.getCars().get(i).engineBroken && !autohandel.getCars().get(i).bodyBroken && !autohandel.getCars().get(i).gearboxBroken) {
                System.out.println(" - samochód sprawny");
            }

            if (autohandel.getCars().get(i).brakesBroken) {
                System.out.println("\tZepsute hamulce.");
                System.out.println("\t\tNaprawa u Janusza to: " + (80 + janusz.margin + repairCost));
                System.out.println("\t\tNaprawa u Mariana to: " + (80 + marian.margin + repairCost));
                System.out.println("\t\tNaprawa u Adriana to: " + (80 + adrian.margin + repairCost));
            }
            if (autohandel.getCars().get(i).suspensionBroken) {
                System.out.println("\tZepsute zawieszenie.");
                System.out.println("\t\tNaprawa u Janusza to: " + (40 + janusz.margin + repairCost));
                System.out.println("\t\tNaprawa u Mariana to: " + (40 + marian.margin + repairCost));
                System.out.println("\t\tNaprawa u Adriana to: " + (40 + adrian.margin + repairCost));
            }
            if (autohandel.getCars().get(i).engineBroken) {
                System.out.println("\tZepsuty silnik.");
                System.out.println("\t\tNaprawa u Janusza to: " + (30 + janusz.margin + repairCost));
                System.out.println("\t\tNaprawa u Mariana to: " + (30 + marian.margin + repairCost));
                System.out.println("\t\tNaprawa u Adriana to: " + (30 + adrian.margin + repairCost));
            }
            if (autohandel.getCars().get(i).bodyBroken) {
                System.out.println("\tUszkodzona karoseria.");
                System.out.println("\t\tNaprawa u Janusza to: " + (50 + janusz.margin + repairCost));
                System.out.println("\t\tNaprawa u Mariana to: " + (50 + marian.margin + repairCost));
                System.out.println("\t\tNaprawa u Adriana to: " + (50 + adrian.margin + repairCost));
            }
            if (autohandel.getCars().get(i).gearboxBroken) {
                System.out.println("\tZepsuta skrzynia biegów");
                System.out.println("\t\tNaprawa u Janusza to: " + (20 + janusz.margin + repairCost));
                System.out.println("\t\tNaprawa u Mariana to: " + (20 + marian.margin + repairCost));
                System.out.println("\t\tNaprawa u Adriana to: " + (20 + adrian.margin + repairCost));
            }
        }

        System.out.println("Wskaż numer samochodu do naprawy:");

        try {
            String choiceString = bufferedReader.readLine();
            int choice = Integer.parseInt(choiceString);
            choice--;

            if (choice + 1 > autohandel.getCars().size())
                System.out.println("Nie masz tylu samochodów.");

            else if (!autohandel.getCars().get(choice).brakesBroken && !autohandel.getCars().get(choice).suspensionBroken && !autohandel.getCars().get(choice).engineBroken && !autohandel.getCars().get(choice).bodyBroken && !autohandel.getCars().get(choice).gearboxBroken) {
                System.out.println("W samochodzie nr " + (choice + 1) + " nie ma nic do naprawy");
                System.out.println(autohandel.getCars().get(choice));
            } else {
                //public static String repairCostFactor(CarDb.brands())
                List<String> brokenElements = new ArrayList<>(); //lista zepsutych elementów w danym samochodzie

                Car chosenCar = autohandel.getCars().get(choice);
                System.out.println("Wybrałeś samochód " + chosenCar);
                int x = 0;


                System.out.println("Do naprawy: ");
                if (autohandel.getCars().get(choice).brakesBroken) {
                    x++;
                    brokenElements.add("hamulce");

                }
                if (autohandel.getCars().get(choice).suspensionBroken) {
                    x++;
                    brokenElements.add("zawieszenie");

                }

                if (autohandel.getCars().get(choice).engineBroken) {
                    x++;
                    brokenElements.add("silnik");

                }
                if (autohandel.getCars().get(choice).bodyBroken) {
                    x++;
                    brokenElements.add("karoseria");
                    //System.out.println("\t" + x + " " + "karoseria");

                }
                if (autohandel.getCars().get(choice).gearboxBroken) {
                    x++;
                    brokenElements.add("skrzynia biegów");
                    //System.out.println("\t" + x + " " +"skrzynia biegów");

                }
                for (int i = 0; i < brokenElements.size(); i++) {
                    System.out.println(i + 1 + ".\t" + brokenElements.get(i)); // wypisanie uszkodzonych elementów danego samochodu
                }


                if (x > 1) {
                    System.out.println("\tCo naprawiamy ? Podaj numer:"); // jeśli jest więcej niż jedna usterka
                    // TODO: wybór elementu do naprawy


                    Scanner input = new Scanner(System.in);
                    String choice2 = input.nextLine();

                    int number = 0;
                    try {
                        number = Integer.parseInt(choice2);

                        if (number > x) {
                            System.out.println("Wybierz numer od 1 do " + x);
                        } else {
                            System.out.println("Wybrałeś do naprawy element: " + brokenElements.get(number - 1));
                            String brokenElement = brokenElements.get(number - 1); //przypisanie wybranego elementu do zmiennej
                        }

                    } catch (NumberFormatException e) {
                    }
                    System.out.println("Wybierz mechanika");

                    System.out.println("1. Janusz");
                    System.out.println("2. Marian");
                    System.out.println("3. Adrian");


                    Scanner input2 = new Scanner(System.in);
                    String choice3 = input.nextLine();
                    try {
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

                        System.out.println(selectedMechanic);

                        if (selectedMechanic == "Janusz") {
                            // todo sprawdź czy masz tyle pieniędzy
                            //  jeśli tak -  skasuj za naprawę marża w zł + % od wartości pojazu
                            //double repairCost; // koszt naprawy dla janusza
                            //repairCost =


                        }

                    } catch (NumberFormatException e) {

                        System.out.println("To nie jest liczba. Podaj liczbę z zakresu od 1 do 3");


                    }


                }


            }


        } catch (Exception e) {
            e.printStackTrace(System.out);
        }


        //moves++;
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
                 9. Sprawdź historię transakcji
                10. Sprawdź historię napraw każdego pojazdu
                11. Sprawdź sumę kosztów napraw i mycia dla każdego z posiadanych pojazdów
                 Naciśnij 'x' aby wyjść.""";
    }


}
