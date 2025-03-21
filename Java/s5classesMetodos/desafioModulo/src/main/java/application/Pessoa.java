package application;

public class Pessoa {

    String nome;
    double peso;

    public Pessoa(String nome, double peso) {
        this.nome = nome;
        this.peso = peso;
    }

    void comer(Comida comida) {
        if(comida != null) {
            this.peso+= (comida.calorias * 0.001);
        }
    }

    @Override
    public String toString() {
        return "Olá! Eu sou o " + nome + " e após toda essa comilança, peso " + peso + " kgs. :)";
    }
}
