package org.application;

public class Casa {

    String endereco;
    double valorAluguel;
    int quantidadeQuartos;
    boolean piscina;

    public Casa(String endereco, double valorAluguel, int quantidadeQuartos, boolean piscina) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
        this.quantidadeQuartos = quantidadeQuartos;
        this.piscina = piscina;
    }

    @Override
    public String toString() {
        return "Casa: " + endereco + " | Valor do aluguel: " + valorAluguel +
                " | Quantidade de quartos: " + quantidadeQuartos +
                " | Piscina: " + (piscina ? "Sim" : "Não");
    }
}
