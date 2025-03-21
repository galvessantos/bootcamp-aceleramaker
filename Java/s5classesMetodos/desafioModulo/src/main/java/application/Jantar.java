package application;

import java.util.Locale;
import java.util.Scanner;

    /*
      Decidi adicionar ao exercício:

       1. Getters e Setters para encapsular os atributos das classes.
       2. Interação com o usuário para permitir entradas dinâmicas de nome, peso, alimentos e calorias.
       3. O uso do laço do-while para repetir a ação de adicionar alimentos até o usuário decidir parar.

       Essas mudanças têm como objetivo aumentar a complexidade do exercício, ao mesmo tempo em que pratico conceitos vistos nas seções anteriores.
    */

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