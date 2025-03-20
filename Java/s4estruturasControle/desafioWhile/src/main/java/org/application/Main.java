package org.application;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Exercício de cálculo de notas utilizando os conceitos aprendidos na seção de Estruturas de Controle.
        // Como achei o exercício muito simples, adicionei algumas implementações que não havíamos aprendido no módulo.

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        char resposta;
        int qtdNotas = 0;
        double total = 0;
        double nota = 0;

        System.out.print("Deseja calcular a nota? (S/N) ");
        resposta = sc.next().charAt(0);

        while (Character.toUpperCase(resposta) == 'S') {
            System.out.print("Informe a nota: ");
            nota = sc.nextDouble();

            if (nota <= 10 && nota >= 0) {
                qtdNotas++;
                total+= nota;

                System.out.print("Deseja fazer o cálculo de mais uma nota? (S/N) ");
                resposta = sc.next().charAt(0);
            }
            else {
                System.out.println("Digite uma nota válida.");
            }
        }

        double media = total / qtdNotas;

        if (media >= 0) {
            System.out.println("A média é: " + media);
        }

        System.out.printf("Você calculou %d nota(s).", qtdNotas);


        sc.close();
    }
}