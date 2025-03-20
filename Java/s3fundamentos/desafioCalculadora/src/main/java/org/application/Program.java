package org.application;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        //Exercício para a conversão de Fahrenheit  para Celsius utilizando os conceitos aprendidos na seção de Fundamentos.

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        double fahrenheit;

        System.out.println("=======CONVERSÃO DE FAHRENHEIT PARA CELSIUS=======");

        System.out.print("Digite a temperatura em Fahrenheit: ");
        fahrenheit = sc.nextDouble();

        double celsius = (fahrenheit - 32) / 1.8;

        System.out.printf("A temperatura em Celsius é: %.4f°", celsius);

        sc.close();
    }
}