package com.example.chessgame.pieces;

public class Piece {
    private final char color;
    private int row;
    private int col;
    protected char letter;

    public Piece(char color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public char getLetter() {
        return letter;
    }
}
