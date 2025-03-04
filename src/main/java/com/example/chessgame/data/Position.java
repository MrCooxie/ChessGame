package com.example.chessgame.data;

/**
 * Represents a specific position on the chess board.
 * This class encapsulates the row and column coordinates of a square,
 * and optionally includes information about a special move.
 */
public class Position {
    /**
     * The row coordinate (0-7) on the chess board
     */
    private final int row;

    /**
     * The column coordinate (0-7) on the chess board
     */
    private final int col;

    /**
     * Represents a special move associated with this position
     * (e.g., castling, en passant, capturing)
     */
    private final Move specialMove;

    /**
     * Constructor for a standard position without a special move.
     *
     * @param row The row coordinate (0-7)
     * @param col The column coordinate (0-7)
     */
    public Position(int row, int col) {
        this(row, col, null);
    }

    /**
     * Constructor for a position that may include a special move.
     *
     * @param row  The row coordinate (0-7)
     * @param col  The column coordinate (0-7)
     * @param move Optional special move associated with this position
     */
    public Position(int row, int col, Move move) {
        this.row = row;
        this.col = col;
        specialMove = move;
    }

    /**
     * Gets the row coordinate of the position.
     *
     * @return The row coordinate (0-7)
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column coordinate of the position.
     *
     * @return The column coordinate (0-7)
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the special move associated with this position.
     *
     * @return The special move (e.g., castling, en passant) or null
     */
    public Move getSpecialMove() {
        return specialMove;
    }

    /**
     * Creates a string representation of the Position.
     * Useful for debugging and logging.
     *
     * @return A string containing row, column, and special move information
     */
    @Override
    public String toString() {
        return "Position{" + "row=" + row + ", col=" + col + ", specialMove=" + specialMove + '}';
    }
}