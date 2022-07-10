package com.autohandel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {

        Mechanic janusz = new Mechanic("Janusz", 0, 0, 100);
        Mechanic marian = new Mechanic("Marian", 10, 0, 30);
        Mechanic adrian = new Mechanic("Adrian", 20, 2, 10);



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
                            if (autohandel.getCars().get(i).brakesBroken) {
                                System.out.println("\tZepsute hamulce.");
                                System.out.println("\t\tNaprawa u Janusza to: " + autohandel.getCars().get(i).value / 800 * janusz.margin);
                                System.out.println("\t\tNaprawa u Mariana to: " + autohandel.getCars().get(i).value / 800 * marian.margin);
                                System.out.println("\t\tNaprawa u Adriana to: " + autohandel.getCars().get(i).value / 800 * adrian.margin);
                            }
                            if (autohandel.getCars().get(i).suspensionBroken) {
                                System.out.println("\tZepsute zawieszenie.");
                                System.out.println("\t\tNaprawa u Janusza to: " + autohandel.getCars().get(i).value / 400 * janusz.margin);
                                System.out.println("\t\tNaprawa u Mariana to: " + autohandel.getCars().get(i).value / 400 * marian.margin);
                                System.out.println("\t\tNaprawa u Adriana to: " + autohandel.getCars().get(i).value / 400 * adrian.margin);
                            }
                            if (autohandel.getCars().get(i).engineBroken) {
                                System.out.println("\tZepsuty silnik.");
                                System.out.println("\t\tNaprawa u Janusza to: " + autohandel.getCars().get(i).value/ 300 * janusz.margin);
                                System.out.println("\t\tNaprawa u Mariana to: " + autohandel.getCars().get(i).value / 300 * marian.margin);
                                System.out.println("\t\tNaprawa u Adriana to: " + autohandel.getCars().get(i).value / 300 * adrian.margin);
                            }
                            if (autohandel.getCars().get(i).bodyBroken) {
                                System.out.println("\tUszkodzona karoseria.");
                                System.out.println("\t\tNaprawa u Janusza to: " + autohandel.getCars().get(i).value/ 500 * janusz.margin);
                                System.out.println("\t\tNaprawa u Mariana to: " + autohandel.getCars().get(i).value / 500 * marian.margin);
                                System.out.println("\t\tNaprawa u Adriana to: " + autohandel.getCars().get(i).value / 500 * adrian.margin);
                            }
                            if (autohandel.getCars().get(i).gearboxBroken) {
                                System.out.println("\tZepsuta skrzynia biegów");
                                System.out.println("\t\tNaprawa u Janusza to: " + autohandel.getCars().get(i).value/ 200 * janusz.margin);
                                System.out.println("\t\tNaprawa u Mariana to: " + autohandel.getCars().get(i).value / 200 * marian.margin);
                                System.out.println("\t\tNaprawa u Adriana to: " + autohandel.getCars().get(i).value / 200 * adrian.margin);
                            }
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
