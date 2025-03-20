package org.application;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        //Exercício para criação de uma Calculadora utilizando apenas os comandos aprendidos na seção Fundamentos.

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        double num1, num2, result;
        String op;

        System.out.print("Insira o primeiro número: ");
        num1 = sc.nextDouble();
        System.out.print("Insira o segundo número: ");
        num2 = sc.nextDouble();
        System.out.print("\nSOMA: + \nSUBTRAÇÃO: - \nDIVISÃO: / \nMULTIPLICAÇÃO: * \nMÓDULO: %  \n\nInsira o tipo de operação: ");
        op = sc.next();

        result = "+".equals(op) ? num1 + num2 : 0;
        result = "-".equals(op) ? num1 - num2 : result;
        result = "/".equals(op) ? num1 / num2 : result;
        result = "*".equals(op) ? num1 * num2 : result;
        result = "%".equals(op) ? num1 % num2 : result;

        System.out.printf("%.2f %s %.2f = %.2f", num1, op, num2, result);

        sc.close();
    }
}