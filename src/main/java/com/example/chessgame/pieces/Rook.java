package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;

import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(char color, int row, int col) {
        super(color, row, col);
        letter = 'R';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        return null;
    }
}
