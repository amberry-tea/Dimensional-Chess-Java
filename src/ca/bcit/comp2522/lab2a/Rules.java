package ca.bcit.comp2522.lab2a;

public class Rules {
    private static Board piecePlacement;
    private static int size;
    private static int dimensions;

    public Rules(int size, int dimensions) {
        this.size = size;
        this.dimensions = dimensions;
        piecePlacement = new Board(size, dimensions);
        setPiecePlacement(piecePlacement);
    }

    private static void setPiecePlacement(Board board) {
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
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
             * piecePlacement in 3D
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

    }

    public static Board getPiecePlacement2D() {
        return piecePlacement;
    }

    public static boolean isValidMove(Tile start, Tile finish) {
        // only check move if finish tile is empty or has enemy piece on it
        if (!finish.hasPiece()
                || (finish.hasPiece() 
                        && !start.getPiece().getTeam().equals(
                                finish.getPiece().getTeam()))) {

            if (start.getPiece().getName().equals("Pawn")) {
                return checkPawnMovement(start, finish);
            } else {
                System.out.println("Pawn name not found!");
                return false;
            }

        } else {
            return false;
        }
    }

    private static boolean checkPawnMovement(Tile start, Tile finish) {
        Board board = GameController.getBoard();
        // The pawn can only move one step. Thus, the number of valid cases for its move
        // can only be 1.
        int validCases = 0;
        int[] startPos = board.getPos(start);
        int[] finishPos = board.getPos(finish);
        boolean twoStep = !((Pawn) (start.getPiece())).hasMoved();

        // check if the piece moves on the x axis, then:
        // return false if the piece tries to move +-1 on x and the finish
        // tile does not have an enemy piece on it
        if (startPos[0] != finishPos[0]) {
            if (!(finish.hasPiece() && finish.getPiece().getTeam().getChar() == 'b'
                    && (startPos[0] == finishPos[0] + 1 || startPos[0] == finishPos[0] - 1))) {
                return false;
            }
        }

        // if piece is white
        if (start.getPiece().getTeam().getChar() == 'w') {
            for (int i = 1; i < dimensions; i++) {

                // if the piece moves -1 in any direction except the x plane,
                if ((startPos[i] - 1 == finishPos[i]) && !finish.hasPiece()) {
                    validCases++;
                }
                // if the piece moves -2 for its first move
                if ((startPos[i] - 2 == finishPos[i]) && !finish.hasPiece() && twoStep) {
                    validCases++;
                }
            }
        } else if (start.getPiece().getTeam().getChar() == 'b') {
            for (int i = 0; i < dimensions; i++) {
                return true;
            }
        }
        
        //System.out.println(validCases);
        if(validCases == 2 && finish.hasPiece())
            return true;
        return validCases == 1;
    }

}
