package com.example.chessgame.data;

public class Position {
    private final int row;
    private final int col;

    private final Move specialMove;

    public Position(int row, int col) {
        this(row, col, null);
    }

    public Position(int row, int col, Move move) {
        this.row = row;
        this.col = col;
        specialMove = move;

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Move getSpecialMove() {
        return specialMove;
    }

}
