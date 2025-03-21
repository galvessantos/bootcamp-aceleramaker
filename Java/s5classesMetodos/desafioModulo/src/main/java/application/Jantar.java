package application;

import java.util.Locale;
import java.util.Scanner;

public class Jantar {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Scanner sc= new Scanner(System.in);

        System.out.print("Digite seu nome: ");
        String name = sc.nextLine();
        System.out.print("Digite seu peso: ");
        double peso = sc.nextDouble();
        Pessoa pessoa = new Pessoa(name, peso);

        char resposta;
        do {
            Comida comida = Comida.adicionarComida(sc);
            pessoa.comer(comida);

            do {
                System.out.print("Deseja adicionar mais um alimento? (S/N): ");
                resposta = sc.next().toUpperCase().charAt(0);
            } while (resposta != 'S' && resposta != 'N');

        } while (resposta != 'N');

        System.out.println(pessoa.toString());

        sc.close();
    }
}