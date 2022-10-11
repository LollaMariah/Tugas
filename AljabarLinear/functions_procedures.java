package AljabarLinear;

/*
TUBES 1 ALGEO
Nabilah Erfariani (13519181)
Rhea Elka Pandumpi (13519047)
Rahmah Khoirussyifa' Nurdini (13519013)
*/

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;

public class functions_procedures {
    /*
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                MASUK KE BAGIAN SPL GAUSS DAN GAUSS JORDAN
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */
    public static void mainsplgaussjordan() {
        Scanner scan = new Scanner(System.in);
            System.out.print("Masukkan jumlah baris matriks augmented: ");
            int baris = scan.nextInt();

            System.out.print("Masukkan jumlah kolom matriks augmented: ");
            int kolom = scan.nextInt();

            float[][] Matrix = new float[baris][kolom];

            inputMatrixdata(scan,Matrix,baris,kolom);

            if (kolom < baris) {
                System.out.println("\nKarena jumlah kolom < jumlah baris, maka SPL bersifat inkonsisten");
            }

            else {
                System.out.println("Matrix Gauss-Jordan dari matrix masukan Anda adalah");
                printMatrix(gaussjordan(Matrix, baris, kolom),baris,kolom);
                printsolusi(gaussjordan(Matrix,baris,kolom),baris,kolom);
            }
    }

    public static void inputMatrixdata(Scanner scan,float[][] Matrix,int baris,int kolom) {
        System.out.println("Input matriks augmented: ");

        for(int i=0; i<baris; i++) {
            for(int j=0; j<kolom; j++) {
                Matrix[i][j] = scan.nextFloat();
            }
        }
    }

    public static void printMatrix(float[][] Matrix, int baris, int kolom) {

        for(int i=0; i<baris; i++) {
            for(int j=0; j<kolom; j++) {
                if (j == kolom-1) {
                    System.out.print(Matrix[i][j]);
                } 
                else {
                    System.out.print(Matrix[i][j]);
                    System.out.print(' ');
                }  
            }
            System.out.println();
        }
    }

    public static int firstfoundX(float[][] Matrix,int i,int kolom,int X) {
        int firstfound = 0;
        for(int j=0; j<kolom; j++) {
            if (Matrix[i][j] == X) {
                firstfound = j;
                break;
            }
        }
        return firstfound;
    }

    public static int firstfoundnotX(float[][] Matrix,int i,int kolom,int X) {
        int firstnotfound = kolom; 
        for(int j=0; j<kolom; j++) {
            if (Matrix[i][j] != X) {
                firstnotfound = j;
                break;
            }
        }
        return firstnotfound;
    }

    public static void make0centered(float[][] Matrix, int baris, int kolom) {
        for(int i=0; i<baris; i++) {
            for(int j=0; j<baris; j++) {
                if (firstfoundnotX(Matrix, i, kolom,0) < firstfoundnotX(Matrix, j, kolom,0)) {
                    tukarbaris(Matrix, i, j, kolom);
                }
            }
        }
    }

    public static void tukarbaris(float[][] Matrix, int idxbaris1, int idxbaris2, int kolom) {
        float[] temp = new float[kolom];

        //MENYIMPAN BARIS1 DI TEMP
        for(int j=0; j<kolom; j++) {
            temp[j] = Matrix[idxbaris1][j];
        }

        //MENUKAR BARIS1 MENJADI BARIS2
        for(int j=0; j<kolom; j++) {
            Matrix[idxbaris1][j] = Matrix[idxbaris2][j];
        }

        //MENUKAR BARIS2 MENJADI TEMP
        for(int j=0; j<kolom; j++) {
            Matrix[idxbaris2][j] = temp[j];
        }
    }

    public static float[][] gauss(float[][] Matrix,int baris, int kolom) {

        int looping = baris;
        make0centered(Matrix, baris, kolom);

        for(int l=looping; l>0; l--) {
            //MEMBAGI ELEMEN PERTAMA SETELAH NOL BIAR JADI 1
            float divider = Matrix[baris-l][baris-l];
            boolean dividernol;
            if (divider==0) {
                dividernol = true;
                int jdiv = baris-l+1;
                while (jdiv<kolom && dividernol) {
                    divider = Matrix[baris-l][jdiv];
                    if (divider==0) dividernol = true;
                    else {
                        dividernol = false;
                    }
                    jdiv += 1;
                }
            }
            else {
                dividernol = false;
            }

            boolean nolsemua;
            if (dividernol) nolsemua = true;
            else nolsemua = false;

            if (!dividernol && !nolsemua) {
                for(int j=0; j<kolom; j++) {
                    Matrix[baris-l][j] /= divider;
                }
            }
        
            //MAU BIKIN JADI NOL SEMUA KE BAWAH
            int kol = firstfoundX(Matrix, baris-l, kolom, 1);
            for(int i=baris-l+1; i<baris; i++) {
                float multiplier = (-1)*Matrix[i][kol];
                for(int j=0; j<kolom; j++) {
                    Matrix[i][j] += multiplier*Matrix[baris-l][j];
                    if (Matrix[i][j] == -0.0) {
                        Matrix[i][j] = Float.parseFloat("0.0");
                    }
                }   
            } 
        }
        return Matrix; 
    }

    public static float[][] gaussjordan(float[][] Matrix,int baris, int kolom) {
        Matrix = gauss(Matrix,baris,kolom);

        for(int i=0; i<baris; i++) {
            boolean found1first = false;
            for(int j=0; j<kolom; j++) {
                if (Matrix[i][j] == 1 && !found1first) {
                    found1first = true;
                    for(int k=0; k<i; k++) {
                        float multiplier = (-1)*Matrix[k][j];
                        for(int l=0; l<kolom; l++) {
                            Matrix[k][l] += multiplier*Matrix[i][l];
                        }
                    }
                }
            }
        }
        return Matrix;
    }

    public static void printsolusi (float[][] Matrix, int baris, int kolom) {
        boolean unik=false,banyak=false,tidakada = false;

        if (firstfoundnotX(Matrix, baris-1, kolom, 0)==kolom) banyak = true;
        else banyak = false;

        if (firstfoundnotX(Matrix, baris-1, kolom, 0)==kolom-1) tidakada = true;
        else tidakada = false;

        for(int i=0; i<baris-1; i++) {
            if (firstfoundX(Matrix,i,kolom,1) == firstfoundX(Matrix,i+1,kolom,1)-1) unik = true;
            else unik = false;
        }

        if (!unik && !tidakada) banyak = true;

        if (banyak) System.out.println("Terdapat banyak solusi");
        if (tidakada) System.out.println("Tidak ada solusi");
        if (unik && !banyak && !tidakada) System.out.println("Terdapat solusi unik");

        if (unik && !banyak && !tidakada) printsolusiunik(Matrix, baris, kolom);
        if (banyak) printsolusibanyak(Matrix, baris, kolom);
    }

    public static void printsolusiunik (float[][] Matrix, int baris, int kolom) {
        float[] ArrayTemp = new float[kolom-1]; 
        
        for(int i=baris-1; i>=0; i--) {
            ArrayTemp[i] = Matrix[i][kolom-1];
            int k=kolom-2;
            while(k>i) {
                ArrayTemp[i] -= Matrix[i][k]*ArrayTemp[k];
                k--;
            }
        }

        for(int j=0; j<kolom-1; j++) {
            int k = j+1;
            System.out.print("X"+k+" = ");
            System.out.println(ArrayTemp[j]);
        }
    }

    public static void printsolusibanyak (float[][] Matrix, int baris, int kolom) {
        float[] ArrayTemp = new float[kolom-1];

        for(int i=0; i<kolom-1; i++) ArrayTemp[i] = 99999;

        for(int i=baris-1; i>=0; i--) {
            ArrayTemp[firstfoundX(Matrix, i, kolom-1, 1)] = Matrix[i][kolom-1];
            int k=kolom-2;
            while(k>i) {
                if (ArrayTemp[k] != 99999) {
                    ArrayTemp[i] -= Matrix[i][k]*ArrayTemp[k];
                }
                k--;
            }
        }

        for(int j=0; j<kolom-1; j++) {
            int ascii = (96+j+1);
            char x = (char) ascii;
            int k = j+1;
            System.out.print("X"+k+" = ");
            if (!(ArrayTemp[j] == 99999)) {
                System.out.print(ArrayTemp[j]);
                
                int bar = 0;
                for(int i=0; i<baris; i++) {
                    if (firstfoundX(Matrix, i, kolom, 1) == j) {
                        bar = i;
                        break;
                    }
                }
    
                for(int l=j+1; l<kolom-1; l++) {
                    int ascii_1 = (96+l+1);
                    x = (char) ascii_1;
                    if (ArrayTemp[l] == 99999) {
                        if (Matrix[bar][l] > 0) {
                            System.out.print("-"+Matrix[bar][l]+x);
                        }
                        else if (Matrix[bar][l] < 0) {
                            System.out.print("+"+((-1)*Matrix[bar][l])+x);
                        }
                    }
                }
            }
            else {
                System.out.print(x);
            }
            System.out.print("\n");
        }
    }
    /*
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                MASUK KE BAGIAN SPL INVERSE
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */
    public static void mainsplinvers()
    {
        Scanner keyboard = new Scanner(System.in);
        int sumSPL = 1;
        int sumvar = 2;
        
        while(sumSPL != sumvar) {
			try {
				System.out.println("-------------------------------------------------------------------------");
				System.out.print("Masukkan jumlah sistem persamaan linear Anda: ");
                sumSPL = keyboard.nextInt();
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("PERINGATAN: Jumlah variabel dan jumlah persamaan harus sama!");
                System.out.println("Jika tidak sama, maka SPL Anda tidak dapat diselesaikan dengan metode invers!");
                System.out.print("Masukkan jumlah variabel persamaan linear Anda: ");
                sumvar = keyboard.nextInt();
			} catch (InputMismatchException ex) {
				keyboard.next();
			}
		}
    
        int i;
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Jika SPL adalah x + 2y + 3z = 4");
        System.out.println("Maka masukan matriks konstanta variabel adalah 1 2 3 dan masukan hasil adalah 4");
        System.out.println("-------------------------------------------------------------------------");
        double [][]thematriks = new double[sumSPL][sumvar];
        double [][]constants = new double[sumSPL][1];
        //input Sistem Persamaan Linear
        for (i=0; i<sumSPL; i++){
            System.out.print("Masukkan matriks konstanta variabel baris ke-"+(i+1)+": ");
            for(int j=0;j<sumvar;j++){
                thematriks[i][j]= keyboard.nextDouble();
            }
            System.out.print("Masukkan hasil baris ke-"+(i+1)+": ");
            constants[i][0] = keyboard.nextDouble();
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Matriks augmented dari inputan Anda adalah :");
        //Menuliskan Sistem Persamaan Linear ke bentuk matriks
        for(i=0; i<sumSPL; i++)
        {
            for(int j=0; j<sumvar; j++)
            {System.out.print(" "+thematriks[i][j]);}
            System.out.print("  ");
            System.out.print("= "+ constants[i][0]);
            System.out.println();
        }

        //melakukan pengecekan apakah matriks invertible atau tidak.
 
        if(determinant(thematriks, sumSPL) == 0)
        {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Matriks tidak invertible, karena determinannya adalah : "+determinant(thematriks, sumSPL));
            System.out.println("Sistem Persamaan Linear Anda tidak memiliki solusi");
            System.out.println("-------------------------------------------------------------------------");
        }
        else
        {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Matriks invertible, karena determinannya adalah : "+determinant(thematriks, sumSPL));
            double d[][] = invert(thematriks); 
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Invers dari matriks Anda adalah: ");
            for (i=0; i<sumSPL; ++i) 
            {
                for (int j=0; j<sumSPL; ++j)
                {System.out.print(d[i][j]+"  "); //menampilkan inverse dari matriks
                }System.out.println();}
            //Perkalian antara inverse matriks dengan contant untuk mendapatkan solusi
            double hasil[][] = new double[sumSPL][1];
            for (i = 0; i < sumSPL; i++) 
            {for (int j = 0; j < 1; j++){
                    for (int k = 0; k < sumSPL; k++)
                    {hasil[i][j] = hasil[i][j] + d[i][k] * constants[k][j];}}
            }
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Maka solusi sistem persamaan linear Anda adalah");
            System.out.println("-------------------------------------------------------------------------");
            for(i=0; i<sumSPL; i++)
            {
                System.out.println("X"+(i+1)+" = "+hasil[i][0] + " ");
                System.out.println("-------------------------------------------------------------------------");
            }
        }
    }
    
    //fungsi inverse matriks 
    public static double[][] invert(double thematriks[][]) 
    {
        int sumSPL = thematriks.length;
        int sumvar = thematriks.length;
        double a[][] = new double[sumSPL][sumvar];
        double b[][] = new double[sumSPL][sumvar];
        int index[] = new int[sumSPL];
        for (int i=0; i<sumSPL; ++i) 
            b[i][i] = 1;
        gaussian(thematriks, index);
        for (int i=0; i<sumSPL-1; ++i)
            for (int j=i+1; j<sumvar; ++j)
                for (int k=0; k<sumSPL; ++k)
                    b[index[j]][k]
                    	    -= thematriks[index[j]][i]*b[index[i]][k];
        for (int i=0; i<sumSPL; ++i) 
        {
            a[sumSPL-1][i] = b[index[sumSPL-1]][i]/thematriks[index[sumSPL-1]][sumvar-1];
            for (int j=sumSPL-2; j>=0; --j) 
            {
                a[j][i] = b[index[j]][i];
                for (int k=j+1; k<sumSPL; ++k) 
                {a[j][i] -= thematriks[index[j]][k]*a[k][i];}
                a[j][i] /= thematriks[index[j]][j];}}
        return a;
    }

    //fungsi eliminasi gauss
    public static void gaussian(double thematriks[][], int index[]) 
    {
        int sumSPL = index.length;
        int sumvar = thematriks.length;
        double c[] = new double[sumSPL];
        // Inisialisasi indeks
        for (int i=0; i<sumSPL; ++i) 
            index[i] = i;
        for (int i=0; i<sumSPL; ++i) {double c1 = 0;
            for (int j=0; j<sumvar; ++j) {double c0 = Math.abs(thematriks[i][j]);if (c0 > c1) c1 = c0;}
            c[i] = c1;}
        int k = 0;
        for (int j=0; j<sumvar-1; ++j) {
            double d1 = 0;
            for (int i=j; i<sumvar; ++i) {
                double d0 = Math.abs(thematriks[index[i]][j]);
                d0 /= c[index[i]];
                if (d0 > d1) 
                {d1 = d0;k = i;}}
            int tmp = index[j];
            index[j] = index[k];
            index[k] = tmp;
            for (int i=j+1; i<sumSPL; ++i) 	
            {
                double e = thematriks[index[i]][j]/thematriks[index[j]][j];
                thematriks[index[i]][j] = e;
                for (int l=j+1; l<sumSPL; ++l)
                    thematriks[index[i]][l] -= e*thematriks[index[j]][l];
            }
        }
    }

    //fungsi mencari determinan matriks
    public static double determinant(double thematriks[][],int sumSPL)
    {
        //membuat variabel det untuk menampung nilai determinan
        double det=0;
        if(sumSPL == 1){det = thematriks[0][0];}// jika matriks hanya memiliki satu element
        //jika matriks 2x2
        else if (sumSPL == 2){det = thematriks[0][0]*thematriks[1][1] - thematriks[1][0]*thematriks[0][1];}
        //jika matriks 3x3 atau lebih
        else
        {det=0;
            for(int j1 = 0; j1 < sumSPL; j1++)
            {
                double[][] newmatriks = new double[sumSPL-1][];
                for(int k=0;k<(sumSPL-1);k++){
                    newmatriks[k] = new double[sumSPL-1];
                    }
                for(int i=1;i<sumSPL;i++){
                    int j2=0;
                    for(int j=0;j<sumSPL;j++){
                        if(j == j1)
                            continue;
                        newmatriks[i-1][j2] = thematriks[i][j];
                        j2++;}}
                det += Math.pow(-1.0,1.0+j1+1.0)* thematriks[0][j1] * determinant(newmatriks,sumSPL-1);}
        }
        return det;
    }
    
    /*
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                MASUK KE BAGIAN DETERMINAN REDUKSI BARIS
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */
    public static void printMatrix(double[][] Matrix, int baris, int kolom) {
        System.out.println("Matrix yang sudah diinput adalah");

        for(int i=0; i<baris; i++) {
            for(int j=0; j<kolom; j++) {
                if (j == kolom-1) {
                    System.out.print(Matrix[i][j]);
                } 
                else {
                    System.out.print(Matrix[i][j]);
                    System.out.print(' ');
                }  
            }
            System.out.println();
        }
    }

    public static void masukanMatriks(Scanner scan, double Matrix[][], int baris) {
        // Menerima pasangan x, y
        System.out.print("Masukkan matriks" + "\n");
        for (int i=0;i<baris;i++) {
            for(int j=0;j<baris;j++) {
                Matrix[i][j] = scan.nextDouble();
            }
        }
    }

    public static void printmatriks(double[][] M, int row, int col) {
        System.out.println("Matriks yang dimasukkan");
        for (int i=0;i<row;i++) {
            for (int j=0;j<col;j++) {
                System.out.print(M[i][j]+"\t");
            }
            System.out.println();
        }
    }
    
    public static double determinantSegitigaAtas(double Matrix[][], int baris) {
        double det = 1;
        double swap = 1;
        for(int i=0;i<baris-1;i++) {
            for(int k=i+1;k<baris;k++) {
            //Pertukaran apabila mutlak elemen diagonal lebih kecil dari yang di bawahnya
                if (mutlak(Matrix[i][i]) < mutlak(Matrix[k][i])) {
                    tukarbaris(Matrix,i,k,baris);
                    swap = -swap;
                }
            }
            //Ke segitiga atas
            for(int k=i+1;k<baris;k++) {
                double ratio = (Matrix[k][i] / Matrix[i][i]);
                for(int j=0;j<baris;j++) {
                    Matrix[k][j] = Matrix[k][j] - (ratio*Matrix[i][j]);
                }
            }
        }
        for (int i=0;i<baris;i++) {
                det = det*Matrix[i][i];
            }
            return (det*swap);
    }

    public static double mutlak (double x) {
        if (x<0) {
            return -x; 
        }
        else {
            return x;
        }
    }

    public static void tukarbaris(double[][] Matrix, int idxbaris1, int idxbaris2, int kolom) {
        double[] temp = new double[kolom];

        //MENYIMPAN BARIS1 DI TEMP
        for(int j=0; j<kolom; j++) {
            temp[j] = Matrix[idxbaris1][j];
        }

        //MENUKAR BARIS1 MENJADI BARIS2
        for(int j=0; j<kolom; j++) {
            Matrix[idxbaris1][j] = Matrix[idxbaris2][j];
        }

        //MENUKAR BARIS2 MENJADI TEMP
        for(int j=0; j<kolom; j++) {
            Matrix[idxbaris2][j] = temp[j];
        }
    }

    /*
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                MASUK KE BAGIAN DETERMINAN COFACTOR
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

    public static void maindetcofactor() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan dimensi matriks" + "\n");
        int baris = scan.nextInt();
        double [][] Matrix = new double [baris][baris];
        masukanMatriks(scan,Matrix,baris);
        System.out.print("Determinan matriks sebesar ");
        System.out.print(determinant(Matrix,baris)); //determinant sudah terdefinisi di bagian lain
    }
    

    /*
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                MASUK KE BAGIAN INVERSE
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

    public static void maininverse() 
    {
        Scanner scan = new Scanner(System.in);
		
        int dimension1 = 1;
        int dimension2 = 2;
        
        //meminta user untuk menginputkan jumlah baris dan kolom yang sama
        //jika tidak maka program akan meminta ulang user untuk menginput nilai yang benar
		while(dimension1 != dimension2) {
            System.out.println("Masukan jumlah kolom dan baris harus sama!");
			try {
				System.out.print("Masukkan jumlah baris: ");
                dimension1 = scan.nextInt();
                System.out.print("Masukkan jumlah kolom: ");
                dimension2 = scan.nextInt();
			} catch (InputMismatchException ex) {
				scan.next();
			}
		}
		
		System.out.println("Done! Matriks Anda berukuran "+dimension1+"x"+dimension1);

        // Deklarasi tipe data matriks yaitu double
        double thematriks[][]= new double[dimension1][dimension1];
        int z=0;
        double sum=0, sum1=0, sum2=0, sum3=0;
        System.out.println("Input data matrix:");
        for (int i=0; i<dimension1; i++){
            for(int j=0;j<dimension1;j++){
                //System.out.print("Masukkan elemen matriks ke-["+i+"]["+j+"]: ");//meminta user untuk menginputkan matriks
                thematriks[i][j]= scan.nextDouble();

            }
        }
        //menampilkan matriks yang diinputkan oleh user
        System.out.println("Matriks Anda adalah ...");
        for(int i=0;i<dimension1;i++){
            for(int j=0;j<dimension1;j++){
                System.out.print(thematriks[i][j]+"  ");
            }
            System.out.println();
        }
        //melakukan validasi matriks merupakan matriks bujursangkar
        for(int i=0;i<dimension1;i++){
            for(int j=0;j<dimension1;j++){
                if(i==j){sum = sum+thematriks[i][j];}
                if(i+j==dimension1-1){sum1=sum1+thematriks[i][j];}}}
        if(sum!=sum1){z=1;}
        else{
            for(int i=0;i<dimension1;i++){
                sum2=0;
                sum3=0;
                for(int j=0;j<dimension1;j++){
                    sum2=sum2+thematriks[i][j];
                    sum3=sum3+thematriks[j][i];
                }if(sum2!=sum2){z=1;}
                else if(sum!=sum2){z=1;}}}
        //melakukan pengecekan apakah matriks invertible atau tidak
        functions_procedures Invrt = new functions_procedures();
        if(Invrt.determinant(thematriks, dimension1) == 0)
        {
            System.out.println("Matriks tidak invertible, karena determinannya adalah : "+Invrt.determinant(thematriks, dimension1));
        }
        else
        {
            System.out.println("Matriks invertible, karena determinannya adalah : "+Invrt.determinant(thematriks, dimension1));
            double newinverse[][] = invert(thematriks);
            System.out.println("Invers dari matriks Anda: ");
            for (int i=0; i<dimension1; ++i) 
            {
                for (int j=0; j<dimension1; ++j)
                {
                    System.out.print(newinverse[i][j]+"  ");
                }
                System.out.println();
            }
        }
    }
}