package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

/**
 * Represents a Rook piece in chess.
 * The Rook can move any number of squares horizontally or vertically,
 * as long as its path is not blocked by other pieces.
 */
public class Rook extends Piece {

    /**
     * Constructor for creating a Rook piece.
     *
     * @param color The color of the piece ('w' for white, 'b' for black)
     * @param row   Initial row position (0-7)
     * @param col   Initial column position (0-7)
     */
    public Rook(char color, int row, int col) {
        super(color, row, col);
        letter = 'R'; // Set the letter representation for the Rook
    }

    /**
     * Calculates all possible moves for the Rook from its current position.
     * The Rook can move horizontally or vertically in any direction.
     *
     * @param chessBoardData Current state of the chess board
     * @return ArrayList of all valid positions this Rook can move to
     */
    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();

        // Only calculate moves if it's this piece's turn to move
        if (chessBoardData.getTurn() == color) {
            // Add all possible moves in four directions:
            possibleMoves.addAll(oneDirectionAllPossibleMoves(+1, 0, chessBoardData)); // Down
            possibleMoves.addAll(oneDirectionAllPossibleMoves(-1, 0, chessBoardData)); // Up
            possibleMoves.addAll(oneDirectionAllPossibleMoves(0, +1, chessBoardData)); // Right
            possibleMoves.addAll(oneDirectionAllPossibleMoves(0, -1, chessBoardData)); // Left
        }

        return possibleMoves;
    }

    /**
     * Helper method to find all possible moves in a single direction.
     * Continues checking squares in the specified direction until it hits
     * a board edge, a friendly piece, or captures an enemy piece.
     *
     * @param rowIncrement   The row direction to move (-1, 0, or 1)
     * @param colIncrement   The column direction to move (-1, 0, or 1)
     * @param chessBoardData Current state of the chess board
     * @return ArrayList of valid positions in the specified direction
     */
    private ArrayList<Position> oneDirectionAllPossibleMoves(int rowIncrement, int colIncrement, ChessBoardData chessBoardData) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        ArrayList<Position> possibleMoves = new ArrayList<>();
        boolean blocked = false; // Flag to stop checking once the path is blocked

        // Check up to 7 squares in the specified direction
        for (int i = 1; i < 8; i++) {
            if (!blocked) {
                int newRow = row + i * rowIncrement;
                int newCol = col + i * colIncrement;

                // Check if the target square is on the board
                if (CheckSquares.isWithInBoard(newRow, newCol)) {

                    // Check if the square is empty
                    if (CheckSquares.squareEmpty(chessBoard, newRow, newCol)) {
                        // Check if moving to this square would not put our king in check
                        if (CheckSquares.moveNotCheck(chessBoardData, newRow, newCol, color, this)) {
                            possibleMoves.add(new Position(newRow, newCol));
                        }
                    }
                    // Check if the square has an opponent's piece (can capture)
                    else if (CheckSquares.squareOppositeColor(chessBoard, newRow, newCol, color)) {
                        // Check if capturing this piece would not put our king in check
                        if (CheckSquares.moveNotCheck(chessBoardData, newRow, newCol, color, this)) {
                            possibleMoves.add(new Position(newRow, newCol, Move.TAKING));
                        }
                        blocked = true; // Can't move further after capturing
                    }
                    // Square has our own piece (can't move through or capture it)
                    else {
                        blocked = true;
                    }
                }
                // Off the board
                else {
                    blocked = true;
                }
            }
        } //TODO: Something off here

        return possibleMoves;
    }
}