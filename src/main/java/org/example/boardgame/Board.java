package org.example.boardgame;

public class Board {

    private Integer row;
    private Integer column;
    private Piece[][] pieces;

    public Board(Integer row, Integer column) {
        if (row < 1 || column < 1){
            throw new BoardExceptions("Erro: O tabuleiro deve ser maior que 1x1");
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

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardExceptions("Position not on the board");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardExceptions("Position not on the board");
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

    public Piece removePiece(Position position){
        if (!positionExists(position)){
            throw new BoardExceptions("Erro: Essa posição não existe");
        }
        if(piece(position) == null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    public boolean positionExists(int row, int column){
        return row >= 0 && row < this.row && column >= 0 && column < this.column;
    }

    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsPiece(Position position){
        if (!positionExists(position)){
            throw new BoardExceptions("Erro: Essa posição não existe");
        }
        return piece(position) != null;
    }
}

