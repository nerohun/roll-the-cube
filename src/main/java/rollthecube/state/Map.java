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

    public static int Xoldal = 2;
    public static int Yoldal = 2;
    public static int currX = 5;
    public static int currY=5;
    boolean win = false;
    boolean gameover = false;
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
     * set lepes for count the player moves.
     * @return new array, representig the start position
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
     * Represent the player start position
     */

    public static void resetPlayer(){

        currX=5;
        currY=5;
        Xoldal=2;
        Yoldal=2;
    }

    /**
     * When the player select a new cube, the method examines the next cube, if it Free the player can move there.
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
            System.out.println("Ide Nem Lehet lépni!");
            log.info("Cube ({}, {}) is pressed", x, y);
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
     * @return the player won the game
     */
    public boolean isWin() {
        if (currX==0&&currY==2 && !gameover){
            win=true;
        }

        return win;
    }

    /**
     *The game is over.
     *
     * @return true when players game is game over
     */
    public boolean isGameOver() {

        return gameover;
    }

    /**
     *Roll the cube the right side.
     * This class represent the cube side, if the red side of the cube touch the map, the game is over.
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

    public int getCurrX() {
        return currX;
    }
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
