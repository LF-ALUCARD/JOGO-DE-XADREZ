package org.example.boardgame;

public class Board {

    private Integer row;
    private Integer column;
    private Piece[][] pieces;

    public Board(Integer row, Integer column) {
        if (row < 1 || column < 1){
            throw new BoardExceptions("Erro: O tabuleiro dve ser maior que 1X1");
        }

        this.row = row;
        this.column = column;
        pieces = new Piece[row][column];
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Piece piece(Integer row, Integer column){
        if (!positionExists(row, column)){
            throw new BoardExceptions("Erro: Essa posiação não existe");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position){
        if (!positionExists(position)){
            throw new BoardExceptions("Erro: Essa posiação não existe");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece (Piece piece, Position position){
        if (thereIsPiece(position)){
            throw new BoardExceptions("Erro: já existe uma peça nessa posição");
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public boolean positionExists(int row, int column){
        return row >= 0 && row <= this.row && column >= 0 && column <= this.column;
    }

    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsPiece(Position position){
        if (!positionExists(row, column)){
            throw new BoardExceptions("Erro: Essa posiação não existe");
        }
        return piece(position) != null;
    }
}
