package com.example.chessgame.pieces;

import com.example.chessgame.controllers.GameOverController;
import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.GameResult;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Abstract base class representing a chess piece.
 * All specific piece types (King, Queen, Pawn, etc.) extend this class.
 */
public abstract class Piece {
    protected final char color; // 'w' for white, 'b' for black
    protected int row;         // Current row position (0-7)
    protected int col;         // Current column position (0-7)
    protected char letter;     // Letter representation of the piece (e.g., 'K' for king)

    protected boolean hasMoved = false; // Tracks if the piece has moved (important for castling and pawn's first move)

    /**
     * Constructor for creating a chess piece.
     *
     * @param color The color of the piece ('w' for white, 'b' for black)
     * @param row   Initial row position (0-7)
     * @param col   Initial column position (0-7)
     */
    public Piece(char color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    // Getters and setters for piece properties
    public char getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public char getLetter() {
        return letter;
    }

    /**
     * Moves the piece to a new position and handles special moves.
     * Also checks if the game has ended after the move.
     *
     * @param row            The destination row
     * @param col            The destination column
     * @param chessBoardData Contains the board state and game information
     * @param specialMove    Enum representing special moves like castling or en passant
     * @param gridPane       The JavaFX GridPane representing the chess board UI
     */
    public void move(int row, int col, ChessBoardData chessBoardData, Move specialMove, GridPane gridPane) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();

        // Handle special moves like castling and en passant
        if (specialMove != null) {
            if (specialMove.equals(Move.CASTLING)) {
                ((King) this).castle(chessBoard, col);
            }
            if (specialMove.equals(Move.EN_PASSANT)) {
                assert this instanceof Pawn;
                ((Pawn) this).enPassant(chessBoard);
            }
        }

        // Reset the "movedFar" flag for all pawns (used for en passant detection)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j] instanceof Pawn) {
                    ((Pawn) chessBoard[i][j]).setMovedFar(false);
                }
            }
        }

        // If a pawn moves two squares, mark it as having moved far (for en passant)
        if (this instanceof Pawn && Math.abs(this.row - row) == 2) {
            ((Pawn) this).setMovedFar(true);
        }

        // Update the board representation: remove piece from old position
        chessBoard[this.row][this.col] = null;
        // Update piece's internal position
        this.row = row;
        this.col = col;
        // Place piece at new position
        chessBoard[row][col] = this;

        // Switch turns (white to black or black to white)
        chessBoardData.nextTurn();
        // Mark the piece as having moved (important for castling and pawn's first move)
        hasMoved = true;

        // Check if the game has ended (checkmate, stalemate, etc.)
        checkForGameEnd(chessBoardData, gridPane);
    }

    /**
     * Abstract method to be implemented by each piece type.
     * Returns all valid positions this piece can move to.
     *
     * @param chessBoardData Current state of the chess board
     * @return ArrayList of valid positions this piece can move to
     */
    public abstract ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData);

    /**
     * Checks if the game has ended after a move and displays the appropriate screen.
     *
     * @param chessBoardData Current state of the chess board
     * @param gridPane       The JavaFX GridPane to show the game over screen on
     */
    private void checkForGameEnd(ChessBoardData chessBoardData, GridPane gridPane) {
        switch (getGameResult(chessBoardData)) {
            case BLACK_WIN -> new GameOverController().createMatchOverScreen(GameResult.BLACK_WIN, gridPane);
            case WHITE_WIN -> new GameOverController().createMatchOverScreen(GameResult.WHITE_WIN, gridPane);
            case PLAY_ON -> {
                // Game continues, no action needed
            }
            case STALEMATE -> new GameOverController().createMatchOverScreen(GameResult.STALEMATE, gridPane);
        }
    }

    /**
     * Determines the result of the game based on the current board state.
     * Checks for checkmate, stalemate, or if the game should continue.
     *
     * @param chessBoardData Current state of the chess board
     * @return GameResult enum indicating the result (BLACK_WIN, WHITE_WIN, STALEMATE, or PLAY_ON)
     */
    private GameResult getGameResult(ChessBoardData chessBoardData) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();

        // Check if the current player can make any legal move
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece checkingPiece = chessBoard[row][col];
                if (checkingPiece != null && checkingPiece.getColor() == chessBoardData.getTurn()) {
                    if (!checkingPiece.getPossibleMoves(chessBoardData).isEmpty()) {
                        return GameResult.PLAY_ON; // At least one legal move exists, game continues
                    }
                }
            }
        }

        // No legal moves exist. Check if the king is in check to determine checkmate vs stalemate
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (chessBoard[row][col] != null && chessBoard[row][col] instanceof King && chessBoard[row][col].getColor() == chessBoardData.getTurn()) {

                    // If king is under check, it's checkmate (since we already know there are no legal moves)
                    if (((King) chessBoard[row][col]).isUnderCheck(chessBoard)) {
                        if (chessBoardData.getTurn() == 'b') {
                            return GameResult.WHITE_WIN; // Black king in check, White wins
                        } else {
                            return GameResult.BLACK_WIN; // White king in check, Black wins
                        }
                    }
                }
            }
        }

        // No legal moves and king is not in check, therefore it's a stalemate
        return GameResult.STALEMATE;
    }

    @Override
    public String toString() {
        return "Piece{" + "color=" + color + ", row=" + row + ", col=" + col + ", letter=" + letter + '}';
    }
}