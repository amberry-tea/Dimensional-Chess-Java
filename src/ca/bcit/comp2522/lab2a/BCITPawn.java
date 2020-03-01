package ca.bcit.comp2522.lab2a;

/**
 * A Pawn Piece.
 * <p>
 * Has a variable "moved" to store if the piece has moved yet.
 * 
 * @author Andrew Martin
 * @version 2020.02.23
 */
public class BCITPawn extends Pawn {
    
    protected boolean moved;
    
    public BCITPawn(Team team) {
        super(team);
    }
    
    public void setMoved() {
        moved = true;
    }
    
    /**
     * Checks if the move is a valid move. Unique to the pawn class.
     * 
     * @param start
     *      The starting tile that this piece is on
     * @param finish
     *      The finish tile that the move requests
     * @return true if the move is valid
     */
    public boolean isValidMove(Tile start, Tile finish) {
        BCITBoard board = GameController.getBoard();
        int[] movement = board.getMovement(start, finish);

        // the total distance in all axis that the piece wants to move
        int distance = 0;
        // the most distance moved in one axis
        int distanceInAxis = 0;

        for (int i = 0; i < movement.length - 1; i++) {
            distance += Math.abs(movement[i]);
            if (distanceInAxis < Math.abs(movement[i])) {
                distanceInAxis = Math.abs(movement[i]);
            }
        }
        
        //if its moving up or down
        if(movement[2] != 0) {
            return pawnSteppy(start, finish, distance, distanceInAxis);
        }
        
        // if the movement is diagonal
        if (movement[0] != 0) {
            // ...and the pawn is taking a piece legally
            if (Math.abs(movement[0]) == 1 && Math.abs(movement[1]) == 1 && finish.hasPiece()) {
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
            return !moved;
        

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
        
    /**
     * If the pawn is performing a big steppy onto the next plane
     * 
     * @param start
     *      The tile that the pawn starts on
     * @param finish
     *      The requested tile to move to
     * @param distance
     *      The total distance that the pawn moves.
     *      Counts the total number of tiles crossed, thus returns a whole value.
     *      Note that due to calculations, moving diagonally by 1 tile is counted as a distance of 2.
     * @param distanceInAxis
     *      The distance the pawn travels on a single axis.
     * @return true if the move is valid
     */
    public boolean pawnSteppy(Tile start, Tile finish, int distance, int distanceInAxis) {
        BCITBoard board = GameController.getBoard();
        int[] startPos = board.getPos(start);
        int[] finishPos = board.getPos(finish);
        int[] movement = board.getMovement(start, finish);
        int directionVector = getTeam() == Team.WHITE ? 1 : -1;
        int steppyVector = movement[2] > 0 ? 1 : -1;
        
        
        if(!(Math.abs(movement[2]) == Math.abs(movement[1])))
            return false;
        
        // if the movement has a distance of two
        // returns true if its the pawn's first move
        if (distanceInAxis == 2 && movement[0] == 0)
            return !moved;
        
     // if the movement is diagonal
        if (movement[0] != 0) {
            // ...and the pawn is taking a piece legally
            if (distance == 2 && distanceInAxis == 1 && finish.hasPiece()) {
                return true;
            }
            return false;
        }
        
     // if the pawn is moving backwards
        if (start.getPiece().getTeam() == Team.WHITE) {
            if (movement[1] < 0)
                return false;
        } else if (movement[1] > 0)
            return false;
        
        
        //Check for collisions by going along the path and finding pieces
        for (int i = 0; i < Math.abs(movement[2]); i++) {
            startPos[1] -= directionVector;
            startPos[2] -= steppyVector;
            if (board.getTile(startPos).hasPiece() && startPos[1] != finishPos[1]) {
                return false;
            }
        }
        
        //otherwise, as long as the landing piece has nothing else on it, return true
        return !(board.getTile(finishPos).hasPiece());
    }
}
