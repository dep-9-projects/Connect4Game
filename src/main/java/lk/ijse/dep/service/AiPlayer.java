package lk.ijse.dep.service;

public class AiPlayer extends Player{

    int col=0;
    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
//        int random;
//        do {
//            random = (int)(Math.random()*6);
//
//
//        }while (!board.isLegalMoves(random));
//
        col=predictColumn();

        board.updateMove(col,Piece.GREEN);

        board.getBoardUI().update(col,false);

        if (board.findWinner().getCol1()!=-1){
            board.getBoardUI().notifyWinner(board.findWinner());
        } else if (board.existLegalMoves()) {
            return;

        }else {
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }

    }

    public int miniMax(int depth,boolean maximizePlayer){
        Piece winningPiece = board.findWinner().getWinningPiece();
        if (winningPiece==Piece.GREEN){
            return 1;
        } else if (winningPiece==Piece.BLUE) {
            return -1;
        }else {
            if (!board.existLegalMoves() || depth ==8){
                return 0;
            }
        }

        if (!maximizePlayer){
            for (int i = 0; i < Board.NUM_OF_COLS; i++) {
                if (board.isLegalMoves(i)){
                    int row = board.findNextAvailableSpot(i);
                    board.updateMove(i,Piece.BLUE);
                    int heuristicVal = miniMax(depth + 1, true);
                    board.updateMove(i,row,Piece.EMPTY);
                    if (heuristicVal==-1) return heuristicVal;
                }
            }
        }else {
            for (int i = 0; i < Board.NUM_OF_COLS; i++) {
                if (board.isLegalMoves(i)){
                    int row = board.findNextAvailableSpot(i);
                    board.updateMove(i,Piece.GREEN);
                    int heuristicVal = miniMax(depth + 1, false);
                    board.updateMove(i,row,Piece.EMPTY);
                    if (heuristicVal==1) return heuristicVal;
                }
            }

        }

        return 0;


    }

    private int predictColumn(){
        boolean isUserWinning=false;  //
        int tiedColumn=0;

        for (int i = 0; i < Board.NUM_OF_COLS; i++) {

            if (board.isLegalMoves(i)){
                int row =board.findNextAvailableSpot(i);
                board.updateMove(i,Piece.GREEN);
                int heuristicVal =miniMax(0,false);
                board.updateMove(i,row,Piece.EMPTY);
                if (heuristicVal==1){
                    return i;
                } else if (heuristicVal==-1) {
                    isUserWinning=true;

                }else {
                    tiedColumn=i;
                }

            }

        }
        if (isUserWinning && board.isLegalMoves(tiedColumn)){
            return tiedColumn;
        }else {
            int col=0;
            do {
                col=(int)(Math.random()*6);
            }while (!board.isLegalMoves(col));
            return col;
        }


    }
}
