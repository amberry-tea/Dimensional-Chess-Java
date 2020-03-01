package ca.bcit.comp2522.lab2a;

/**
 * Stores and retrieves Tile objects, which contain pieces. 
 * Also contains and manages information about the board and its set up.
 * <p>
 * Uses a one dimensional array to store all the tiles on the board, and divides up
 * the dimensions procedurally depending on the number of dimensions and the size of the
 * board.
 * 
 * @author Andrew Martin
 * @version 2020.02.23
 */
public class BCITBoard extends Board {
    /** 
     * The tiles contained in a 1D array,
     * which is parsed for its dimensional
     * coordinates using functions.
     */
    Tile[] tiles;
    private int dimensions;

    public BCITBoard(int size, int dimensions) {
        super(size, dimensions);
    }
    
    /**
     * Calculates a pieces movement in a straight line,
     * as well as checking for collisions.
     * @param a 
     *      The start tile of the piece
     * @param b 
     *      The piece where the tile lands
     * @return True if it is a straight line.
     */
    public boolean moveStraight(Tile a, Tile b) {
        int[] startPos = getPos(a);
        int[] finishPos = getPos(b);
        int[] movement = getMovement(a, b);
        int axis = -1;

        // for loop retrieves the axis of movement for the rook, except from the third dimension
        for (int i = 0; i < startPos.length - 1; i++) {
            if (startPos[i] != finishPos[i]) {
                // check if the axis has not been changed. if changed, return false (not
                // straight movement)
                if (axis == -1) {
                    axis = i;
                } else {
                    return false;
                }
            }
        }

        
        //3d movement part
        if(axis == -1)
            return false;
        
        if(movement[2] != 0) {
            return straightSteppy(a, b, axis);
        }
        
        int directionVector = movement[axis] > 0 ? 1 : -1;

        // for loop that loops through the absolute distance between the start and
        // finish on axis
        // subtract 1 from the absolute distance, as to not check the tile that the tile
        // moves to
        for (int i = 0; i < Math.abs(movement[axis]); i++) {
            startPos[axis] -= directionVector;
            if (getTile(startPos).hasPiece() && startPos[axis] != finishPos[axis]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calculates a pieces movement onto another plane in 3D
     * if the piece is moving only on one 2D axis and the z axis.
     * 
     *  @param a 
     *      The start tile of the piece
     * @param b 
     *      The piece where the tile lands
     * @param axis
     *      The axis in 2D along which the piece is traveling.
     *      The piece will always be moving along the z axis.
     * @return 
     *      True if the piece moves in 45 degrees along the z axis
     *      and one two dimensional axis. There must also be no collisions
     *      along the pieces path.
     */
    public boolean straightSteppy(Tile a, Tile b, int axis) {
        int[] movement = getMovement(a, b);
        int[] startPos = getPos(a);
        int[] finishPos = getPos(b);
        int directionVector = movement[axis] > 0 ? 1 : -1;
        int steppyVector = movement[2] > 0 ? 1 : -1;
        //System.out.println(steppyVector);
        
        if(!(Math.abs(movement[2]) == Math.abs(movement[0]) 
                || Math.abs(movement[2]) == Math.abs(movement[1])))
            return false;
        
        for (int i = 0; i < Math.abs(movement[2]); i++) {
            startPos[axis] -= directionVector;
            startPos[2] -= steppyVector;
            if (getTile(startPos).hasPiece() && startPos[axis] != finishPos[axis]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Calculates a pieces movement if it is traveling at a
     * diagonal on the x and y axis. Calls another function if the
     * piece travels on the z axis aswell.
     * 
     *  @param a 
     *      The start tile of the piece
     * @param b 
     *      The piece where the tile lands
     * @return True if it is a diagonal line.
     */
    public boolean moveDiagonal(Tile a, Tile b) {
        int[] startPos = getPos(a);
        int[] finishPos = getPos(b);
        int[] movement = getMovement(a, b);
        
        int[] directionVector = new int[2];
        directionVector[0] = movement[0] > 0 ? 1 : -1;
        directionVector[1] = movement[1] > 0 ? 1 : -1;
        
        if(movement[2] != 0)
            return diagonalSteppy(a, b, directionVector);
        
        if(Math.abs(movement[0]) != Math.abs(movement[1]))
            return false;
        
        for (int i = 0; i < Math.abs(movement[0]); i++) {
            startPos[0] -= directionVector[0];
            startPos[1] -= directionVector[1];
            if (getTile(startPos).hasPiece() && startPos[0] != finishPos[0]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calculates a pieces movement onto another plane in 3D
     * if the piece is moving on all three axis.
     * 
     *  @param a 
     *      The start tile of the piece
     * @param b 
     *      The piece where the tile lands
     * @param axis
     *      The direction which the piece moves on the x and y plane.
     *      The piece will always be moving along the z axis.
     * @return 
     *      True if the piece moves on all 3 axis in a perfect
     *      diagonal fashion.
     */
    public boolean diagonalSteppy(Tile a, Tile b, int[] axis) {
        int[] movement = getMovement(a, b);
        int[] startPos = getPos(a);
        int[] finishPos = getPos(b);
        int steppyVector = movement[2] > 0 ? 1 : -1;
        //System.out.println(steppyVector);
        
        if(!(Math.abs(movement[2]) == Math.abs(movement[0]) 
                && Math.abs(movement[2]) == Math.abs(movement[1])))
            return false;
        
        for (int i = 0; i < Math.abs(movement[2]); i++) {
            startPos[0] -= axis[0];
            startPos[1] -= axis[1];
            startPos[2] -= steppyVector;
            if (getTile(startPos).hasPiece() && startPos[0] != finishPos[0]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Debug function to check what a move is doing. Prints
     * info to the console.
     * 
     * @param start
     *      The start tile of the move
     * @param finish
     *      The finish tile of the move
     */
    public void debugMove(Tile start, Tile finish) {
        int[] movement = getMovement(start, finish);
        int[] startPos = getPos(start);
        int[] finishPos = getPos(finish);

        if (dimensions == 2) {
            System.out.println("Start tile coordinates x:" + startPos[0] + " y:" + startPos[1]);
            System.out.println("Finish tile coordinates x:" + finishPos[0] + " y:" + finishPos[1]);
            System.out.println("Vector for movement x:" + movement[0] + " y:" + movement[1]);
        } else {
            System.out.println("Start tile coordinates x:" + startPos[0] + " y:" + startPos[1]);
            System.out.println("Finish tile coordinates x:" + finishPos[0] + " y:" + finishPos[1]);
            System.out.println("Vector for movement x:" + movement[0] + " y:" + movement[1] + " z:" + movement[2]);
        }

        System.out.println();
    }
}
