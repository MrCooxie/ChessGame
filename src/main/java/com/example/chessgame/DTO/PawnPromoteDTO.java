package com.example.chessgame.DTO;

import com.example.chessgame.data.ChessBoardData;
import javafx.scene.layout.GridPane;

public record PawnPromoteDTO(GridPane gridPane, int row, int col, ChessBoardData chessBoardData) {

}
