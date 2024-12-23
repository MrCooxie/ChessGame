package com.example.chessgame.data;

public class Position {
    private final int row;
    private final int col;

    private final boolean castling;

    private final boolean enPassant;

    public Position(int row, int col) {
        this(row, col, false, false);
    }
    public Position(int row, int col, boolean castling, boolean enPassant){
        this.row = row;
        this.col = col;
        this.castling = castling;
        this.enPassant = enPassant;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public boolean isCastling() {
        return castling;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                ", castling=" + castling +
                '}';
    }
}
