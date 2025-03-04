package com.example.chessgame.data;

/**
 * Represents the possible outcomes of a chess game.
 * This enum defines the different states a chess game can end in.
 */
public enum GameResult {
    /**
     * Indicates that the white player has won the game,
     * typically by checkmating the black king
     */
    WHITE_WIN,

    /**
     * Indicates that the black player has won the game,
     * typically by checkmating the white king
     */
    BLACK_WIN,

    /**
     * Indicates that the game is still in progress
     * and has not yet reached a final state
     */
    PLAY_ON,

    /**
     * Indicates a draw where the game ends with neither player winning.
     * Occurs when a player has no legal moves but is not in check
     * (the king is not threatened, but no piece can move without putting the king in check)
     */
    STALEMATE
}