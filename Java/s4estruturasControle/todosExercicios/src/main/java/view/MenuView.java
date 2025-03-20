package view;

import controller.ExerciciosController;

import java.util.Scanner;

public class MenuView {

    private final Scanner scanner = new Scanner(System.in);
    private final ExerciciosController controller = new ExerciciosController();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n===== MENU DE EXERCÍCIOS =====");
            for (int i = 1; i <= 8; i++) {
                System.out.println(i + ". Exercício " + i);
            }
            System.out.println("9. Sair");
            System.out.print("\nEscolha uma opção: ");

            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= 8) {
                controller.executarExercicios(opcao);
            } else if (opcao != 9) {
                System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 9);

        System.out.println("Fechando...");
        scanner.close();
    }
}
