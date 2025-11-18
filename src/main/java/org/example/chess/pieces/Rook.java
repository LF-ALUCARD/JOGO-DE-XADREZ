package org.example.chess.pieces;

import org.example.boardgame.Board;
import org.example.chess.ChessMatch;
import org.example.chess.ChessPiece;
import org.example.chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
}
