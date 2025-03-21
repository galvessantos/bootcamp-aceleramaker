package application;

public class Pessoa {

    private String nome;
    private double peso;

    public Pessoa(String nome, double peso) {
        this.nome = nome;
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    void comer(Comida comida) {
        if(comida != null) {
            this.peso+= (comida.getCalorias() * 0.001);
        }
    }

    @Override
    public String toString() {
        return "Olá! Eu sou o " + nome + " e após toda essa comilança, peso " + peso + " kgs. :)";
    }
}
