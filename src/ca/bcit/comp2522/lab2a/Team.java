package ca.bcit.comp2522.lab2a;

public enum Team {
    WHITE(0, 'w'),
    BLACK(1, 'b');
    
    public static final int NUMBER_OF_TEAMS = Team.values().length;
    private int teamNum;
    private char teamChar;
    
    Team(int teamNum, char teamChar){
        this.teamNum = teamNum;
        this.teamChar = teamChar;
    }
    
    public int getNum() {
        return teamNum;
    }
    
    public char getChar() {
        return teamChar;
    }
    
    public static int nextTurn(int t) {
        if(++t >= NUMBER_OF_TEAMS) {
            t = 0;
        }
        return t;
    }
}
