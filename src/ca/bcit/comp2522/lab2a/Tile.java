package ca.bcit.comp2522.lab2a;

public class Tile {
    public int x, y, z;
    private Piece piece;
    
    public Tile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        piece = null;
    }
    
    public Tile(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }
    
    public boolean hasPiece() {
        return piece != null;
    }
    
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    public Piece removePiece() {
        Piece temp = piece;
        piece = null;
        return temp;
    }
    
    
}
