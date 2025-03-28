package org.application;

public class Basico {
    public static void main(String[] args) {

        Aluno a1 = null;

        try {
            imprimirNomeDoAluno(a1); // Erro ao tentar imprimir um aluno nulo.
        } catch (Exception excecao) {
            System.out.println("Ocorreu um erro no momento de imprimir o nome do usuário.");
        }

        try {
            System.out.println(7 / 0);  // Erro de divisão por zero.
        } catch (ArithmeticException e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }


        System.out.println("Fim"); // Caso os erros acima não sejam tratados, a palavra Fim não exibe, já que os erros interrompem o programa.
    }

    public static void imprimirNomeDoAluno (Aluno aluno) {
        System.out.println(aluno.nome);
    }
}