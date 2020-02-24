package ca.bcit.comp2522.lab2a;

public class Rook extends Piece {
    
    public Rook(Team team) {
        super(team);
        pieceType = PieceType.ROOK;
    }
    
    public boolean isValidMove(Tile start, Tile finish) {
        return GameController.getBoard().moveStraight(start, finish);
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
    
}
