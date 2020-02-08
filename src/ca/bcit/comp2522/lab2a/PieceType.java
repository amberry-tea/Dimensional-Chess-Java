package ca.bcit.comp2522.lab2a;

public enum PieceType {
    PAWN("Pawn"),
    BISHOP("Bishop"),
    KNIGHT("Knight"),
    ROOK("Rook"),
    QUEEN("Queen"),
    KING("King");
    
    private String pieceName;

    PieceType(String pieceName){
        this.pieceName = pieceName;
    }
    
    public String getName() {
        return pieceName;
    }
}
