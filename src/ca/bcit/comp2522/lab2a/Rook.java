package ca.bcit.comp2522.lab2a;

public class Rook extends Piece {
    
    public Rook(Team team) {
        super(team);
        pieceType = PieceType.ROOK;
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
