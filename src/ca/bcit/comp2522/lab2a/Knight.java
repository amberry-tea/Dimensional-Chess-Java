package ca.bcit.comp2522.lab2a;

public class Knight extends Piece {

    public Knight(Team team) {
        super(team);
        pieceType = PieceType.KNIGHT;
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
        Board board = GameController.getBoard();
        int[] startPos = board.getPos(start);
        int[] finishPos = board.getPos(finish);
        int[] movement = board.getMovement(start, finish);
        // the distance traveled diagonally
        Integer length = null;
        // the number of axis that the piece travels on.
        // returns false if more than 2
        int axisCount = 0;
        // the dimension of the first axis traveled
        int axisA = 0;
        // the dimension of the second axis traveled
        int axisB = 0;

        // TODO: Use length to verify that the movement along the two axis of travel are
        // of equal length. Then use the movement vector to choose a direction to go
        // along and test each tile.

        // for loop retrieves the axis of movement for the rook
        for (int i = 0; i < movement.length; i++) {
            // if there is movement on the axis 'i'
            if (startPos[i] != finishPos[i]) {

                axisCount++;

                // set axis 1 to the fist axis traveled along, then set the second axis to the
                // next axis traveled on.
                if (axisA == 0) {
                    axisA = i;
                } else {
                    axisB = i;
                }

            }
        }

        if (axisCount == 2 && ((Math.abs(movement[axisA]) == 2 && Math.abs(movement[axisB]) == 1)
                || (Math.abs(movement[axisB]) == 2 && Math.abs(movement[axisA]) == 1))) {
            return true;

        }

        return false;
    }
}
