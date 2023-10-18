package com.example.tictactoe;

import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
    private final char[][] board;
    private char currentPlayer;
    private int boardSize;
    private int winCondition;

    public TicTacToe(int boardSize) {
        this.boardSize = boardSize;
        this.winCondition = (boardSize == 3) ? 3 : 5;
        board = new char[boardSize][boardSize];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = ' ';
            }
        }
    }

    private void printBoard() {
        System.out.print(" ");
        for (int col = 0; col < boardSize; col++) {
            System.out.print("   " + col);
        }
        System.out.println();

        for (int row = 0; row < boardSize; row++) {
            System.out.print(row + "|");

            for (int col = 0; col < boardSize; col++) {
                System.out.print(" " + board[row][col] + " |");
            }

            System.out.println("\n ");
        }

        System.out.println();
    }

    private boolean makeMove(int row, int col) {
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (col + winCondition - 1 < boardSize) {
                    boolean horizontalWin = true;
                    for (int i = 0; i < winCondition; i++) {
                        if (board[row][col + i] != currentPlayer) {
                            horizontalWin = false;
                            break;
                        }
                    }
                    if (horizontalWin) {
                        return true;
                    }
                }
                if (row + winCondition - 1 < boardSize) {
                    boolean verticalWin = true;
                    for (int i = 0; i < winCondition; i++) {
                        if (board[row + i][col] != currentPlayer) {
                            verticalWin = false;
                            break;
                        }
                    }
                    if (verticalWin) {
                        return true;
                    }
                }
                if (row + winCondition - 1 < boardSize && col + winCondition - 1 < boardSize) {
                    boolean diagonalWin = true;
                    for (int i = 0; i < winCondition; i++) {
                        if (board[row + i][col + i] != currentPlayer) {
                            diagonalWin = false;
                            break;
                        }
                    }
                    if (diagonalWin) {
                        return true;
                    }
                }
                if (row + winCondition - 1 < boardSize && col - winCondition + 1 >= 0) {
                    boolean diagonalWin = true;
                    for (int i = 0; i < winCondition; i++) {
                        if (board[row + i][col - i] != currentPlayer) {
                            diagonalWin = false;
                            break;
                        }
                    }
                    if (diagonalWin) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void makeComputerMove() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
        } while (!makeMove(row, col));

        System.out.println("Komputer wybrał pole: " + row + ", " + col);
    }

    public void playGameWithComputer() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        System.out.println("Witaj w grze Kółko i Krzyżyk " + boardSize + "x" + boardSize + "!");
        System.out.println("Czy chcesz grać z komputerem? (Tak/Nie)");

        String choice = scanner.nextLine();
        boolean playWithComputer = choice.equalsIgnoreCase("Tak");

        while (!gameOver) {
            System.out.println("Gracz " + currentPlayer + ", podaj współrzędne ruchu (wiersz i kolumna oddzielone spacją):");

            if (!playWithComputer || currentPlayer == 'X') {
                int row, col;
                if (scanner.hasNextInt()) {
                    row = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                    } else {
                        System.out.println("Nieprawidłowy ruch. Podaj współrzędne liczbowe.");
                        scanner = new Scanner(System.in);
                        continue;
                    }
                    } else {
                        System.out.println("Nieprawidłowy ruch. Podaj współrzędne liczbowe.");
                        scanner = new Scanner(System.in);
                        continue;
                    }

                if (makeMove(row, col)) {
                    printBoard();

                    if (checkWin()) {
                        System.out.println("Gracz " + currentPlayer + " wygrał!");
                        gameOver = true;
                    } else if (isBoardFull()) {
                        System.out.println("Remis!");
                        gameOver = true;
                    } else {
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    }
                } else {
                    System.out.println("Nieprawidłowy ruch. Spróbuj ponownie.");
                }
            } else if (playWithComputer && currentPlayer == 'O') {
                makeComputerMove();
                printBoard();

                if (checkWin()) {
                    System.out.println("Komputer wygrał!");
                    gameOver = true;
                } else if (isBoardFull()) {
                    System.out.println("Remis!");
                    gameOver = true;
                } else {
                    currentPlayer = 'X';
                }
            }
        }

        scanner.close();
    }
}