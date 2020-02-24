package ca.bcit.comp2522.lab2a;

/**
 * Class which checks how pieces can move on the board, as well as how the
 * board is set up.
 * <p>
 * For now, the board set up will be hard coded in this class. In the future,
 * board set up should be implemented as a "game mode" where players can place
 * pieces on a board of their choosing, and save that board as a file to their
 * PC.
 * 
 * @author Andrew Martin
 * @version 2020.02.23
 */
public class Rules {
    // Constructs and returns a board with pieces in the appropriate slots.
    public static Board getPiecePlacement(int size, int dimensions) {
        Board board;
        board = new Board(size, dimensions);

        if (dimensions == 2 && size == 8) {
            /*
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ piecePlacement in 2D
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
             */
            int[] pos = new int[dimensions];
            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 0;
                if (c == 0 || c == 7) {
                    board.putPiece(pos, new Rook(Team.BLACK));
                } else if (c == 1 || c == 6) {
                    board.putPiece(pos, new Knight(Team.BLACK));
                } else if (c == 2 || c == 5) {
                    board.putPiece(pos, new Bishop(Team.BLACK));
                } else if (c == 3) {
                    board.putPiece(pos, new King(Team.BLACK));
                } else if (c == 4) {
                    board.getTile(pos).setPiece(new Queen(Team.BLACK));
                }
            }

            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 1;
                board.getTile(pos).setPiece(new Pawn(Team.BLACK));
            }

            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 7;
                if (c == 0 || c == 7) {
                    board.putPiece(pos, new Rook(Team.WHITE));
                } else if (c == 1 || c == 6) {
                    board.putPiece(pos, new Knight(Team.WHITE));
                } else if (c == 2 || c == 5) {
                    board.putPiece(pos, new Bishop(Team.WHITE));
                } else if (c == 3) {
                    board.putPiece(pos, new King(Team.WHITE));
                } else if (c == 4) {
                    board.putPiece(pos, new Queen(Team.WHITE));
                }
            }

            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 6;
                board.getTile(pos).setPiece(new Pawn(Team.WHITE));
            }

        } else if (dimensions == 3 && size == 8) {
            /*
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ piecePlacement in 3D
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
             */
            int[] pos = new int[dimensions];
            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 0;
                pos[2] = 0;
                if (c == 0 || c == 7) {
                    board.putPiece(pos, new Rook(Team.BLACK));
                } else if (c == 1 || c == 6) {
                    board.putPiece(pos, new Knight(Team.BLACK));
                } else if (c == 2 || c == 5) {
                    board.putPiece(pos, new Bishop(Team.BLACK));
                } else if (c == 3) {
                    board.putPiece(pos, new King(Team.BLACK));
                } else if (c == 4) {
                    board.getTile(pos).setPiece(new Queen(Team.BLACK));
                }
            }

            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 1;
                pos[2] = 0;
                board.putPiece(pos, new Pawn(Team.BLACK));
            }

            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 7;
                pos[2] = 2;
                if (c == 0 || c == 7) {
                    board.putPiece(pos, new Rook(Team.WHITE));
                } else if (c == 1 || c == 6) {
                    board.putPiece(pos, new Knight(Team.WHITE));
                } else if (c == 2 || c == 5) {
                    board.putPiece(pos, new Bishop(Team.WHITE));
                } else if (c == 3) {
                    board.putPiece(pos, new King(Team.WHITE));
                } else if (c == 4) {
                    board.putPiece(pos, new Queen(Team.WHITE));
                }
            }

            for (int c = 0; c < size; c++) {
                pos[0] = c;
                pos[1] = 6;
                pos[2] = 2;
                board.getTile(pos).setPiece(new Pawn(Team.WHITE));
            }

        } else {
            System.out.println("ERROR: No default board of size " + size + " and dimensions " + dimensions + "!");
        }

        return board;
    }

    public static <P extends Piece> boolean isValidMove(Tile start, Tile finish) {
        P piece = (P) start.getPiece();
        // only check move if finish tile is empty or has enemy piece on it
        if (!finish.hasPiece()
                || (finish.hasPiece() && !start.getPiece().getTeam().equals(finish.getPiece().getTeam()))) {
            return piece.isValidMove(start, finish);
        } else {
            return false;
        }
    }

}
