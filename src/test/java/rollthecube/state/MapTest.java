package rollthecube.state;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {


    @Test
    void isFree() {
        Map m = new Map();
        m.getCurrX();
        m.getCurrY();
        //assertFalse(m.isFree(0, 0));


    }
    @Test
    void resetMap(){

        int[][] map =  new int[][]{
                {0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 1},
                {2, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 3, 1},
                {0, 0, 0, 0, 0, 0, 0}

        };

    }
    Map m = new Map();
    private void assertWin(int x, int y,boolean go,boolean win) {
        assertAll("IsWin",
                () -> assertEquals(x, m.getCurrX(), "X"),
                () -> assertEquals(y, m.getCurrY(), "Y"),
                () -> assertEquals(go, m.getGameOver(), "Game Over"),
                () -> assertEquals(win,m.getWin(),"Win")
        );
    }

    @Test
    void isWin() {
    assertFalse(new Map().isWin());
    assertWin(5,5,false,false);
    m.setCurrX(0);
    m.setCurrY(2);
    m.setGameover(false);
    m.isWin();
    assertWin(0,2,false,true);

    }



    @Test
    void isGameOver() {
        assertFalse(new Map().isGameOver());

    }
    private void assertGameOver(int x, int y) {
        assertAll("GameOver",
                () -> assertEquals(x, m.getXoldal(), "X"),
                () -> assertEquals(y, m.getYoldal(), "Y")
        );
    }
    @Test
    void gameOver() {
        // Fel-le Mozgás
        assertGameOver(2,2);
        m.gameOver(1,0);
        assertGameOver(2,3);
        m.gameOver(-1,0);
        m.gameOver(-1,0);
        assertGameOver(2,1);
        m.gameOver(1,0);
        assertGameOver(2,2);

        //Jobbra-balra
        m.gameOver(0,1);
        assertGameOver(3,2);
        m.gameOver(0,-1);
        m.gameOver(0,-1);
        assertGameOver(1,2);
        m.gameOver(0,1);
        assertGameOver(2,2);

    }
}
