package org.example.boardgame;

public class Position
{
    private Integer row;
    private Integer column;

    public Position() {

    }

    public Position(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(row + ", " + column);
        return sb.toString();
    }
}

