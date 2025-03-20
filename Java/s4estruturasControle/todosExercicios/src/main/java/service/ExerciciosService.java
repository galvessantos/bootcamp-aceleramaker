package service;

import java.util.Random;
import java.util.Scanner;

public class ExerciciosService {

    private final Scanner sc = new Scanner(System.in);

    public void exercicio1() {

        System.out.println("\n=====EXERCÍCIO 01 - VERIFICAÇÃO DE NÚMERO=====");
        System.out.print("Digite um número: ");
        int num = sc.nextInt();

        if (num >=0 && num <=10 && num % 2 == 0) {
            System.out.println("O número está entre 0 e 10 e é par.");
        }
        else {
            System.out.println("O número que você digitou não é par ou não está entre 0 e 10.");
        }
    }

    public void exercicio2() {

        System.out.println("\n======EXERCÍCIO 02 - VERIFICAÇÃO DE ANO BISSEXTO=====");
        System.out.print("Digite o ano: ");
        int ano = sc.nextInt();
        if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
            System.out.println("Ano bissexto.");
        }
        else {
            System.out.println("Não é ano bissexto.");
        }
    }

    public void exercicio3() {

        System.out.println("\n======EXERCÍCIO 03 - VERIFICAÇÃO DE NOTA=====");
        System.out.println("Digite o primeiro número: ");
        double num1 = sc.nextDouble();
        System.out.println("Digite o segundo número: ");
        double num2 = sc.nextDouble();

        double media = (num1 + num2) / 2;

        if (media >= 7) {
            System.out.printf("Parabéns! Você foi aprovado! Sua média foi: %.2f\n", media);
        }
        else if (media < 7 && media > 4) {
            System.out.printf("Infelizmente você está de recuperação. Sua média foi: %.2f\n", media);
        }
        else {
            System.out.printf("Infelizmente você foi reprovado. Sua média foi: %.2f\n", media);
        }
    }

    public void exercicio4() {

        System.out.println("\n======EXERCÍCIO 04 - VERIFICAÇÃO DE NÚMERO PRIMO=====");
        System.out.print("Digite um número: ");
        int num = sc.nextInt();

        boolean primo = num > 1;

        for (int i=2; i<num; i++) {
            if (num % i == 0) {
                primo = false;
                break;
            }
        }

        System.out.println(primo ? "O número é primo" : "O número não é primo");
    }

    public void exercicio5() {

        System.out.println("\n======EXERCÍCIO 05 - VERIFICAÇÃO DE NÚMERO PRIMO (SWITCH)=====");
        System.out.println("Digite um número: ");
        int num = sc.nextInt();

        boolean primo = num > 1;

        switch (num) {
            case 0, 1 -> primo = false;
            case 2, 3 -> primo = true;
            default -> {
                for (int i = 2; i < num; i++) {
                    if (num % i == 0) {
                        primo = false;
                        break;
                    }
                }
            }
        }

        System.out.println(primo ? "É primo." : "Não é primo.");
    }

    public void exercicio6() {

        System.out.println("\n======EXERCÍCIO 06 - JOGO DA ADIVINHAÇÃO=====");
        Random random = new Random();
        int numeroSecreto = random.nextInt(101);
        int tentativas = 10;

        System.out.println("Tente adivinhar o número entre 0 e 100!");

        while (tentativas > 0) {
            System.out.print("Digite seu palpite: ");
            int palpite = sc.nextInt();
            tentativas--;

            if (palpite == numeroSecreto) {
                System.out.println("Parabéns! Você acertou.");
                return;
            }
            else if (palpite < numeroSecreto) {
                System.out.println("O número é maior. Tentativas restantes: " + tentativas);
            }
            else {
                System.out.println("O número é menor. Tentativas restantes: " + tentativas);
            }
        }

        System.out.println("Acabaram as tentativas! O número era " + numeroSecreto);
    }

    public void exercicio7() {

        System.out.println("\n======EXERCÍCIO 07 - NUMEROS POSITIVOS=====");

        int num;
        int soma = 0;

        do {
            System.out.print("Digite um número positivo: ");
            num = sc.nextInt();

            if (num >= 0) {
                soma += num;
                System.out.println("A soma dos números positivos é: " + soma);
            }
        } while (num >= 0);

        System.out.println("Fim!");
    }

    public void exercicio8() {

        System.out.println("\n======EXERCÍCIO 08 - MAIOR NUMERO=====");

        int maior = Integer.MIN_VALUE;
        System.out.println("Digite 10 números: ");

        for (int i=1; i <= 10; i++) {
            System.out.print("Número " + i + ": ");
            int num = sc.nextInt();
            if (num > maior) {
                maior = num;
            }
        }

        System.out.println("O maior número digitado é: " + maior);
    }
}
