package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;

import java.util.ArrayList;

/**
 * Represents a Queen piece in chess.
 * The Queen combines the movement capabilities of both the Bishop and Rook,
 * allowing it to move any number of squares diagonally, horizontally, or vertically.
 */
public class Queen extends Piece {

    /**
     * Constructor for creating a Queen piece.
     *
     * @param color The color of the piece ('w' for white, 'b' for black)
     * @param row   Initial row position (0-7)
     * @param col   Initial column position (0-7)
     */
    public Queen(char color, int row, int col) {
        super(color, row, col);
        letter = 'Q'; // Set the letter representation for the Queen
    }

    /**
     * Calculates all possible moves for the Queen from its current position.
     * The Queen combines the movement patterns of both Bishop (diagonal) and Rook (straight).
     * This method uses a clever approach by temporarily replacing the Queen with Bishop and Rook
     * on the board to calculate their respective moves, then combines the results.
     *
     * @param chessBoardData Current state of the chess board
     * @return ArrayList of all valid positions this Queen can move to
     */
    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();

        // Temporarily replace the Queen with a Bishop at the same position
        // to calculate all possible diagonal moves
        chessBoard[row][col] = new Bishop(color, row, col);
        ArrayList<Position> diagonalMoves = chessBoard[row][col].getPossibleMoves(chessBoardData);

        // Temporarily replace the Queen with a Rook at the same position
        // to calculate all possible straight moves (horizontal and vertical)
        chessBoard[row][col] = new Rook(color, row, col);
        ArrayList<Position> straightMoves = chessBoard[row][col].getPossibleMoves(chessBoardData);

        // Restore the Queen to its original position on the board
        chessBoard[row][col] = this;

        // Combine the diagonal and straight moves to get all possible Queen moves
        diagonalMoves.addAll(straightMoves);
        return diagonalMoves;
    }
}