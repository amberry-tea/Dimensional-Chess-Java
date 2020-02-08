package ca.bcit.comp2522.lab2a;

public class Knight extends Piece {
    
    public Knight(Team team) {
        super(team);
        pieceType = PieceType.KNIGHT;
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
