package com.example.chessgame.controllers;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.pieces.Piece;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Stack;

public class ChessBoardController {


    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        //TODO: Don't need to use Piece object can also just use row and col
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        GridPane gridPane = (GridPane) mouseEvent.getSource();
        handleLogic((Position) gridPane.getUserData(),clickedRow,clickedCol,chessBoardData);
        handleHints(clickedRow,clickedCol, gridPane,chessBoardData);

    }
    private void handleHints(int clickedRow, int clickedCol, GridPane gridPane, ChessBoardData chessBoardData){
        Position previousClick = (Position) gridPane.getUserData();
        Piece targetPiece = chessBoardData.getChessBoard()[clickedRow][clickedCol];
        if(previousClick == null || chessBoardData.getChessBoard()[previousClick.getRow()][previousClick.getCol()] == null){
            //No previous click then just add hints
            if(targetPiece != null){
             addHints(targetPiece.getPossibleMoves(chessBoardData), gridPane);
            }
            gridPane.setUserData(new Position(clickedRow,clickedCol));
        } else {
            removeHints(chessBoardData.getChessBoard()[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData), gridPane);
            if(targetPiece != null) {
                addHints(targetPiece.getPossibleMoves(chessBoardData), gridPane);
                gridPane.setUserData(new Position(clickedRow,clickedCol));
            } else {
                gridPane.setUserData(null);
            }

        }

    }
    private void addHints(ArrayList<Position> placesToAddHints, GridPane gridPane){
        for(Position place : placesToAddHints){
            StackPane square = (StackPane) gridPane.getChildren().get(place.getRow() * 8 + place.getCol());
            Circle circle = new Circle(11.5, Color.rgb(0, 0, 0, 0.14));
            square.getChildren().add(circle);
        }
    }
    private void removeHints(ArrayList<Position> placesToRemoveHints, GridPane gridPane){
        for (Position move : placesToRemoveHints) {
            StackPane square = (StackPane) gridPane.getChildren().get(move.getRow() * 8 + move.getCol());
            square.getChildren().removeIf(item -> item instanceof Circle);
        }
    }

    private void handleLogic(Position previousPosition, int clickedRow, int clickedCol, ChessBoardData chessBoardData){
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if(previousPosition != null && chessBoard[previousPosition.getRow()][previousPosition.getCol()] != null){
            ArrayList<Position> legalMoves = chessBoard[previousPosition.getRow()][previousPosition.getCol()].getPossibleMoves(chessBoardData);
            for(Position move : legalMoves){
                if(move.getRow() == clickedRow && move.getCol() == clickedCol){
                    //Legal move
                    Piece movingPiece = chessBoard[previousPosition.getRow()][previousPosition.getCol()];
                    movingPiece.setRow(clickedRow);
                    movingPiece.setCol(clickedCol);
                    chessBoard[previousPosition.getRow()][previousPosition.getCol()] = null;
                    chessBoard[clickedRow][clickedCol] = movingPiece;
                }
            }
        }
    }
}
