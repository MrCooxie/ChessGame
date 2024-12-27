package com.example.chessgame.DTO;

import com.example.chessgame.data.ChessBoardData;
import javafx.scene.layout.GridPane;

public class PawnPromoteDTO {
    private final GridPane gridPane;
    private final int row;
    private final int col;

    private final ChessBoardData chessBoardData;

    public ChessBoardData getChessBoardData() {
        return chessBoardData;
    }

    public PawnPromoteDTO(GridPane gridPane, int row, int col, ChessBoardData chessBoardData) {
        this.gridPane = gridPane;
        this.row = row;
        this.col = col;
        this.chessBoardData = chessBoardData;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
