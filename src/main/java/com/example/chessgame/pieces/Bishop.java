package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

/**
 * Represents a Bishop chess piece in the chess game.
 * Bishops move diagonally any number of squares but cannot jump over other pieces.
 */
public class Bishop extends Piece {

    /**
     * Constructs a new Bishop piece with the specified color and starting position.
     *
     * @param color The color of the bishop ('w' for white, 'b' for black)
     * @param row   The starting row position (0-7)
     * @param col   The starting column position (0-7)
     */
    public Bishop(char color, int row, int col) {
        super(color, row, col);
        letter = 'B'; // Sets the character representation of the Bishop to 'B'
    }

    /**
     * Calculates all possible valid moves for this Bishop based on the current board state.
     * Bishops can move diagonally in all four directions.
     *
     * @param chessBoardData The current state of the chess board
     * @return An ArrayList of all valid positions the Bishop can move to
     */
    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();

        // Only calculate moves if it's this piece's color's turn
        if (chessBoardData.getTurn() == color) {
            // Check all four diagonal directions a bishop can move
            possibleMoves.addAll(oneDirectionAllPossibleMoves(-1, -1, chessBoardData)); // Up-left diagonal
            possibleMoves.addAll(oneDirectionAllPossibleMoves(-1, +1, chessBoardData)); // Up-right diagonal
            possibleMoves.addAll(oneDirectionAllPossibleMoves(+1, -1, chessBoardData)); // Down-left diagonal
            possibleMoves.addAll(oneDirectionAllPossibleMoves(+1, +1, chessBoardData)); // Down-right diagonal
        }
        return possibleMoves;
    }

    /**
     * Helper method that calculates all possible moves in one diagonal direction.
     * Continues in the specified direction until it reaches the board edge,
     * an opponent's piece (which it can capture), or a friendly piece (which blocks movement).
     *
     * @param rowIncrement   The row direction (-1 for up, +1 for down)
     * @param colIncrement   The column direction (-1 for left, +1 for right)
     * @param chessBoardData The current state of the chess board
     * @return An ArrayList of valid positions in the specified diagonal direction
     */
    private ArrayList<Position> oneDirectionAllPossibleMoves(int rowIncrement, int colIncrement, ChessBoardData chessBoardData) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        ArrayList<Position> possibleMoves = new ArrayList<>();
        boolean blocked = false; // Flag to track if the path is blocked

        // Check up to 7 squares in the diagonal direction (maximum possible in chess)
        for (int i = 1; i < 8; i++) {
            if (!blocked) {
                // Calculate the target position
                int targetRow = row + i * rowIncrement;
                int targetCol = col + i * colIncrement;

                // Check if the target square is on the board
                if (CheckSquares.isWithInBoard(targetRow, targetCol)) {
                    // Case 1: Empty square - can move there if it doesn't put the king in check
                    if (CheckSquares.squareEmpty(chessBoard, targetRow, targetCol)) {
                        if (CheckSquares.moveNotCheck(chessBoardData, targetRow, targetCol, color, this)) {
                            possibleMoves.add(new Position(targetRow, targetCol));
                        }
                    }
                    // Case 2: Opponent's piece - can capture it if it doesn't put the king in check
                    else if (CheckSquares.squareOppositeColor(chessBoard, targetRow, targetCol, color)) {
                        if (CheckSquares.moveNotCheck(chessBoardData, targetRow, targetCol, color, this)) {
                            possibleMoves.add(new Position(targetRow, targetCol, Move.TAKING));
                        }
                        blocked = true; // Can't move beyond a capture
                    }
                    // Case 3: Same color piece - path is blocked
                    else {
                        blocked = true;
                    }
                }
                // Case 4: Outside the board - path is blocked
                else {
                    blocked = true;
                }
            }
        }
        return possibleMoves;
    }
}