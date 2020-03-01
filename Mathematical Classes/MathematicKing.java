package ca.bcit.comp2522.lab2a;

public class MathematicKing extends Piece {
    
    public MathematicKing(Team team) {
        super(team);
        pieceType = PieceType.KING;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public PieceType getPieceType() {
        return pieceType;
    }
    
    public String getName() {
        return pieceType.getName();
    }

    @Override
    protected boolean isValidMove(Tile start, Tile finish) {
        Board board = GameController.getBoard();
        int[] startPos = board.getPos(start);
        int[] finishPos = board.getPos(finish);
        
        for(int i = 0; i < startPos.length; i++) {
            if(Math.abs(startPos[i] - finishPos[i]) > 1) //if it moves more than 1 space
                return false;
        }
        return ((GameController.getBoard().moveStraight(start, finish) || GameController.getBoard().moveDiagonal(start, finish)));

    }
    
}
