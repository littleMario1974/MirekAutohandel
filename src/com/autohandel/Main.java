package com.autohandel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String lastInput = "";
        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            do {
                System.out.println(printMenu());
                lastInput = readInput(bufferedReader);
                //namierz opcję i wykonaj obliczenia pętlą switch

                switch (lastInput) {
                    case "1":
                        System.out.println("Baza samochodów do kupienia");
                        break;
                    case "2":
                        System.out.println("Kup samochód");
                        break;
                    case "3":
                        System.out.println("Baza posiadanych samochodów");
                        break;
                    case "4":
                        System.out.println("Naprawa samochodów");
                        break;
                    case "5":
                        System.out.println("Potencjalni klienci");
                        break;
                    case "6":
                        System.out.println("Sprzedaż samochodu");
                        break;
                    case "7":
                        System.out.println("Kup reklamę");
                        break;
                    case "8":
                        System.out.println("Stan konta");
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
                    default:
                        System.out.println("Podałeś niewłaściwą opcję");
                }
            } while (!lastInput.equalsIgnoreCase("x"));
        }
        //System.out.println("Do widzenia...");
    }

    Double cash = 50000.0; // początkowy zasób gotówki

    Mechanic janusz = new Mechanic("Janusz", 0, 0, 50);
    Mechanic marian = new Mechanic("Marian", 10, 0, 30);
    Mechanic adrian = new Mechanic("Adrian", 20, 2, 10);


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
