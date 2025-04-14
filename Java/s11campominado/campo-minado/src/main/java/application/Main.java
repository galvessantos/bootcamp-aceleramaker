package application;

import model.Tabuleiro;
import view.TabuleiroView;

import javax.swing.text.TabableView;

public class Main {

    public static void main(String[] args) {

        Tabuleiro tabuleiro= new Tabuleiro(6, 6, 6);
        new TabuleiroView(tabuleiro);
    }
}
