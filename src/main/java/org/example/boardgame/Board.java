package org.example.boardgame;

public class Board {

    private Integer row;
    private Integer column;
    private Piece[][] pieces;

    public Board(Integer row, Integer column) {
        this.row = row;
        this.column = column;
        pieces = new Piece[row][column];
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Piece piece(Integer row, Integer column){
        return pieces[row][column];
    }

    public Piece piece(Position position){
        return pieces[position.getRow()][position.getColumn()];
    }
}
