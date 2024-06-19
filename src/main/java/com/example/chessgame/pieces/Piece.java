package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;

import java.util.ArrayList;

public abstract class Piece {
    protected final char color;
    protected int row;
    protected int col;
    protected char letter;

    public Piece(char color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public char getColor() {
        return color;
    }

    public char getLetter() {
        return letter;
    }

    public abstract ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData);

    @Override
    public String toString() {
        return "Piece{" + "color=" + color + ", row=" + row + ", col=" + col + ", letter=" + letter + '}';
    }
}
