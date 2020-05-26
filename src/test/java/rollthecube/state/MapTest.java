package rollthecube.state;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    Map m = new Map();
    private void assertisFree(int x, int y) {
        assertAll("isFree",
                () -> assertEquals(x, m.getCurrX(), "X"),
                () -> assertEquals(y, m.getCurrY(), "Y")
        );
    }

    @Test
    void isFree() {

        assertisFree(5,5);
        m.isFree(6,5);// Itt egy szürke kocka van.
        assertisFree(5,5);
        m.isFree(4,5);//Itt egy szürke kocka van.
        assertisFree(5,5);
        m.isFree(5,6);//Ez egy fehér mező, szabadon rá lehet lépni.
        assertisFree(5,6);
        m.isFree(1,1);//Ide nem lehet lépni!!
        assertisFree(5,6);



    }

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
    m.setCurrX(5);
    m.setCurrY(5);

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
