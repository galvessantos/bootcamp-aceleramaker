package application;

import java.util.Locale;

public class Sistema {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Compra compra1 = new Compra();
        compra1.adicionarItem("Livro", 50, 1);
        compra1.adicionarItem("Garrafa", 39.99, 1);

        Compra compra2 = new Compra();
        compra2.adicionarItem("Chocolate", 9.99, 3);
        compra2.adicionarItem("Salgadinho", 6.99, 5);

        Cliente cliente = new Cliente("Anna Julia");
        cliente.adicionarCompra(compra1);
        cliente.adicionarCompra(compra2);

        System.out.println(String.format("O total comprado por " + cliente.nome + "  foi de: %.2f", cliente.obterValorTotal()));
    }
}