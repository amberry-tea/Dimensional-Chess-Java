package ca.bcit.comp2522.lab2a;

public class King extends Piece {
    
    public King(Team team) {
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
    
}
