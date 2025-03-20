package org.application;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        //Exercício que liga o dia da semana a um número utilizando os conceitos aprendidos na seção de Estruturas de Controle.

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        String diaSemana;
        int num;

        System.out.println("=======TRANSFORMANDO O DIA DA SEMANA EM UM NÚMERO=======");

        System.out.print("Digite um dia da semana (Ex: Quarta) : ");
        diaSemana = sc.nextLine();

        if (diaSemana.equalsIgnoreCase("domingo")) {
            System.out.println("Você digitou o 1° dia da semana!");
        }
        else if (diaSemana.equalsIgnoreCase("segunda")) {
            System.out.println("Você digitou o 2° dia da semana!");
        }
        else if (diaSemana.equalsIgnoreCase("terça")) {
            System.out.println("Você digitou o 3° dia da semana!");
        }
        else if (diaSemana.equalsIgnoreCase("quarta")) {
            System.out.println("Você digitou o 4° dia da semana!");
        }
        else if (diaSemana.equalsIgnoreCase("quinta")) {
            System.out.println("Você digitou o 5° dia da semana!");
        }
        else if (diaSemana.equalsIgnoreCase("sexta")) {
            System.out.println("Você digitou o 6° dia da semana!");
        }
        else if (diaSemana.equalsIgnoreCase("sábado")) {
            System.out.println("Você digitou o 7° dia da semana!");
        }

        sc.close();
    }
}