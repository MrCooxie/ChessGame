package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;

import java.util.ArrayList;

public abstract class Piece {
    protected final char color;
    protected int row;
    protected int col;
    protected char letter;

    protected boolean hasMoved = false;

    public Piece(char color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public char getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public int getCol() {
        return col;
    }

    public char getLetter() {
        return letter;
    }

    public void move(int row, int col, ChessBoardData chessBoardData, boolean isCastling){
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if(isCastling){
            //Figure if king side or queen side.
            if(this.col < col){
                //King Side
                Piece piece;
                if(color == 'b'){
                    piece = chessBoard[0][7];
                    chessBoard[0][7] = null;
                } else {
                    piece = chessBoard[7][7];
                    chessBoard[7][7] = null;
                }
                piece.setCol(5);
                chessBoard[piece.row][piece.col] = piece;

            } else {
                //Queen side
                Piece piece;
                if(color == 'b'){
                    piece = chessBoard[0][0];
                    chessBoard[0][0] = null;
                } else {
                    piece = chessBoard[7][0];
                    chessBoard[7][0] = null;
                }
                piece.setCol(3);
                chessBoard[piece.row][piece.col] = piece;
            }
        }
        chessBoard[this.row][this.col] = null;
        this.row = row;
        this.col = col;
        chessBoard[row][col] = this;

        chessBoardData.nextTurn();
        hasMoved = true;

    }

    public abstract ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData);

    @Override
    public String toString() {
        return "Piece{" + "color=" + color + ", row=" + row + ", col=" + col + ", letter=" + letter + '}';
    }
}
