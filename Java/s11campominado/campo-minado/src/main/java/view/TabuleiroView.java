package view;

import exceptions.ExplosaoException;
import exceptions.SairException;
import model.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroView {

    private Tabuleiro tabuleiro;
    private Scanner sc = new Scanner(System.in);

    public TabuleiroView(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        
        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar) {
                cicloDoJogo();

                System.out.println("Deseja jogar outra partida? (S/N): ");
                char resposta = sc.next().charAt(0);

                if ("n".equalsIgnoreCase(String.valueOf(resposta))) {
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }
        } catch (SairException e) {
            System.out.println("Até logo!");
        } finally {
            sc.close();
        }
    }

    private void cicloDoJogo() {
        try {
            while(!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro);

                String digitado = capturarValorDigitado("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - Abrir | 2 - Desmarcar");

                if("1".equals(digitado)) {
                    tabuleiro.abrir(xy.next(), xy.next());
                }
                else if ("2".equals(digitado)) {
                    tabuleiro.alterarMarcacao(xy.next(), xy.next());
                }
            }
            System.out.println(tabuleiro);
            System.out.println("Você ganhou!");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro);
            System.out.println("Você perdeu!");
        }
    }

    private String capturarValorDigitado (String texto) {
        System.out.println(texto);
        String digitado = sc.nextLine();

        if("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }
        return digitado;
    }
}
