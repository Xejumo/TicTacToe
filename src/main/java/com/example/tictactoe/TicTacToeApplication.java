package com.example.tictactoe;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicTacToeApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz rozmiar planszy (3x3 lub 10x10):");
        int sizeChoice = scanner.nextInt();
        TicTacToe game = new TicTacToe(sizeChoice);

        game.playGameWithComputer();

        scanner.close();
    }

}

