package org.application;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        //Exercício para calculo de IMC utilizando os conceitos aprendidos na seção de Fundamentos.

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        double peso, altura;

        System.out.println("=======CÁLCULO DE IMC=======");

        System.out.print("Digite a sua altura em metros (X.XX): ");
        altura = sc.nextDouble();
        System.out.print("Digite seu peso em kg (XX.XX): ");
        peso = sc.nextDouble();

        double imc = peso / (altura * altura);

        System.out.printf("Seu IMC é de %.2f", imc);

        sc.close();
    }
}