package hr.algebra.models;


public class Move {
    private int ROW;
    private int COLUMN;

    private String color;

    public Move(int row, int column, String color) {
        this.ROW = row;
        this.COLUMN = column;
        this.color = color;
    }

    public int getROW() {
        return ROW;
    }

    public void setROW(int ROW) {
        this.ROW = ROW;
    }

    public String getCOLUMN() {
        return String.valueOf(COLUMN);
    }

    public void setCOLUMN(int COLUMN) {
        this.COLUMN = COLUMN;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
