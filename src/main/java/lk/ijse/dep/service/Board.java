package lk.ijse.dep.service;

public interface Board {
    public final int NUM_OF_ROWS=5;
    public final int NUM_OF_COLS=6;

    public BoardUI getBoardUI();

    public int findNextAvailableSpot(int col);

    public  boolean isLegalMoves(int col);

    public void updateMove(int col,int row,Piece move);

    public boolean existLegalMoves();

    public void updateMove(int col,Piece move);
    public Winner findWinner();


}
