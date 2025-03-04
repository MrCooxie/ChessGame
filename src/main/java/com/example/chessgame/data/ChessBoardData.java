package com.example.chessgame.data;

import com.example.chessgame.pieces.*;

/**
 * This class represents the data model for a chess game.
 * It manages the chess board state and whose turn it is.
 */
public class ChessBoardData {

    // A 2D array representing the chess board, initialized with the standard chess setup
    private final Piece[][] chessBoard = createChessBoard();

    // Tracks whose turn it is: 'w' for white, 'b' for black
    private char turn = 'w';

    /**
     * Gets the current player's turn.
     *
     * @return 'w' for white's turn, 'b' for black's turn
     */
    public char getTurn() {
        return turn;
    }

    /**
     * Switches the turn from the current player to the opponent.
     * Changes from white to black or black to white.
     */
    public void nextTurn() {
        if (turn == 'w') turn = 'b';
        else turn = 'w';
    }

    /**
     * Creates and initializes a chess board with all pieces in their starting positions.
     *
     * @return A 2D array representing the chess board with pieces
     */
    private Piece[][] createChessBoard() {
        Piece[][] chessBoard = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Position the major pieces on the back ranks (rows 0 and 7)
                if (row == 0 || row == 7) {
                    // Set color based on row: black for row 0, white for row 7
                    char color = (row == 0) ? 'b' : 'w';
                    // Place the correct piece type based on the column
                    switch (col) {
                        case 0, 7 -> chessBoard[row][col] = new Rook(color, row, col);   // Rooks in corners
                        case 1, 6 -> chessBoard[row][col] = new Knight(color, row, col); // Knights next to rooks
                        case 2, 5 -> chessBoard[row][col] = new Bishop(color, row, col); // Bishops next to knights
                        case 3 -> chessBoard[row][col] = new Queen(color, row, col);     // Queen on its color
                        case 4 -> chessBoard[row][col] = new King(color, row, col);      // King in the middle
                    }
                }
                // Position pawns on the second and seventh rows
                else if (row == 1 || row == 6) {
                    // Black pawns on row 1, white pawns on row 6
                    chessBoard[row][col] = new Pawn(((row == 1) ? 'b' : 'w'), row, col);
                }
                // Leave the middle rows empty
                else {
                    chessBoard[row][col] = null;
                }
            }
        }
        return chessBoard;
    }

    /**
     * Prints a text representation of the chess board to the console.
     * Used for debugging or console-based views.
     * Each piece is represented by its letter abbreviation or '-' for empty squares.
     */
    public void printChessBoard() {
        for (int row = 0; row < 8; row++) {
            StringBuilder rowText = new StringBuilder("[");
            for (int col = 0; col < 8; col++) {
                if (chessBoard[row][col] != null) {
                    // Add the piece's letter code to the string
                    rowText.append(chessBoard[row][col].getLetter()).append(", ");
                } else {
                    // Add a dash for empty squares
                    rowText.append("-, ");
                }
            }
            // Print the row without the trailing comma and space
            System.out.println(rowText.substring(0, rowText.length() - 2) + "]");
        }
    }

    /**
     * Gets the current state of the chess board.
     *
     * @return The 2D array representing the chess board
     */
    public Piece[][] getChessBoard() {
        return chessBoard;
    }
}