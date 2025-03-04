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

/**
 * Controller class responsible for handling pawn promotion in the chess game.
 * When a pawn reaches the opposite end of the board, this controller manages
 * the UI and logic for promoting the pawn to another piece type.
 */
public class PromoteScreenController {

    /**
     * Event handler for when the player clicks on a piece to promote their pawn to.
     * This method is called when a player selects which piece to promote their pawn to.
     *
     * @param mouseEvent The mouse click event containing information about which piece was clicked
     */
    @FXML
    private void onPieceClick(MouseEvent mouseEvent) {
        // Get the ImageView (chess piece image) that was clicked
        ImageView image = (ImageView) mouseEvent.getSource();

        // Get the parent VBox containing the promotion options
        VBox vBox = (VBox) image.getParent();

        // Update the visual representation of the pawn to the selected piece
        changeGraphics(image.getId(), vBox);

        // Retrieve the pawn promotion data stored in the VBox's userData
        PawnPromoteDTO pawnPromoteDTO = (PawnPromoteDTO) vBox.getUserData();

        // Update the logical chess board with the new piece
        changeChessBoard(pawnPromoteDTO.row(), pawnPromoteDTO.col(), pawnPromoteDTO.chessBoardData(), image.getId());
    }

    /**
     * Updates the visual representation of the chess board after a pawn promotion.
     * Replaces the pawn image with the chosen piece and removes the promotion screen.
     *
     * @param id   The ID of the selected piece (e.g., "white-queen", "black-knight")
     * @param vBox The VBox containing the promotion options that will be removed
     */
    private void changeGraphics(String id, VBox vBox) {
        // Retrieve promotion data from the VBox
        PawnPromoteDTO pawnPromoteDTO = (PawnPromoteDTO) vBox.getUserData();
        GridPane gridPane = pawnPromoteDTO.gridPane();

        // Find the specific stack pane in the chess board that contains the pawn to be promoted
        StackPane stackPane = (StackPane) gridPane.getChildren().get(pawnPromoteDTO.row() * 8 + pawnPromoteDTO.col());

        // Iterate through the nodes in the stack pane to find the image view
        for (Node node : stackPane.getChildren()) {
            if (node instanceof ImageView imageView) {
                // Replace the pawn image with the image of the selected promotion piece
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/chessgame/images/chess-pieces/" + id + ".png"))));
                break;
            }
        }

        // Remove the promotion selection screen from the UI
        StackPane stackPaneParent = (StackPane) vBox.getParent();
        stackPaneParent.getChildren().remove(vBox);
    }

    /**
     * Updates the logical representation of the chess board after a pawn promotion.
     * Replaces the pawn object with the chosen piece in the data model.
     *
     * @param row            The row position of the pawn on the chess board
     * @param col            The column position of the pawn on the chess board
     * @param chessBoardData The data model representing the chess board
     * @param id             The ID of the selected piece (e.g., "white-queen", "black-knight")
     */
    private void changeChessBoard(int row, int col, ChessBoardData chessBoardData, String id) {
        // Get the 2D array representing the chess board
        Piece[][] chessBoard = chessBoardData.getChessBoard();

        // Get the pawn object at the specified position
        Pawn pawn = (Pawn) chessBoard[row][col];

        // Promote the pawn to the selected piece type
        pawn.promotePiece(chessBoard, id);

        // Print the updated chess board (likely for debugging or console representation)
        chessBoardData.printChessBoard();
    }
}