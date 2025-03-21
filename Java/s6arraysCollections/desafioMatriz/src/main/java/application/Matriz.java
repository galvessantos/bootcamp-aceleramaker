package application;

import java.util.Locale;
import java.util.Scanner;

 //Apesar de não ser um desafio, quis pausar a aula e praticar o exemplo do professor para relembrar o uso de matriz. 

public class Matriz {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        System.out.print("Digite a quantidade de alunos: ");
        int qtdAlunos = sc.nextInt();

        System.out.print("Digite a quantidade de notas por aluno: ");
        int qtdNotas = sc.nextInt();

        double[] [] notasTurma = new double [qtdAlunos] [qtdNotas];

        double total = 0;
        for (int a=0; a < notasTurma.length; a++) {
            for (int n=0; n < notasTurma[a].length; n++) {

                System.out.printf("Informe a nota %d do aluno %d: ", n + 1, a + 1);
                notasTurma[a] [n] = sc.nextDouble();
                total += notasTurma[a] [n];
            }
        }

        double media = total / (qtdAlunos * qtdNotas);
        System.out.println("A média da turma é: " + media);

        sc.close();
    }
}