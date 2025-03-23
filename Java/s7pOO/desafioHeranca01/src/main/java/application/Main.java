package application;

import java.util.Locale;

//Nesse desafio, a proposta é realizarmos um teste de herança baseado na classe genérica Carro.
//Utilizamos os atributos da classe Carro com as peculiaridades de cada carro, como a força de aceleração.
//Nessa classe Main, busquei mostrar os exemplos de funcionamento do carro acelerando e freiando.
//Busquei utilizar também os exemplos de encapsulamento e uso dos getters e setters.

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Carro carro1 = new Ferrari();
        Carro carro2 = new Civic();

        System.out.println("\n=====FERRARI=====");

        carro1.acelerar();
        carro1.acelerar();
        carro1.acelerar();

        System.out.println("Velocidade atual: " + String.format("%.2f", carro1.getVelocidadeAtual()) + " KM/h.");

        carro1.frear();

        System.out.println("Velocidade atual: " + String.format("%.2f", carro1.getVelocidadeAtual()) + " KM/h.");

        System.out.println("\n=====CIVIC=====");

        carro2.acelerar();
        carro2.acelerar();
        carro2.acelerar();

        System.out.println("Velocidade atual: " + String.format("%.2f", carro2.getVelocidadeAtual()) + " KM/h.");

        carro2.frear();

        System.out.println("Velocidade atual: " + String.format("%.2f", carro2.getVelocidadeAtual()) + " KM/h.");
    }
}