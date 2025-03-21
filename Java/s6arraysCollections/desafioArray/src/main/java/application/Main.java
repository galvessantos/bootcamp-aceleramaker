package application;

import java.util.Locale;
import java.util.Scanner;

//Nesse exercício, tivemos que utilizar apenas o Array para executar a lógica de quantidade de notas digitadas e média dessas notas.

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        int qtdNotas;

        System.out.print("Digite a quantidade de notas: ");
        qtdNotas = sc.nextInt();

        double[] arrayNota = new double[qtdNotas];

        double nota;

        for (int i=0; i < arrayNota.length; i++) {
            System.out.print("Digite a nota " + (i+1) + ": ");
            arrayNota[i] = sc.nextDouble();

        }

        double total = 0;
        for (double notas : arrayNota) {
            total+= notas;
        }

        double media = total / arrayNota.length;

        System.out.println("A média das notas é: " + media);

        sc.close();
    }
}