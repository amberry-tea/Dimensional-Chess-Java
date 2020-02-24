package ca.bcit.comp2522.lab2a;

/**
 * A Pawn Piece.
 * <p>
 * Has a variable "moved" to store if the piece has moved yet.
 * 
 * @author Andrew Martin
 * @version 2020.02.23
 */
public class Pawn extends Piece {
    
    private boolean moved;
    
    public Pawn(Team team) {
        super(team);
        pieceType = PieceType.PAWN;
        moved = false;
    }
    
    public boolean isValidMove(Tile start, Tile finish) {
        Board board = GameController.getBoard();

        // If the pawn has moved
        boolean twoStep = !(((Pawn) (start.getPiece())).hasMoved());
        int[] movement = board.getMovement(start, finish);

        // the total distance in all axis that the piece wants to move
        int distance = 0;
        // the most distance moved in one axis
        int distanceInAxis = 0;

        for (int i = 0; i < movement.length; i++) {
            distance += Math.abs(movement[i]);
            if (distanceInAxis < Math.abs(movement[i])) {
                distanceInAxis = Math.abs(movement[i]);
            }
        }
        
        // if the movement is diagonal
        if (distance != distanceInAxis) {
            // ...and the pawn is taking a piece legally
            if (distance == 2 && distanceInAxis == 1 && finish.hasPiece()) {
                return true;
            }
            return false;
        }
        
        //otherwise, if the move isnt diagonal and it's landing on a piece
        if(finish.hasPiece())
            return false;

        // if the movement has a distance greater than 2
        if (distanceInAxis > 2)
            return false;
        

        // if the movement has a distance of two
        // returns true if its the pawn's first move
        if (distanceInAxis == 2)
            return twoStep;
        

        // if any movement along the x axis
        if (movement[0] != 0)
            return false;
        

        // if the pawn is moving backwards
        if (start.getPiece().getTeam() == Team.WHITE) {
            if (movement[1] < 0)
                return false;
        } else if (movement[1] > 0)
            return false;

        return true;
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
    
    public void setMoved() {
        moved = true;
    }
}
