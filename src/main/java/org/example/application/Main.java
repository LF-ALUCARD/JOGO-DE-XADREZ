package org.example.application;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

import org.example.boardgame.Position;
import org.example.chess.ChessMatch;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ChessMatch chessMatch = new ChessMatch();

        UI.printBoard(chessMatch.getPieces());
    }
}