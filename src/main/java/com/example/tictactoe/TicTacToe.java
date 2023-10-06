package com.example.tictactoe;

import java.util.Scanner;

public class TicTacToe {
    private final char[][] board;
    private char currentPlayer;

    public TicTacToe() {
        board = new char[10][10];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = ' ';
            }
        }
    }

    private void printBoard() {
        System.out.println("  0   1   2   3   4   5   6   7   8   9");
        System.out.println(" ---------------------------------------");

        for (int row = 0; row < 10; row++) {
            System.out.print(row + "|");

            for (int col = 0; col < 10; col++) {
                System.out.print(" " + board[row][col] + " |");
            }

            System.out.println("\n ---------------------------------------");
        }

        System.out.println();
    }

    private boolean makeMove(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10 && board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] == currentPlayer &&
                        board[row][col + 1] == currentPlayer &&
                        board[row][col + 2] == currentPlayer &&
                        board[row][col + 3] == currentPlayer &&
                        board[row][col + 4] == currentPlayer) {
                    return true;
                }
            }
        }

        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 6; row++) {
                if (board[row][col] == currentPlayer &&
                        board[row + 1][col] == currentPlayer &&
                        board[row + 2][col] == currentPlayer &&
                        board[row + 3][col] == currentPlayer &&
                        board[row + 4][col] == currentPlayer) {
                    return true;
                }
            }
        }
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] == currentPlayer &&
                        board[row + 1][col + 1] == currentPlayer &&
                        board[row + 2][col + 2] == currentPlayer &&
                        board[row + 3][col + 3] == currentPlayer &&
                        board[row + 4][col + 4] == currentPlayer) {
                    return true;
                }
            }
        }
        for (int row = 0; row < 6; row++) {
            for (int col = 9; col > 3; col--) {
                if (board[row][col] == currentPlayer &&
                        board[row + 1][col - 1] == currentPlayer &&
                        board[row + 2][col - 2] == currentPlayer &&
                        board[row + 3][col - 3] == currentPlayer &&
                        board[row + 4][col - 4] == currentPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private int minimax(char[][] currentBoard, int depth, boolean isMaximizing) {
        char opponent = (currentPlayer == 'X') ? 'O' : 'X';

        if (checkWin() && currentPlayer == 'O') {
            return -1;
        } else if (checkWin() && currentPlayer == 'X') {
            return 1;
        } else if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;

            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (currentBoard[row][col] == ' ') {
                        currentBoard[row][col] = currentPlayer;
                        int score = minimax(currentBoard, depth + 1, false);
                        currentBoard[row][col] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (currentBoard[row][col] == ' ') {
                        currentBoard[row][col] = opponent;
                        int score = minimax(currentBoard, depth + 1, true);
                        currentBoard[row][col] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }

            return bestScore;
        }
    }

    private void makeComputerMove() {
        char[][] currentBoard = new char[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                currentBoard[row][col] = board[row][col];
            }
        }

        int bestMoveScore = Integer.MIN_VALUE;
        int bestMoveRow = -1;
        int bestMoveCol = -1;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (currentBoard[row][col] == ' ') {
                    currentBoard[row][col] = currentPlayer;
                    int moveScore = minimax(currentBoard, 0, false);
                    currentBoard[row][col] = ' ';

                    if (moveScore > bestMoveScore) {
                        bestMoveScore = moveScore;
                        bestMoveRow = row;
                        bestMoveCol = col;
                    }
                }
            }
        }

        board[bestMoveRow][bestMoveCol] = currentPlayer;
        System.out.println("Komputer wybrał pole: " + bestMoveRow + ", " + bestMoveCol);
    }

    public void playGameWithComputer() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        System.out.println("Witaj w grze Kółko i Krzyżyk 10x10!");
        System.out.println("Czy chcesz grać z komputerem? (Tak/Nie)");

        String choice = scanner.nextLine();
        boolean playWithComputer = choice.equalsIgnoreCase("Tak");

        while (!gameOver) {
            System.out.println("Gracz " + currentPlayer + ", podaj współrzędne ruchu (wiersz i kolumna oddzielone spacją):");

            if (!playWithComputer || currentPlayer == 'X') {
                int row, col;
                if (scanner.hasNextInt()) {
                    row = scanner.nextInt();
                } else {
                    System.out.println("Nieprawidłowy ruch. Podaj współrzędne liczbowe.");
                    scanner.next();
                    continue;
                }

                if (scanner.hasNextInt()) {
                    col = scanner.nextInt();
                } else {
                    System.out.println("Nieprawidłowy ruch. Podaj współrzędne liczbowe.");
                    scanner.next();
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