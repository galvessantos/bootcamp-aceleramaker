package application;

public class Program {
    public static void main(String[] args) {

        //Pessoa instanciada com seu peso atual.
        Pessoa convidado = new Pessoa(80.00);

        //Comidas instanciadas com seus respectivos pesos.
        Arroz ingrediente1 = new Arroz(0.2);
        Feijao ingrediente2 = new Feijao(0.1);
        Sorvete sobremesa = new Sorvete(0.2);

        //Mostrando o peso do convidado sem comer.
        System.out.println(convidado.getPeso());

        //Chamando o método comer da classe Pessoa que adiciona o peso do alimento ao peso do convidado.
        convidado.comer(ingrediente1);
        convidado.comer(ingrediente2);
        convidado.comer(sobremesa);

        //Mostrando o peso do convidado após comer.
        System.out.println(convidado.getPeso());
    }
}