package org.application;

import java.util.Scanner;

public class Finally {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        try {
            System.out.println(7 / sc.nextInt());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Finalmente...");
            sc.close();
        }
    }
}
