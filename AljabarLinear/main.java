package AljabarLinear;

import java.util.Scanner;

public class main extends functions_procedures {

    public static void PrintMenu() {
        System.out.println("");
        System.out.println("****************************************************************");
        System.out.println("                             MENU");
        System.out.println("****************************************************************");
        System.out.println("1. Sistem Persamaan Linier Gauss Jordan");
        System.out.println("2. Determinan Kofaktor");
        System.out.println("3. Matriks Inverse");
        System.out.println("4. Keluar");
        System.out.print("Pilih menu: ");
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        PrintMenu();
        int menu = scan.nextInt();
        while (menu != 4) {
            if (menu == 1) { //SPL
                    System.out.println("");
                    System.out.println("****************************************************************");
                    System.out.println("          MENYELESAIKAN SPL DENGAN METODE GAUSS-JORDAN");
                    System.out.println("****************************************************************");
                    mainsplgaussjordan();
            }
            
            else if (menu == 2) { //DETERMINAN
                System.out.println("");
                System.out.println("****************************************************************");
                System.out.println("          MENCARI DETERMINAN DENGAN EKSPANSI KOFAKTOR");
                System.out.println("****************************************************************");
                maindetcofactor();
            }

            else if (menu == 3) { //INVERS
                System.out.println("");
                System.out.println("****************************************************************");
                System.out.println("                 MENCARI INVERS SUATU MATRIKS");
                System.out.println("****************************************************************");
                maininverse();
            }

            else if (menu == 4) {

            }
            PrintMenu();
            menu = scan.nextInt();
        }

        System.out.println("");
        System.out.println("****************************************************************");
        System.out.println("                       SELAMAT BELAJAR!");
        System.out.println("****************************************************************");
    }
}
