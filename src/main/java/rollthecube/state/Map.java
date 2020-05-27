package rollthecube.state;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j

/**
 * Class representing the map and the cube's movement.
 */


public class Map implements Cloneable{
    private IntegerProperty steps = new SimpleIntegerProperty();
    /**
     * Specifies the player cubes X side
     */
    public static int Xoldal = 2;
    /**
     * Specifies the player cubes Y side
     */
    public static int Yoldal = 2;
    /**
     *Specifies the player start X position
     */
    public static int currX = 5;
    /**
     *Specifies the player start Y position
     */
    public static int currY=5;
    /**
     * Specifies the game state.
     * If the {@code win} true, the player win the game
     */
    boolean win = false;
    /**
     * Specifies the game state.
     * If the {@code gameover} true, the player lost the game
     */
    boolean gameover = false;
    /**
     * Specifies of the players number of steps
     */
    public static int lepes = 0;
    /**
     * Initalize the map array, this is the playground.
     */

    public static int[][] map = {
            {0,0,0,0,1,0,0 },
            {1,0,0,0,0,0,1 },
            {2,1,0,1,0,0,0 },
            {0,0,0,0,1,0,0 },
            {0,1,0,0,0,0,0 },
            {0,0,1,0,1,3,1 },
            {0,0,0,0,0,0,0 }

    };

    /**
     *Reset the map if the player press the reset button
     *  {@code lepes}  count the players orderly moves.
     * set player position to the start.
     * @return new array, representing the start position
     */
    public static int[][] resetMap(){
        map = new int[][]{
                {0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 1},
                {2, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 3, 1},
                {0, 0, 0, 0, 0, 0, 0}

        };
        resetPlayer();
        lepes = 0;
        return map;
    }

    /**
     * Reset the player start position in the map and the cube state
     * currX and currY the player start position on the map
     * Xoldal and Yoldal for the player cubes orientation
     */

    public static void resetPlayer(){

        currX=5;
        currY=5;
        Xoldal=2;
        Yoldal=2;
    }

    /**
     * When the player select a new cube the method examines the cube, if it Free the player can move there.
     *Firstly, examinate the next position. If it occupied, the player cant move there.
     * After that, you cannot cross multiple fields, and cannot move your current posititon
     *
     *
     * @param x : player next X coordinates in the map
     * @param y : player next Y coordinates in the map
     */
    public void isFree(int x, int y) {
        int curry = currY;
        int currx = currX;
        int nexty=y;
        int nextx=x;
        if (map[y][x] == 1) {
            log.info("Ide nem lehet lépni!");
        } else {
            if(curry-nexty >1 || curry-nexty <-1 || currx-nextx >1 || currx-nextx<-1 || (curry+currx)-(nexty+nextx)>1 || (curry+currx)-(nexty+nextx)<-1 || curry+currx == nexty+nextx) {
                log.info("Csak a szemközti 4 mezőre léphetsz!");
            }else{
                gameOver((currX-x),(currY-y));
                map[currY][currX] = 0;
                map[y][x] = 3;
                currX = x;
                currY = y;
                isWin();
                lepes++;

            }
        }
    }

    /**
     *Examines the player position and the gameover value.
     * If the player in the Finish position and the gameover not true, the player win the game.
     * @return true when the player won, false anyway
     */
    public boolean isWin() {
        if (currX==0&&currY==2 && !gameover){
            win=true;
        }

        return win;
    }

    /**
     *Represent the game status
     *
     *  when the {@code gameover} is true, the game is over
     * @return true when players game is over, false anyway
     */
    public boolean isGameOver() {

        return gameover;
    }

    /**
     *Roll the players cube of the right side.
     * This class represent the cube side, if the "red" side of the cube touch the map, the game is over.
     * @param x : Player movement of the X axis
     * @param y : Player movement of the Y axis
     */

    public void gameOver(int x, int y){


            if (x==1 && y==0){ // Kocka fordul balra;
                if (Xoldal==1 || Xoldal == 3){

                }else {
                    Yoldal = Yoldal + x;

                }
            }else if (x==-1 && y ==0){//jobbra
                if (Xoldal==1 || Xoldal == 3){

                }else {
                    Yoldal = Yoldal + x;

                }
            }else if (x==0 && y==1){ //  fel
                if (Yoldal==1 || Yoldal == 3){

                }else {
                    Xoldal = Xoldal + y;
                }
            }else{ // le
                if (Yoldal==1 || Yoldal == 3){

                }else {
                    Xoldal = Xoldal + y;
                }
            }
            if (Yoldal ==0 || Yoldal == 4 || Xoldal == 0 || Xoldal == 4){
                gameover = true;
            }

        log.debug("Oldaly: {},oldalx: {}",Xoldal, Yoldal);
    }

    public static int getCurrX() { return currX; }

    public int getCurrY() {
        return currY;
    }

    public void setCurrX(int i) {
        currX=i;
    }
    public void setCurrY(int i){
        currY = i;
    }
    public void setGameover(boolean i ){
        gameover = i;
    }

    public int getXoldal() {
        return Xoldal;
    }
    public int getYoldal(){
        return Yoldal;
    }

    public boolean getWin() {
        return win;
    }

    public boolean getGameOver() {
        return gameover;
    }

    public int[][] getMap() {
        return map;
    }
}
