package ca.bcit.comp2522.lab2a;

public class Queen extends Piece {
    
    public Queen(Team team) {
        super(team);
        pieceType = PieceType.QUEEN;
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
        return (GameController.getBoard().moveStraight(start, finish) || GameController.getBoard().moveDiagonal(start, finish));
    }
    
}
