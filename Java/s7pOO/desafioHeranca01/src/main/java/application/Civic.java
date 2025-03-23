package application;

public class Civic extends Carro{

    @Override
    public void acelerar() {
        setVelocidadeAtual(getVelocidadeAtual() + 5);
    }

    @Override
    public void frear() {
        if (getVelocidadeAtual() > 0) {
            setVelocidadeAtual(getVelocidadeAtual() - 5);
        }
        else {setVelocidadeAtual(0);}
    }
}

