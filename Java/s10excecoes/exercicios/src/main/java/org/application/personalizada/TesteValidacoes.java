package org.application.personalizada;

import org.application.Aluno;

public class TesteValidacoes {

    public static void main(String[] args) {

        try {
            Aluno aluno = new Aluno("Anna", 10, -7);

            Validar.aluno(aluno);
        } catch (StringVaziaException | NumeroForaIntervaloException e) {
            System.out.println(e.getMessage());
        }
    }
}
