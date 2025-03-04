package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

/**
 * This class represents a Pawn chess piece.
 * Pawns have special movement rules including:
 * - Moving one square forward
 * - Moving two squares on first move
 * - Capturing diagonally
 * - En Passant captures
 * - Promotion when reaching the opposite side
 */
public class Pawn extends Piece {

    // Tracks if a pawn just moved two squares (for en passant rule)
    private boolean movedFar = false;

    /**
     * Constructor for creating a new Pawn
     *
     * @param color The color of the pawn ('w' for white, 'b' for black)
     * @param row   The starting row position
     * @param col   The starting column position
     */
    public Pawn(char color, int row, int col) {
        super(color, row, col);  // Call the parent constructor
        letter = 'P';  // Set the piece identifier to 'P' for Pawn
    }

    /**
     * Check if the pawn has just moved two squares
     *
     * @return true if the pawn moved two squares in its last move
     */
    public boolean isMovedFar() {
        return movedFar;
    }

    /**
     * Set whether the pawn has just moved two squares
     *
     * @param movedFar true if the pawn moved two squares
     */
    public void setMovedFar(boolean movedFar) {
        this.movedFar = movedFar;
    }

    /**
     * Calculates all possible valid moves for this pawn
     *
     * @param chessBoardData Current state of the chess board
     * @return ArrayList of all possible positions this pawn can move to
     */
    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();

        // Only calculate moves if it's this pawn's turn to move
        if (chessBoardData.getTurn() == color) {
            // Determine direction of movement (black moves down, white moves up)
            int moveDirection = (color == 'b') ? 1 : -1;
            Piece[][] chessBoard = chessBoardData.getChessBoard();

            // Check forward movement
            if (CheckSquares.squareInBoardAndEmpty(chessBoard, row + moveDirection, col)) {
                // Ensure the move doesn't put own king in check
                if (CheckSquares.moveNotCheck(chessBoardData, row + moveDirection, col, color, this)) {
                    // Check if the pawn will reach the opposite end (promotion)
                    if (pawnToOtherSide(row + moveDirection)) {
                        possibleMoves.add(new Position(row + moveDirection, col, Move.PROMOTE));
                    } else {
                        possibleMoves.add(new Position(row + moveDirection, col));
                    }


                    // Check if pawn can move two squares forward (first move only)
                    if (!(hasMoved) && CheckSquares.squareInBoardAndEmpty(chessBoard, row + 2 * moveDirection, col) && CheckSquares.moveNotCheck(chessBoardData, row + 2 * moveDirection, col, color, this)) {
                        possibleMoves.add(new Position(row + 2 * moveDirection, col));
                    }
                }
            }

            // Check diagonal capture to the right
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + moveDirection, col + 1, color) && CheckSquares.moveNotCheck(chessBoardData, row + moveDirection, col + 1, color, this)) {
                if (pawnToOtherSide(row + moveDirection)) {
                    // Promotion after capture
                    possibleMoves.add(new Position(row + moveDirection, col + 1, Move.PROMOTE_TAKING));
                } else {
                    // Regular capture
                    possibleMoves.add(new Position(row + moveDirection, col + 1, Move.TAKING));
                }
            }

            // Check diagonal capture to the left
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + moveDirection, col - 1, color) && CheckSquares.moveNotCheck(chessBoardData, row + moveDirection, col - 1, color, this)) {
                if (pawnToOtherSide(row + moveDirection)) {
                    // Promotion after capture
                    possibleMoves.add(new Position(row + moveDirection, col - 1, Move.PROMOTE_TAKING));
                } else {
                    // Regular capture
                    possibleMoves.add(new Position(row + moveDirection, col - 1, Move.TAKING));
                }
            }

            // Check for En Passant capture to the left
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col - 1, color) && chessBoard[row][col - 1] instanceof Pawn && ((Pawn) chessBoard[row][col - 1]).isMovedFar()) {
                possibleMoves.add(new Position(row + moveDirection, col - 1, Move.EN_PASSANT));
            }

            // Check for En Passant capture to the right
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col + 1, color) && chessBoard[row][col + 1] instanceof Pawn && ((Pawn) chessBoard[row][col + 1]).isMovedFar()) {
                possibleMoves.add(new Position(row + moveDirection, col + 1, Move.EN_PASSANT));
            }
        }
        return possibleMoves;
    }

    /**
     * Determines if the pawn has reached the opposite side of the board
     *
     * @param row The row to check
     * @return true if the pawn has reached the opposite end of the board
     */
    private boolean pawnToOtherSide(int row) {
        return (row == 0 && color == 'w') || (row == 7 && color == 'b');
    }

    /**
     * Executes the en passant capture by removing the captured pawn
     *
     * @param chessBoard The current chess board
     */
    public void enPassant(Piece[][] chessBoard) {
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col + 1, color) && chessBoard[row][col + 1] instanceof Pawn && ((Pawn) chessBoard[row][col + 1]).isMovedFar()) {
            // Remove the pawn to the right
            chessBoard[row][col + 1] = null;
        } else {
            // Remove the pawn to the left
            chessBoard[row][col - 1] = null;
        }
    }

    /**
     * Promotes the pawn to another piece (queen, rook, bishop, or knight)
     *
     * @param chessBoard The current chess board
     * @param piece      String indicating which piece to promote to (e.g., "wq" for white queen)
     */
    public void promotePiece(Piece[][] chessBoard, String piece) {
        char promotedPieceColor = piece.charAt(0);  // First char is the color
        char pieceToPromoteTO = piece.charAt(1);    // Second char is the piece type

        // Replace this pawn with the chosen piece
        switch (pieceToPromoteTO) {
            case 'q' -> chessBoard[this.row][this.col] = new Queen(promotedPieceColor, this.row, this.col);
            case 'r' -> chessBoard[this.row][this.col] = new Rook(promotedPieceColor, this.row, this.col);
            case 'b' -> chessBoard[this.row][this.col] = new Bishop(promotedPieceColor, this.row, this.col);
            case 'n' -> chessBoard[this.row][this.col] = new Knight(promotedPieceColor, this.row, this.col);
        }
    }
}