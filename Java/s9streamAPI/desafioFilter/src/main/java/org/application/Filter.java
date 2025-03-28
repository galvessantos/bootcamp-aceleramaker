package org.application;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/*
 * Desafio: Criar um sistema de filtragem de casas para aluguel, onde as casas
 * são classificadas em três categorias: "Excelente negócio", "Bom negócio" e
 * "Mal negócio".
 *
 * O código utiliza predicados para aplicar filtros baseados em critérios como
 * valor de aluguel, quantidade de quartos e presença de piscina. As casas que
 * atendem a esses critérios são então exibidas no console.
 */


public class Filter {
    public static void main(String[] args) {

        Casa c1 = new Casa("Rua Maria Eugenia, 45", 750.00, 2, false);
        Casa c2 = new Casa("Rua Joao Augusto, 69", 1250.00, 3, true);
        Casa c3 = new Casa("Rua Gabriel Alves, 10", 2700.00, 5, false);
        Casa c4 = new Casa("Rua Rildo Aparecido, 23", 1500.00, 3, false);
        Casa c5 = new Casa("Rua Dalete Cristina, 50", 1100.00, 2, true);
        Casa c6 = new Casa("Rua Anna Julia, 100", 1800.00, 3, false);
        Casa c7 = new Casa("Rua Jonas Lima, 92", 3000.00, 2, false);

        List<Casa> casasAluguel = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);

        Predicate<Casa> excelenteNegocio = c -> c.valorAluguel <= 1500 && c.piscina && c.quantidadeQuartos >= 2;
        Predicate<Casa> bomNegocio = c -> c.valorAluguel <= 1800 && c.piscina && c.quantidadeQuartos >= 3;
        Predicate<Casa> malNegocio = c -> c.valorAluguel >= 2500 && !c.piscina && c.quantidadeQuartos <= 5;

        casasAluguel.stream()
                .filter(malNegocio)
                .forEach(System.out::println);

    }
}