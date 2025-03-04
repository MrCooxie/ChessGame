package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

/**
 * Represents a Knight chess piece that extends the base Piece class.
 * Knights move in an L-shape pattern: 2 squares in one direction and 1 square perpendicular.
 */
public class Knight extends Piece {
    /**
     * Constructor for creating a new Knight.
     *
     * @param color The color of the Knight ('w' for white, 'b' for black)
     * @param row   The initial row position on the chess board
     * @param col   The initial column position on the chess board
     */
    public Knight(char color, int row, int col) {
        // Call the parent constructor to set color and position
        super(color, row, col);
        // Set the piece letter to 'N' (standard chess notation for Knight)
        letter = 'N';
    }

    /**
     * Calculates all possible moves for this Knight based on current board state.
     * Knights have a unique L-shaped movement pattern and can jump over other pieces.
     *
     * @param chessBoardData Contains the current state of the chess board
     * @return ArrayList of valid positions the Knight can move to
     */
    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        // Only calculate moves if it's this piece's turn to move
        if (chessBoardData.getTurn() == color) {

            // Check all eight possible L-shaped moves to empty squares
            // Pattern: 2 squares in one direction, 1 square in perpendicular direction
            checkKnightMoveToEmptySquare(chessBoardData, row + 2, col - 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row + 2, col + 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 2, col - 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 2, col + 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row + 1, col + 2, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 1, col + 2, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row + 1, col - 2, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 1, col - 2, color, this, possibleMoves);

            // Check all eight possible L-shaped moves to capture opponent pieces
            checkKnightTakePiece(chessBoardData, row + 2, col - 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row + 2, col + 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 2, col - 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 2, col + 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row + 1, col + 2, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 1, col + 2, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row + 1, col - 2, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 1, col - 2, color, this, possibleMoves);

        }
        return possibleMoves;
    }

    /**
     * Helper method to check if a Knight can move to an empty square.
     * Adds the position to possible moves if:
     * 1. The square is within the board boundaries
     * 2. The square is empty
     * 3. The move doesn't put the king in check
     *
     * @param chessBoardData Current state of the chess board
     * @param row            Target row position
     * @param col            Target column position
     * @param color          Color of the Knight ('w' or 'b')
     * @param piece          Reference to this Knight piece
     * @param possibleMoves  List to add valid positions to
     */
    private void checkKnightMoveToEmptySquare(ChessBoardData chessBoardData, int row, int col, char color, Piece piece, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if ((CheckSquares.squareInBoardAndEmpty(chessBoard, row, col)) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, piece)) {
            possibleMoves.add(new Position(row, col));
        }
    }

    /**
     * Helper method to check if a Knight can capture an opponent's piece.
     * Adds the position to possible moves if:
     * 1. The square is within the board boundaries
     * 2. The square contains an opponent's piece
     * 3. The move doesn't put the king in check
     *
     * @param chessBoardData Current state of the chess board
     * @param row            Target row position
     * @param col            Target column position
     * @param color          Color of the Knight ('w' or 'b')
     * @param piece          Reference to this Knight piece
     * @param possibleMoves  List to add valid positions to
     */
    private void checkKnightTakePiece(ChessBoardData chessBoardData, int row, int col, char color, Piece piece, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col, color) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, piece)) {
            // Create a position with the TAKING move type to indicate capturing a piece
            possibleMoves.add(new Position(row, col, Move.TAKING));
        }
    }
}