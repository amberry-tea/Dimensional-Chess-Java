package ca.bcit.comp2522.lab2a;

public abstract class Piece {
    
    protected Team team;
    protected PieceType pieceType;
    
    public Piece(Team team) {
        this.team = team;
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
