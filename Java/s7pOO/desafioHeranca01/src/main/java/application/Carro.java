package application;

public abstract class Carro {

    private double velocidadeAtual = 0;

    public abstract void acelerar();

    public abstract void frear();

    public double getVelocidadeAtual() {
        return velocidadeAtual;
    }

    public void setVelocidadeAtual(double velocidadeAtual) {
        this.velocidadeAtual = velocidadeAtual;
    }
}
