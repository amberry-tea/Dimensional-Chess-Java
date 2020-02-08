package ca.bcit.comp2522.lab2a;

public class Board {
    Tile[] tiles;
    int dimensions;
    int size;
    int arraySize;

    public Board(int size, int dimensions) {
        this.dimensions = dimensions;
        this.size = size;
        arraySize = (int) Math.pow(size, dimensions);
        tiles = new Tile[arraySize];

        for (int i = 0; i < (int) arraySize; i++) {
            //POSSIBLY INACCURATE XYZ VALUES: BE WARY
            tiles[i] = new Tile(i % size, i / (int)Math.pow(size, dimensions-1), i / (int)Math.pow(size, dimensions));
        }
    }

    /**
     * Converts a vector into its index in the tiles array
     * 
     * @param vector The coordinates to convert to index
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
     * @param index The index in the array to convert to coordinates
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
     * @param position The coordinates of the 2D plane in a higher dimension. Eg.
     *                 The 2D plane at the bottom of a 3D chess board would be at
     *                 {0}, whereas the plane above that would be {1}. More indexes
     *                 of the array are for more dimensions.
     * @return
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
            //ans[i % size][(i - index) / size] = tiles[i + index];
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
}
