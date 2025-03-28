package org.application;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/*
 * Nesse desafio, tive que converter um número para uma string binária (Ex: 6 => "110"
 * Também tive que inverter o resultado desse toBinaryString (Ex: "110" => "011"
 * E por fim, converter o número invertido novamente para inteiro (Ex: "011" => 3
 */

public class Desafio {
    public static void main(String[] args) {

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        UnaryOperator<String> inverter =
                s -> new StringBuilder(s).reverse().toString();

        Function<String, Integer> binarioParaInt =
                s -> Integer.parseInt(s, 2);

        nums.stream()
                .map(Integer::toBinaryString)
                .map(inverter)
                .map(binarioParaInt)
                .forEach(System.out::println);

    }
}