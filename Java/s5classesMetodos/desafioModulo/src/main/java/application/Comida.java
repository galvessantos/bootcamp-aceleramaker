package application;

import java.util.Scanner;

public class Comida {

    String nome;
    double calorias;

    public Comida(String nome, double calorias) {
        this.nome = nome;
        this.calorias = calorias;
    }

    public static Comida adicionarComida(Scanner sc) {
        sc.nextLine();
        System.out.print("Digite o nome do alimento que você comeu: ");
        String nomeAlimento = sc.nextLine();
        System.out.print("Digite a quantidade de calorias: ");
        double calorias = sc.nextDouble();
        return new Comida(nomeAlimento, calorias);
    }
}
