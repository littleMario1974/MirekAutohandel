package com.autohandel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        Mechanic janusz = new Mechanic("Janusz", 0, 0, 200);
        Mechanic marian = new Mechanic("Marian", 10, 0, 100);
        Mechanic adrian = new Mechanic("Adrian", 20, 2, 50);


        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            String lastInput = "";
            int moves = 0; //zlicza ilość wykonanych ruchów
            AutoHandel autohandel = new AutoHandel();
            autohandel.setCash(50000);
            CarDb dealers = new CarDb();
            CustomerDb potentialCustomers = new CustomerDb(); //potencjalni klienci


            do {
                System.out.println(printMenu());
                lastInput = readInput(bufferedReader);
                //namierz opcję i wykonaj obliczenia pętlą switch

                switch (lastInput) {
                    case "1":
                        System.out.println("Baza samochodów do kupienia");
                        dealers.getCarsForSale().forEach(System.out::println);
                        break;
                    case "2":
                        System.out.println("Kup samochód");
                        System.out.println(buyCar(autohandel, dealers, bufferedReader));
                        moves++;
                        break;
                    case "3":
                        if (autohandel.getCars().size()==0){
                            System.out.println("Nie masz żadnych samochodów");
                        }
                        else
                            System.out.println("Baza posiadanych samochodów");
                            autohandel.getCars().forEach(System.out::println);
                        break;
                    case "4":
                        System.out.println("Naprawa samochodów");
                        // wylistowanie samochodów z uszkodzonym elementem
                        //System.out.println("Samochody z uszkodzeniami");

                        for (int i = 0; i < autohandel.getCars().size(); i++) {

                            System.out.println(i + 1 + ".\t" + autohandel.getCars().get(i));
                            if (!autohandel.getCars().get(i).brakesBroken && !autohandel.getCars().get(i).suspensionBroken && !autohandel.getCars().get(i).engineBroken && !autohandel.getCars().get(i).bodyBroken && !autohandel.getCars().get(i).gearboxBroken) {
                                System.out.println(" - samochód sprawny");
                            }

                            if (autohandel.getCars().get(i).brakesBroken) {
                                System.out.println("\tZepsute hamulce.");
                                System.out.println("\t\tNaprawa u Janusza to: " + (autohandel.getCars().get(i).value / 80 + janusz.margin));
                                System.out.println("\t\tNaprawa u Mariana to: " + (autohandel.getCars().get(i).value / 80 + marian.margin));
                                System.out.println("\t\tNaprawa u Adriana to: " + (autohandel.getCars().get(i).value / 80 + adrian.margin));
                            }
                            if (autohandel.getCars().get(i).suspensionBroken) {
                                System.out.println("\tZepsute zawieszenie.");
                                System.out.println("\t\tNaprawa u Janusza to: " + (autohandel.getCars().get(i).value / 40 + janusz.margin));
                                System.out.println("\t\tNaprawa u Mariana to: " + (autohandel.getCars().get(i).value / 40 + marian.margin));
                                System.out.println("\t\tNaprawa u Adriana to: " + (autohandel.getCars().get(i).value / 40 + adrian.margin));
                            }
                            if (autohandel.getCars().get(i).engineBroken) {
                                System.out.println("\tZepsuty silnik.");
                                System.out.println("\t\tNaprawa u Janusza to: " + (autohandel.getCars().get(i).value / 30 + janusz.margin));
                                System.out.println("\t\tNaprawa u Mariana to: " + (autohandel.getCars().get(i).value / 30 + marian.margin));
                                System.out.println("\t\tNaprawa u Adriana to: " + (autohandel.getCars().get(i).value / 30 + adrian.margin));
                            }
                            if (autohandel.getCars().get(i).bodyBroken) {
                                System.out.println("\tUszkodzona karoseria.");
                                System.out.println("\t\tNaprawa u Janusza to: " + (autohandel.getCars().get(i).value / 50 + janusz.margin));
                                System.out.println("\t\tNaprawa u Mariana to: " + (autohandel.getCars().get(i).value / 50 + marian.margin));
                                System.out.println("\t\tNaprawa u Adriana to: " + (autohandel.getCars().get(i).value / 50 + adrian.margin));
                            }
                            if (autohandel.getCars().get(i).gearboxBroken) {
                                System.out.println("\tZepsuta skrzynia biegów");
                                System.out.println("\t\tNaprawa u Janusza to: " + (autohandel.getCars().get(i).value / 20 + janusz.margin));
                                System.out.println("\t\tNaprawa u Mariana to: " + (autohandel.getCars().get(i).value / 20 + marian.margin));
                                System.out.println("\t\tNaprawa u Adriana to: " + (autohandel.getCars().get(i).value / 20 + adrian.margin));
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
                            } else {
                                System.out.println("Wybrałeś samochód " + autohandel.getCars().get(choice));
                                int x = 1;

                                System.out.println("Do naprawy: ");
                                if (autohandel.getCars().get(choice).brakesBroken) {

                                    System.out.println("\t" + x + ". hamulce");
                                    x++;
                                }
                                if (autohandel.getCars().get(choice).suspensionBroken) {
                                    System.out.println("\t" + x + ". zawieszenie");
                                    x++;
                                }

                                if (autohandel.getCars().get(choice).engineBroken) {
                                    System.out.println("\t" + x + ". silnik");
                                    x++;
                                }
                                if (autohandel.getCars().get(choice).bodyBroken) {
                                    System.out.println("\t" + x + ". karoseria");
                                    x++;
                                }
                                if (autohandel.getCars().get(choice).gearboxBroken) {
                                    System.out.println("\t" + x + ". skrzynia biegów");
                                }
                                if ( x > 2 ) System.out.println("\tCo naprawiamy ? Podaj numer:"); // jeśli jest więcej niż jedna usterka
                                // TODO: wybór elementu do naprawy


                                Scanner input = new Scanner(System.in);
                                String choice2 = input.nextLine();
                                try
                                {
                                    int number = Integer.parseInt(choice2);
                                }
                                catch(NumberFormatException e) {
                                    System.out.println("Podaj liczbę z zakresu od 1 do " + x);

                                    System.out.println("Wybrałeś do naprawy element nr " + choice2);

                                }

                            }

                                } catch (Exception e) {
                            e.printStackTrace(System.out);
                            return;
                        }


                        moves++;
                        break;

                    case "5":
                        System.out.println("Potencjalni klienci");
                        potentialCustomers.getCustomers().forEach(System.out::println);
                        //System.out.println(potentialCustomers.customers);

                        break;
                    case "6":
                        System.out.println("Sprzedaż samochodu");
                        //todo wybierz samochód, wybierz klienta i sprawdź czy ma wystarczająco dużo gotówki, czy segment i marka odpowiada i czy akcetuje zespsuty samochód jeśli taki wybrałeś
                        System.out.println(sellCar(autohandel, potentialCustomers, bufferedReader));

                        moves++;
                        break;
                    case "7":
                        System.out.println("Kup reklamę");
                        moves++;
                        break;
                    case "8":
                        System.out.println("Stan konta");
                        System.out.println("Masz na koncie " + autohandel.getCash() + " zł");
                        break;
                    case "9":
                        System.out.println("Historia transakcji");
                        break;
                    case "10":
                        System.out.println("Historia naprawy samochodu");
                        break;
                    case "11":
                        System.out.println("Suma kosztów napraw i mycia samochodu");
                        break;
                    //default:
                    //System.out.println("Podałeś niewłaściwą opcję");
                }
            } while (!lastInput.equalsIgnoreCase("x"));
        }
        System.out.println("Do widzenia...");
    }


    private static String buyCar(AutoHandel autoHandel, CarDb dealers, BufferedReader bufferedReader) {
        List<Car> carsForSale = dealers.getCarsForSale();
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
            return String.format("Kupiłeś %s za %s. Zostało Ci %s", chosenCar.getBrand(), chosenCar.getValue(), autoHandel.getCash());


        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }
    }

    //******************************************************

    private static String sellCar(AutoHandel autoHandel, CustomerDb potentialCustomers, BufferedReader bufferedReader) {
        List<Car> Cars = autoHandel.getCars();

        if (Cars.size()==0) {
            System.out.println("Nie posiadasz samochodów na sprzedaż.");

        } else System.out.println("Wybierz auto, które chcesz sprzedać: ");
        for (int i = 0; i < Cars.size(); i++) {
            System.out.println(i + 1 + ".\t" + Cars.get(i));
        }

        try {
            String choiceString = bufferedReader.readLine();
            int choice = Integer.parseInt(choiceString);
            choice--;
            if (0 > choice || choice > Cars.size()) {
                return "Błędny wybór";
            }
            Car chosenCar = Cars.get(choice);
            //todo
            /*if (chosenCar.getValue() > autoHandel.getCash()) {
                //return String.format("Nie masz tyle kasy. Trzeba %s a masz %s", chosenCar.getValue(), autoHandel.getCash());
            }
            Cars.remove(chosenCar); //usuwanie kupionego samochodu z listy do kupienia
            autoHandel.setCash(autoHandel.getCash() - chosenCar.getValue()); //pomniejszenie dostępnej gotówki
            autoHandel.getCars().add(chosenCar); //dodanie samochodu do bazy samochodów Autohandlu
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
            } */
            return String.format("Wybrałeś do sprzedaży: " + Cars.get(choice));


        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }


    }





    //******************************************************

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
                 Naciśnij x aby wyjść.""";
    }


}
