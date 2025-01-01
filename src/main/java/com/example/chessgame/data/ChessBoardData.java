package com.example.chessgame.data;

import com.example.chessgame.pieces.*;

public class ChessBoardData {


    private final Piece[][] chessBoard = createChessBoard();
    private char turn = 'w';

    public char getTurn() {
        return turn;
    }

    public void nextTurn() {
        if (turn == 'w') turn = 'b';
        else turn = 'w';
    }

    private Piece[][] createChessBoard() {
        Piece[][] chessBoard = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row == 0 || row == 7) {
                    char color = (row == 0) ? 'b' : 'w';
                    switch (col) {
                        case 0, 7 -> chessBoard[row][col] = new Rook(color, row, col);
                        case 1, 6 -> chessBoard[row][col] = new Knight(color, row, col);
                        case 2, 5 -> chessBoard[row][col] = new Bishop(color, row, col);
                        case 3 -> chessBoard[row][col] = new Queen(color, row, col);
                        case 4 -> chessBoard[row][col] = new King(color, row, col);
                    }
                } else if (row == 1 || row == 6) {
                    chessBoard[row][col] = new Pawn(((row == 1) ? 'b' : 'w'), row, col);
                } else {
                    chessBoard[row][col] = null;
                }
            }
        }
        return chessBoard;
    }

    public void printChessBoard() {
        for (int row = 0; row < 8; row++) {
            StringBuilder rowText = new StringBuilder("[");
            for (int col = 0; col < 8; col++) {
                if (chessBoard[row][col] != null) {
                    rowText.append(chessBoard[row][col].getLetter()).append(", ");
                } else {
                    rowText.append("-, ");
                }
            }
            System.out.println(rowText.substring(0, rowText.length() - 2) + "]");
        }
    }

    public Piece[][] getChessBoard() {
        return chessBoard;
    }
}
