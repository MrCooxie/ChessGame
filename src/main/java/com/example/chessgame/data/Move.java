package com.example.chessgame.data;

/**
 * Represents special moves and actions in chess.
 * This enum defines unique movement types beyond standard piece movement.
 */
public enum Move {
    /**
     * Represents the castling move, where the king and rook
     * move simultaneously under special conditions.
     * Allows the king to move two squares towards a rook,
     * and the rook moves to the square the king crossed.
     */
    CASTLING,

    /**
     * Represents the en passant special pawn capture.
     * Occurs when a pawn moves two squares forward, and an opponent's pawn
     * can capture it as if it had only moved one square.
     */
    EN_PASSANT,

    /**
     * Represents a standard piece capture,
     * where one piece removes an opponent's piece from the board.
     */
    TAKING,

    /**
     * Represents a pawn promotion where a pawn reaching
     * the opposite end of the board can be transformed
     * into a queen, rook, bishop, or knight.
     */
    PROMOTE,

    /**
     * Represents a pawn promotion that occurs during a capture,
     * combining the promotion and taking actions.
     */
    PROMOTE_TAKING
}