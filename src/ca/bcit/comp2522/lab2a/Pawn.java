package ca.bcit.comp2522.lab2a;

public class Pawn extends Piece {
    
    private boolean moved;
    
    public Pawn(Team team) {
        super(team);
        pieceType = PieceType.PAWN;
        moved = false;
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
    
    public boolean hasMoved() {
        return moved;
    }
}
