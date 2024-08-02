package com.example.chessgame.controllers;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.pieces.Piece;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Stack;

public class ChessBoardController {


    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        //TODO: Don't need to use Piece object can also just use row and col
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        GridPane gridPane = (GridPane) mouseEvent.getSource();
        /*handleLogic((Position) gridPane.getUserData(),clickedRow,clickedCol,chessBoardData);
        handleHints(clickedRow,clickedCol, gridPane,chessBoardData);*/
        handleMoveLogic(clickedRow,clickedCol, gridPane, chessBoardData);

    }
    private void handleMoveLogic(int clickedRow, int clickedCol, GridPane gridPane, ChessBoardData chessBoardData){
        //Remove previous hints.
        Position previousClick = (Position) gridPane.getUserData();
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if(previousClick == null || chessBoardData.getChessBoard()[previousClick.getRow()][previousClick.getCol()] == null){
            //No hints to remove so just add new hints
            if(chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane);
            }
            gridPane.setUserData(new Position(clickedRow,clickedCol));
        } else {
            removeHints(chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData),gridPane);
            if(chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane);
            }
            ArrayList<Position> legalMoves = chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData);
            for(Position move : legalMoves){
                if(move.getRow() == clickedRow && move.getCol() == clickedCol) {
                    //Was a legal move make changes in graphics and logic.
                    Piece movingPiece = chessBoard[previousClick.getRow()][previousClick.getCol()];
                    movingPiece.setRow(clickedRow);
                    movingPiece.setCol(clickedCol);
                    chessBoard[previousClick.getRow()][previousClick.getCol()] = null;
                    chessBoard[clickedRow][clickedCol] = movingPiece;
                    chessBoardData.printChessBoard();

                    StackPane square = (StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol());
                    for(Node children : square.getChildren()){
                        if(children instanceof ImageView){
                            StackPane targetSquare = (StackPane) gridPane.getChildren().get(clickedRow * 8 + clickedCol);
                                for(Node targetChildren : targetSquare.getChildren()){
                                    if(targetChildren instanceof ImageView){
                                        targetSquare.getChildren().remove(targetChildren);
                                        break;
                                    }
                                }
                                targetSquare.getChildren().add(children);
                                break;
                        }
                    }
                }
            }
            gridPane.setUserData(new Position(clickedRow,clickedCol));
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

}
