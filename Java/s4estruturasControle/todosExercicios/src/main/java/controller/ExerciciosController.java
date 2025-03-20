package controller;

import service.ExerciciosService;

public class ExerciciosController {
    private final ExerciciosService exerciciosService = new ExerciciosService();

    public void executarExercicios(int opcao) {
        switch (opcao) {
            case 1 -> exerciciosService.exercicio1();
            case 2 -> exerciciosService.exercicio2();
            case 3 -> exerciciosService.exercicio3();
            case 4 -> exerciciosService.exercicio4();
            case 5 -> exerciciosService.exercicio5();
            case 6 -> exerciciosService.exercicio6();
            case 7 -> exerciciosService.exercicio7();
            case 8 -> exerciciosService.exercicio8();
        }
    }
}
