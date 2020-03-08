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
public class Board {
    Tile[] tiles;
    private int dimensions;
    private int size;
    private int arraySize;

    /**
     * Constructor for a Board object
     * @param size 
     *      The size of the board
     * @param dimensions 
     *      The number of dimensions the board is in
     */
    public Board(int size, int dimensions) {
        this.dimensions = dimensions;
        this.size = size;
        arraySize = (int) Math.pow(size, dimensions);
        tiles = new Tile[arraySize];

        for (int i = 0; i < (int) arraySize; i++) {
            tiles[i] = new Tile(i % size, i / (int)Math.pow(size, dimensions-1), i / (int)Math.pow(size, dimensions));
        }
    }

    /**
     * Converts a vector into its index in the tiles array
     * 
     * @param vector 
     *      The coordinates to convert to index
     * @return The index equivalent for the tiles array
     */
    private int vectorToIndex(int[] vector) {
        int index = 0;
        for (int i = 0; i < vector.length; i++) {
            index += vector[i] * (Math.pow(size, i));
        }
        return index;
    }

    /**
     * Converts an index in the tiles array to a vector.
     * 
     * @param index 
     *      The index in the array to convert to coordinates
     * @return The coordinates at the provided index
     */
    private int[] indexToVector(int index) {
        int[] vector = new int[dimensions];
        for(int i = 0; i < dimensions; i++) {
            vector[i] = index % size;
            index /= size;
        }
        return vector;
    }

    public Tile getTile(int[] coords) {
        // The index of the tile in the array
        return tiles[vectorToIndex(coords)];
    }
    
    public int[] getPos(Tile tile) {
        for(int i = 0; i < arraySize; i++) {
            if(tile == tiles[i]) {
                return indexToVector(i);
            }
        }
        System.out.println("No position found for tile: " + tile +" !");
        int[] noPosition = {};
        return noPosition;
    }

    /**
     * Takes the coordinates of a dimension greater than 2, and returns the 2d plane
     * at those coordinates.
     * 
     * @param position 
     *      The coordinates of the 2D plane in a higher dimension. Eg.
     *      The 2D plane at the bottom of a 3D chess board would be at
     *      {0}, whereas the plane above that would be {1}. More indexes
     *      of the array are for more dimensions.
     * @return
     *      A 2D cross section of the board in a 2D array of tiles
     */
    public Tile[][] getTiles2D(int[] position) {
        Tile[][] ans = new Tile[size][size];

        // If the dimension is 1, assigns the entire tiles array to the first dimension
        // of the Tile[][] ans.
        if (tiles.length == size) {
            ans[0] = tiles;
            return ans;
        }

        // The index of the first block in the specified position
        int index = 0;

        // Adds each coordinate to its corresponding power for dimension, which is
        // incremented by i
        for (int i = 0; i < position.length; i++) {
            index += position[i] * Math.pow(size, i + 2);
        }
        
        // From the starting point retrieved by the previous for loop,
        // index through size^2 tiles and add each one to its corresponding
        // coordinates in a 2D array
        for (int i = index; i < index + Math.pow(size, 2); i++) {
            ans[i % size][(i - index) / size] = tiles[i];
            
        }
        return ans;
    }
    
    public String putPiece(int[] pos, Piece piece) {
        Tile temp = getTile(pos);
        Piece replaced = temp.getPiece();
        temp.setPiece(piece);
        return "" + piece.getPieceType().getName() + " was placed on " + temp.x + ", " + temp.y + ", " + temp.z;
    }
    
    /**
     * Gets a vector representing the change in position.
     * @param a 
     *      The first tile
     * @param b 
     *      The second tile
     * @return The distance between the two tiles
     */
    public int[] getMovement(Tile a, Tile b) {
        int[] aPos = getPos(a);
        int[] bPos = getPos(b);
        int[] movement = new int[aPos.length];

        for (int i = 0; i < aPos.length; i++) {
            movement[i] = aPos[i] - bPos[i];
        }

        return movement;
    }
    
    /**
     * Calculates a pieces movement along 1 axis.
     * 
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

        // for loop retrieves the axis of movement for the rook
        for (int i = 0; i < startPos.length; i++) {
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
     * Calculates a pieces movement if it is traveling
     * along 2 axis.
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
        //the distance traveled diagonally
        Integer length = null;
        //the number of axis that the piece travels on.
        //returns false if more than 2
        int axisCount = 0;
        
        //the dimension of the first axis traveled
        int axisA = 0;
        //the dimension of the second axis traveled
        int axisB = 0;
        
        //TODO: Use length to verify that the movement along the two axis of travel are of equal length. Then use the movement vector to choose a direction to go along and test each tile.

        // for loop retrieves the axis of movement for the rook
        for (int i = 0; i < movement.length; i++) {
            // if there is movement on the axis 'i'
            if (startPos[i] != finishPos[i]) {
                
                axisCount++;
                
                //set axis 1 to the fist axis traveled along, then set the second axis to the next axis traveled on.
                if(axisA == 0) {
                    axisA = i;
                } else {
                    axisB = i;
                }
                
                if(length != null && length != Math.abs(movement[i])) {
                    return false; //if its already set, return false.
                } else if (length == null) {
                    length = Math.abs(movement[i]); //sets the distance traveled if it is not already set
                }
                
            }
        }
        
        //if the number of axis traveled along is greater than 2
        if(axisCount != 2)
            return false;
        
        int[] directionVector = new int[2];
        directionVector[0] = movement[axisA] > 0 ? 1 : -1;
        directionVector[1] = movement[axisB] > 0 ? 1 : -1;
        
        for (int i = 0; i < length; i++) {
            startPos[axisA] -= directionVector[0];
            startPos[axisB] -= directionVector[1];
            if (getTile(startPos).hasPiece() && startPos[axisA] != finishPos[axisA]) {
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
