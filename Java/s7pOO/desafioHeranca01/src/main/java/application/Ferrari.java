package application;

public class Ferrari extends Carro{

    @Override
    public void acelerar() {
        setVelocidadeAtual(getVelocidadeAtual() + 15);
    }

    @Override
    public void frear() {
        if (getVelocidadeAtual() > 0) {
            setVelocidadeAtual(getVelocidadeAtual() - 15);
        }
        else {setVelocidadeAtual(0);}
    }
}
