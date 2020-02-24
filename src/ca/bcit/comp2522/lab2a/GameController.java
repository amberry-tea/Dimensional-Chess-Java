package ca.bcit.comp2522.lab2a;

/**
 * The main controller of the game components.
 * <p>
 * Controls piece selection and movement using the GUI and Rules classes.
 * 
 * @author Andrew Martin
 * @version 2020.02.23
 */
public class GameController {
    public static final int SIZE = 8;
    public static final int DIMENSIONS = 3;
    public static final int HEIGHT = 2;
    
    //private static Board board = new Board(SIZE, DIMENSIONS);
    private static Board board = Rules.getPiecePlacement(SIZE, DIMENSIONS);
    private static Tile selected;
    private static GUI gui = new GUI();
    //who's turn it is
    private static int turn;
    //how many turns have passed since the start of the game
    private static int turnNum;
    
    public static Board getBoard() {
        return board;
    }
    
    public static void selectTile(int[] mousePos) {
        //print statements to check the click position against the coordinates of the tile
        //System.out.println("MousePos: " + mousePos[0] + " " + mousePos[1]);
        //System.out.println("TileCoords: " + board.getTile(mousePos).x + " " + board.getTile(mousePos).y);
        
        Tile selectedTemp = board.getTile(mousePos);
        if(selected == null && selectedTemp.hasPiece()) //player selects a piece
        {
            //DEBUG::::
            //inserted true here to remove turn taking
            if(true || selectedTemp.getPiece().getTeam().getNum() == turn)
                selectPiece(selectedTemp);
        } 
        else if (selected != null) //player attempts to move the piece
        {
            if(movePiece(selected, selectedTemp)) {
                turnNum++;
                turn = Team.nextTurn(turn);
            } else {
                selected = null;
                gui.update();
            }
        }
    }
    
    private static boolean movePiece(Tile start, Tile finish) {
        //DEBUG: Debugger to check move data
        board.debugMove(start, finish);
        
        //check with rules and return that boolean
        if(Rules.isValidMove(start, finish)) {
            if(selected.getPiece().getPieceType() == PieceType.PAWN) {
                ((Pawn)selected.getPiece()).setMoved();
            }
            finish.setPiece(start.removePiece());
            selected = null;
            gui.update();
            return true;
        } else {
            return false;
        }
    }

    public static void selectPiece(Tile a) {
        selected = a;
    }
    
    public static int getTurnNum() {
        return turnNum;
    }
    
    public static boolean hasSelection() {
        return selected != null;
    }
    
    public static Tile getSelected() {
        return selected;
    }
    
    
}
