package view.mainView;

public record Index(int row, int col) {

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
