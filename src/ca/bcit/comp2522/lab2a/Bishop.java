package ca.bcit.comp2522.lab2a;

public class Bishop extends Piece {
    
    public Bishop(Team team) {
        super(team);
        pieceType = PieceType.BISHOP;
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
