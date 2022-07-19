package com.autohandel;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
                        System.out.println(buyCar(autohandel, dealers, bufferedReader));

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

                        // wylistowanie samochodów

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
                                if (x > 2)
                                    System.out.println("\tCo naprawiamy ? Podaj numer:"); // jeśli jest więcej niż jedna usterka
                                // TODO: wybór elementu do naprawy


                                Scanner input = new Scanner(System.in);
                                String choice2 = input.nextLine();
                                try {
                                    int number = Integer.parseInt(choice2);
                                    System.out.println("Wybrałeś numer " + (number));
                                } catch (NumberFormatException e) {

                                    //System.out.println("Podaj liczbę z zakresu od 1 do " + (x-1));

                                    //System.out.println("Wybrałeś do naprawy element nr " + choice2);

                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace(System.out);
                        }


                        moves++;
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


    public static String buyCar(AutoHandel autoHandel, CarDb dealers, BufferedReader bufferedReader) {
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
            return String.format("Kupiłeś %s za %s. Zostało Ci %s", chosenCar.getBrand(), chosenCar.getValue(), autoHandel.getCash());


        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }
    }


    //******************************************************

    private static String sellCar(AutoHandel autoHandel, CustomerDb potentialCustomers, BufferedReader bufferedReader) {
        List<Car> autoHandelCars = autoHandel.getCars();
        List<Customer> Customers = potentialCustomers.getCustomers();

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
                System.out.println(i + 1 + ".\t" + Customers.get(i)); //listowanie potencjalnego klienta
            }

            Scanner input = new Scanner(System.in);
            String choice2 = input.nextLine();
            try {
                int number = Integer.parseInt(choice2);
                if (0 > number || number > potentialCustomers.getCustomers().size()) {
                    return ("Podaj liczbe z zakresu od 1 do " + potentialCustomers.getCustomers().size());
                }
                System.out.println(number);
            } catch (NumberFormatException e) {

                System.out.println("To nie jest liczba. Podaj liczbe z zakresu od 1 do " + potentialCustomers.getCustomers().size());


            }
            int potentialCustomerNumber = (Integer.parseInt(choice2) - 1); //parsowanie na liczbę minus jeden, bo od zera się zaczyna
            System.out.println("Wybrałeś klienta nr: " + potentialCustomers.getCustomers().get(potentialCustomerNumber));


            //todo sprawdzenie czy ma kasę, czy odpowaida segment i marka 1 i 2 i czy akcetuje zespsuty samochód jeśli taki wybrałeś

            boolean compatiblePreferences = true;

            if (Customers.get(potentialCustomerNumber).getBudget() < chosenCar.value) {
                System.out.println("Kupujący jest za biedny. :(");
                compatiblePreferences = false;

            }
            if (Customers.get(potentialCustomerNumber).getDesiredSegment() != chosenCar.getSegment()) {
                System.out.println("Kupujący preferuje inny segment. :(");
                compatiblePreferences = false;

            }
            if ((Customers.get(potentialCustomerNumber).getDesiredBrand1() != chosenCar.getBrand()) && (Customers.get(potentialCustomerNumber).getDesiredBrand2() != chosenCar.getBrand())) {
                System.out.println("Kupujący preferuje inne marki. :(");
                compatiblePreferences = false;

            }
            if (brokenCar) {
                if (!Customers.get(potentialCustomerNumber).acceptsBroken)
                    System.out.println("Kupujący nie akceptuje zesputych samochodów !!!");
                compatiblePreferences = false;

            }
            if (compatiblePreferences) {
                System.out.println("Preferencje :" + compatiblePreferences);
                System.out.println("Gratulacje !!! Samochód sprzedany !");
                autoHandelCars.remove(chosenCar); //usuwanie sprzedanego samochodu z listy AutoHandlu
                autoHandel.setCash(autoHandel.getCash() + chosenCar.getValue());//powiększenie dostępnej gotówki o cenę samochodu
                Customers.remove(potentialCustomerNumber); //usuwanie klienta, który kupił samochód
                moves++; // kolejny ruch po sprzedazy samochodu
                transactionHistory.add("Sprzedaż   " + chosenCar); //dodanie do listy historii transakicji kupno/sprzedaż
            } //else System.out.println("Nie sprzedałeś samochodu.");
            return "Nie sprzedałeś samochodu.";

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }

    }

    private static String buyAd(AutoHandel autoHandel, CustomerDb potentialCustomers, BufferedReader bufferedReader) {

        System.out.println("1 . Ogłoszenie w lokalnej gazecie - 1000 zł.");
        System.out.println("2 . Ogłoszenie w internecie - 200 zł.");
        if (autoHandel.getCash() < 200) {
            System.out.println("Nie stać cię na żadną reklamę. Masz " + autoHandel.getCash() + " zł, a potrzeba min. 200 zł.");
            return "";
        }
        try {
            String choiceString = bufferedReader.readLine();
            int choice = Integer.parseInt(choiceString);
            choice--;
            if (0 > choice || choice > 1) {
                return "Błędny wybór";
            }
            if (choice == 0) {
                Random random = new Random();
                int newCustomers = random.nextInt(2, 10);
                System.out.println("Wykupiłeś ogłoszenie w lokalnej gazecie. Masz " + newCustomers + " nowych klientów.");
                // todo procedura dodania nowych klientów


            } else {
                System.out.println("Wykupiłeś ogłoszenie w internecie. Masz 1 nowego klienta.");
                // todo procedura dodania nowego klienta

            }
            return "";
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
                 Naciśnij 'x' aby wyjść.""";
    }


}
