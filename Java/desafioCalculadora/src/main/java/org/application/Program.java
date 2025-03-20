package org.application;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        double num1, num2, resultado;
        String op;

        System.out.print("Insira o primeiro número: ");
        num1 = sc.nextDouble();
        System.out.print("Insira o segundo número: ");
        num2 = sc.nextDouble();
        System.out.print("\nSOMA: + \nSUBTRAÇÃO: - \nDIVISÃO: / \nMULTIPLICAÇÃO: * \nMÓDULO: %  \n\nInsira o tipo de operação: ");
        op = sc.next();

        resultado = "+".equals(op) ? num1 + num2 : 0;
        resultado = "-".equals(op) ? num1 - num2 : resultado;
        resultado = "/".equals(op) ? num1 / num2 : resultado;
        resultado = "*".equals(op) ? num1 * num2 : resultado;
        resultado = "%".equals(op) ? num1 % num2 : resultado;

        System.out.printf("%.2f %s %.2f = %.2f", num1, op, num2, resultado);

        sc.close();
    }
}