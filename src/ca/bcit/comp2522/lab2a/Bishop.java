package ca.bcit.comp2522.lab2a;

/**
 * A Bishop Piece.
 * <p>
 * Can move diagonally.
 * 
 * @author Andrew Martin
 * @version 2020.02.23
 */
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

    @Override
    protected boolean isValidMove(Tile start, Tile finish) {
        return GameController.getBoard().moveDiagonal(start, finish);
    }
    
}
