package ast;

public class Position {
    private int _row;
    private int _col;

    public Position(int row, int column) {
	_row = row;
	_col = column;
    }

    public int getRow() {
	return _row;
    }
    
    public int getColumn() {
	return _col;
    }

    public void setRow(int row) {
	_row = row;
    }

    public void setColumn(int column) {
	_col = column;
    }
}
