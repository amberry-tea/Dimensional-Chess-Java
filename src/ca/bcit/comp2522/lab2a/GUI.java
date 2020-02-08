package ca.bcit.comp2522.lab2a;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Class that draws a star using JavaFX and the users mouse inputs.
 * <p>
 * Draws a star using lines in JavaFX using the users mouse. When the mouse is
 * clicked and dragged, a star will be drawn and can be resized and rotated by
 * the angle and distance from the initial click point.
 * </p>
 *
 * @author Andrew Martin
 * @version 1.0
 */
public class GUI extends Application {

    // 0: No highlighting, no AI
    // 1: Highlighting, no AI
    // 2: No highlighting, AI
    // 3: Highlighting, AI

    public static final int MODE = 1;

    public static final int HEIGHT = 750;
    public static final int WIDTH = 750;

    public static final int GRID_WIDTH = 8;
    public static final int GRID_HEIGHT = 8;

    public static final int TILE_WIDTH = WIDTH / GRID_WIDTH;
    public static final int TILE_HEIGHT = HEIGHT / GRID_HEIGHT;

    private static Color light;
    private static Color dark;
    private static Color lightSelected;
    private static Color darkSelected;
    private static Color selectCircleColor;
    /** Array of the points for the grid. */
    // public static Point2D[][] grid;
    private static Rectangle[][] tiles;
    private static Circle[][] selectCircles;

    /** The contents of the application scene. */
    private static Group root;

    private static ArrayList<Node> onScreen;

    /*
     * The current 2D plane position on the board.
     * First coordinate represents the position on a 3D board.
     */
    private static int[] dimensionPos;

    /**
     * Displays an initially empty scene, waiting for the user to draw lines with
     * the mouse.
     * 
     * @param primaryStage a Stage
     */
    public void start(Stage primaryStage) {
        onScreen = new ArrayList<Node>();
        root = new Group();
        light = Color.rgb(240, 217, 181);
        dark = Color.rgb(181, 136, 99);
        lightSelected = Color.rgb(174, 177, 135);
        darkSelected = Color.rgb(132, 121, 78);
        selectCircleColor = Color.rgb(126, 191, 94);

        // Instantiate variables here
        dimensionPos = new int[GameController.DIMENSIONS - 1];
        dimensionPos[0] = 0;

        update();

        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BEIGE);

        scene.setOnMousePressed(this::mousePressed);
        scene.setOnKeyPressed(this::keyPressed);

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();

        update();

    }

    /**
     * Launches the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void update() {
        root.getChildren().clear();
        onScreen = getNodes2D(dimensionPos);
        if(GameController.hasSelection()) {
            onScreen.addAll(showValidMoves(GameController.getSelected()));
        }
        int[] tempPos = dimensionPos;
        //if(dimensionPos == 0) {
            
        //}
        for (Node n : onScreen) {
            root.getChildren().add(n);
        }
    }

    public static int addChild(Node n) {
        onScreen.add(n);
        return onScreen.size();
    }
    
    public ArrayList<Node> showValidMoves(Tile a) {
        ArrayList<Node> screen = new ArrayList<Node>();
        Tile[][] boardTiles = GameController.getBoard().getTiles2D(dimensionPos);
        selectCircles = new Circle[GRID_WIDTH][GRID_HEIGHT];
        
        for(int c = 0; c < boardTiles.length; c++) {
            for(int r = 0; r < boardTiles[0].length; r++) {
                if(Rules.isValidMove(a, boardTiles[c][r])) {
                    Circle dot = new Circle(c * TILE_WIDTH + (TILE_WIDTH / 2), r * TILE_HEIGHT + (TILE_HEIGHT / 2), 15);
                    dot.setFill(selectCircleColor);
                    selectCircles[c][r] = dot;
                    screen.add(dot);
                }
            }
        }
        return screen;
    }

    public void mousePressed(MouseEvent event) {
        if ((int) (event.getX() / TILE_WIDTH) < tiles.length && (int) (event.getY() / TILE_HEIGHT) < tiles[0].length) {
            
            int[] mousePos = { (int) (event.getX() / TILE_WIDTH), (int) (event.getY() / TILE_HEIGHT), dimensionPos[0]};
            Rectangle tileClicked = tiles[mousePos[0]][mousePos[1]];
            Color c = (Color) tileClicked.getFill();
            if (c.equals(dark)) {
                tileClicked.setFill(Color.rgb(132, 121, 78));
            } else {
                tileClicked.setFill(Color.rgb(174, 177, 135));
            }

            GameController.selectTile(mousePos);
            update();

        } else {
            System.out.println("Clicked out of bounds!");
        }
    }
    
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
            if(dimensionPos[0] < GameController.HEIGHT) {
                dimensionPos[0]++;
                update();
            }
        }
        if (event.getCode() == KeyCode.LEFT) {
            if(dimensionPos[0] > 0) {
                dimensionPos[0]--;
                update();
            }
        }
    }

    private Image getImage(String pieceName) {
        try {
            String appMain = System.getProperty("user.dir");
            Image image = new Image(
                    "file:" + appMain + "\\src\\ca\\bcit\\comp2522\\lab2a\\resources\\" + pieceName + ".png",
                    TILE_WIDTH, TILE_HEIGHT, false, false);
            return image;
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Failed to load image " + pieceName + " in GUI class!");
            throw e;
        } catch (NullPointerException e) {
            System.out.println("image is null");
            return null;
        }
    }

    private ArrayList<Node> getNodes2D(int[] dimensionPosition) {
        ArrayList<Node> screen = new ArrayList<Node>();
        tiles = new Rectangle[GRID_WIDTH][GRID_HEIGHT];

        boolean isLight = false;

        Tile[][] boardTiles = GameController.getBoard().getTiles2D(dimensionPosition);
        for (int c = 0; c < GRID_WIDTH; c++) {
            for (int r = 0; r < GRID_HEIGHT; r++) {
                tiles[c][r] = new Rectangle(c * TILE_WIDTH, r * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                if (isLight) {
                    tiles[c][r].setFill(light);
                } else {
                    tiles[c][r].setFill(dark);
                }
                isLight = !isLight;
                screen.add(tiles[c][r]);
                if (boardTiles[c][r].hasPiece()) {
                    Piece piece = boardTiles[c][r].getPiece();
                    ImageView img = new ImageView(getImage(piece.getTeam().getChar() + piece.getName()));
                    img.setX(c * TILE_WIDTH);
                    img.setY(r * TILE_HEIGHT);
                    screen.add(img);
                }
            }
            isLight = !isLight;
        }
        return screen;
    }
    
    private ArrayList<Node> getPieces2D(int[] dimensionPosition, Color color){
        ArrayList<Node> screen = new ArrayList<Node>();
        Tile[][] boardTiles = GameController.getBoard().getTiles2D(dimensionPosition);

        for (int c = 0; c < GRID_WIDTH; c++) {
            for (int r = 0; r < GRID_HEIGHT; r++) {
                if (boardTiles[c][r].hasPiece()) {
                    Piece piece = boardTiles[c][r].getPiece();
                    ImageView img = new ImageView(getImage(piece.getTeam().getChar() + piece.getName()));
                    img.setX(c * TILE_WIDTH);
                    img.setY(r * TILE_HEIGHT);
                    screen.add(img);
                }
            }
        }
        return screen;
    }

}
