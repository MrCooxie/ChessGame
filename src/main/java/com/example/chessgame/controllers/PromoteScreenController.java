package com.example.chessgame.controllers;

import com.example.chessgame.DTO.PawnPromoteDTO;
import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.pieces.Pawn;
import com.example.chessgame.pieces.Piece;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class PromoteScreenController {


    @FXML
    private void onPieceClick(MouseEvent mouseEvent){
        ImageView image = (ImageView) mouseEvent.getSource();
        VBox vBox = (VBox) image.getParent();
        changeGraphics(image.getId(), vBox);
        PawnPromoteDTO pawnPromoteDTO = (PawnPromoteDTO) vBox.getUserData();

        changeChessBoard(pawnPromoteDTO.getRow(), pawnPromoteDTO.getCol(), pawnPromoteDTO.getChessBoardData(), image.getId());

    }
    private void changeGraphics(String id, VBox vBox){

        //Remove promote screen
        PawnPromoteDTO pawnPromoteDTO = (PawnPromoteDTO) vBox.getUserData();
        GridPane gridPane = pawnPromoteDTO.getGridPane();

        StackPane stackPane = (StackPane) gridPane.getChildren().get(pawnPromoteDTO.getRow() * 8 + pawnPromoteDTO.getCol());

        for(Node node : stackPane.getChildren()){
            if(node instanceof ImageView imageView){
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/chessgame/images/chess-pieces/" +id + ".png"))));
                break;
            }
        }
        StackPane stackPaneParent = (StackPane) vBox.getParent();
        stackPaneParent.getChildren().remove(vBox);
    }
    private void changeChessBoard(int row, int col, ChessBoardData chessBoardData, String id){
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        Pawn pawn = (Pawn) chessBoard[row][col];
        pawn.promotePiece(chessBoard, id);
        chessBoardData.printChessBoard();
    }
}
