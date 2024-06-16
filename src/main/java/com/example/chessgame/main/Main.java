package com.example.chessgame.main;

import com.example.chessgame.graphics.ChessBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new ChessBoard(Color.rgb(119, 149, 86), Color.rgb(235, 236, 208), Color.rgb(115, 149, 82), Color.rgb(235, 236, 208), 92).createChessBoard());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}