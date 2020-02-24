package ca.bcit.comp2522.lab2a;

/**
 * Abstract class that represents a piece type.
 * <p>
 * Pieces are defined by their Team and PieceType, each of which are
 * an enumerated class.
 * 
 * @author Andrew Martin
 * @version 2020.02.23
 */
public abstract class Piece {
    
    protected Team team;
    protected PieceType pieceType;
    
    public Piece(Team team) {
        this.team = team;
    }
    
    protected abstract boolean isValidMove(Tile start, Tile finish);
    
    
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
