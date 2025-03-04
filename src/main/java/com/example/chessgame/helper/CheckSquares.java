package com.example.chessgame.helper;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.pieces.King;
import com.example.chessgame.pieces.Knight;
import com.example.chessgame.pieces.Piece;

/**
 * Utility class that provides static methods for validating and checking chess board squares.
 * Contains various helper methods to determine if moves are valid and check game state conditions.
 */
public class CheckSquares {
    /**
     * Private constructor to prevent instantiation of this utility class.
     * All methods are static and should be accessed directly through the class.
     */
    private CheckSquares() {

    }

    /**
     * Checks if a square on the chessboard is empty.
     *
     * @param chessBoard The 2D array representing the chess board
     * @param row        The row coordinate to check
     * @param col        The column coordinate to check
     * @return true if the specified square is empty (null), false otherwise
     */
    public static boolean squareEmpty(Piece[][] chessBoard, int row, int col) {
        return chessBoard[row][col] == null;
    }

    /**
     * Validates if the given coordinates are within the boundaries of the chess board.
     * Standard chess board has 8x8 dimensions (indices 0-7).
     *
     * @param row The row coordinate to check
     * @param col The column coordinate to check
     * @return true if the coordinates are within the board's boundaries, false otherwise
     */
    public static boolean isWithInBoard(int row, int col) {
        return (row >= 0 && row <= 7) && (col >= 0 && col <= 7);
    }

    /**
     * Checks if a square is both within the board boundaries and empty.
     * A convenient combination of isWithInBoard and squareEmpty methods.
     *
     * @param chessboard The 2D array representing the chess board
     * @param row        The row coordinate to check
     * @param col        The column coordinate to check
     * @return true if the square is within board boundaries and empty, false otherwise
     */
    public static boolean squareInBoardAndEmpty(Piece[][] chessboard, int row, int col) {
        return isWithInBoard(row, col) && squareEmpty(chessboard, row, col);
    }

    /**
     * Checks if a piece at the specified square is of the opposite color.
     *
     * @param chessBoard The 2D array representing the chess board
     * @param row        The row coordinate to check
     * @param col        The column coordinate to check
     * @param color      The color to compare against (typically the current player's color)
     * @return true if the piece at the specified square is of opposite color, false if same color
     */
    public static boolean squareOppositeColor(Piece[][] chessBoard, int row, int col, char color) {
        return !(chessBoard[row][col].getColor() == color);
    }

    /**
     * Checks if a square is within the board, not empty, and contains a piece of the opposite color.
     * Useful for determining if a piece can capture another piece.
     *
     * @param chessBoard The 2D array representing the chess board
     * @param row        The row coordinate to check
     * @param col        The column coordinate to check
     * @param color      The color to compare against (typically the current player's color)
     * @return true if all conditions are met (valid square with opponent's piece), false otherwise
     */
    public static boolean squareInBoardNotEmptyOppositeColor(Piece[][] chessBoard, int row, int col, char color) {
        return (isWithInBoard(row, col) && chessBoard[row][col] != null && squareOppositeColor(chessBoard, row, col, color));
    }

    /**
     * Checks if the square contains an opponent's Knight piece.
     * Used for determining if a king is under check from a knight.
     *
     * @param chessBoard The 2D array representing the chess board
     * @param row        The row coordinate to check
     * @param col        The column coordinate to check
     * @param color      The color to compare against (typically the current player's color)
     * @return true if the square contains an opponent's Knight, false otherwise
     */
    public static boolean isOppositeColorKnight(Piece[][] chessBoard, int row, int col, char color) {
        if (isWithInBoard(row, col) && !squareEmpty(chessBoard, row, col) && squareOppositeColor(chessBoard, row, col, color)) {
            return chessBoard[row][col] instanceof Knight;
        }
        return false;
    }

    /**
     * Checks if moving a piece to a destination square would result in the king being in check.
     * Temporarily moves the piece, checks if the king is under check, then restores the board state.
     *
     * @param chessBoardData Object containing the chess board and game state
     * @param row            Destination row coordinate
     * @param col            Destination column coordinate
     * @param color          Color of the player making the move
     * @param piece          The piece being moved
     * @return true if the move is safe (king not in check), false otherwise
     */
    public static boolean moveNotCheck(ChessBoardData chessBoardData, int row, int col, char color, Piece piece) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        int pieceRow = piece.getRow();
        int pieceCol = piece.getCol();
        Piece takenPiece = chessBoard[row][col];

        // Temporarily make the move on the board
        chessBoard[piece.getRow()][piece.getCol()] = null;
        chessBoard[row][col] = piece;

        // Search for the king of the same color as the moving piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j] != null && chessBoard[i][j] instanceof King king && chessBoard[i][j].getColor() == color) {
                    // King of the same color found

                    // If the piece being moved is the king itself, update its position attributes
                    if (piece instanceof King) {
                        king.setRow(i);
                        king.setCol(j);
                    }

                    // Check if the king is under check in this temporary board state
                    if (king.isUnderCheck(chessBoard)) {
                        // King is under check, restore the original board state
                        chessBoard[row][col] = takenPiece;
                        chessBoard[pieceRow][pieceCol] = piece;
                        if (piece instanceof King) {
                            // Reset king's position attributes if it was the king being moved
                            king.setRow(pieceRow);
                            king.setCol(pieceCol);
                        }
                        return false; // Move is not valid as it puts/leaves king in check
                    } else {
                        // King is not under check, restore the original board state
                        chessBoard[row][col] = takenPiece;
                        chessBoard[pieceRow][pieceCol] = piece;
                        if (piece instanceof King) {
                            // Reset king's position attributes if it was the king being moved
                            king.setRow(pieceRow);
                            king.setCol(pieceCol);
                        }
                        return true; // Move is valid as the king is not in check
                    }
                }
            }
        }
        // If no king found (shouldn't happen in a valid chess game), consider the move valid
        return true;
    }
}