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
    private void assertWin(int x, int y,boolean win) {
        assertAll("IsWin",
                () -> assertEquals(x, m.getCurrX(), "X"),
                () -> assertEquals(y, m.getCurrY(), "Y"),
                () -> assertEquals(win, m.gameover, "Game Over")
        );
    }

    @Test
    void isWin() {
    assertFalse(new Map().isWin());
    assertWin(5,5,false);
    m.setCurrX(0);
    m.setCurrY(2);
    m.setGameover(true);
    assertWin(0,2,true);
    }


    @Test
    void isGameOver() {
        Map m = new Map();
        assertFalse(new Map().isGameOver());

    }

    @Test
    void gameOver() {


    }
}
