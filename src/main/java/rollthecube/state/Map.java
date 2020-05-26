package rollthecube.state;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j

public class Map implements Cloneable{

    public static int[][] map = {
            {0,0,0,0,1,0,0 },
            {1,0,0,0,0,0,1 },
            {2,1,0,1,0,0,0 },
            {0,0,0,0,1,0,0 },
            {0,1,0,0,0,0,0 },
            {0,0,1,0,1,3,1 },
            {0,0,0,0,0,0,0 }

    };
    boolean win = false;
    boolean six = false;

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
        currX=5;
        currY=5;
        koldal=1;
        return map;
    }
    public static int koldal = 1;
    public static int currX = 5;

    public static int getCurrX() {
        return currX;
    }

    public static int currY=5;

    public static int getCurrY() {
        return currY;
    }

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
                mOldal(koldal,(currX-x),(currY-y));
                map[currY][currX] = 0;
                map[y][x] = 3;
                currX = x;
                currY = y;
                isWin();

            }
        }
    }

    public boolean isWin() {
        if (currX==0&&currY==2 && koldal !=6){
            win=true;
        }

        return win;
    }
    public boolean isSix() {
        if (koldal == 6){
            six=true;
        }

        return six;
    }


    public void mOldal(int lap, int x, int y){
        if (lap == 1){ // 1 > 2,5,3,4
            if (x==1 && y==0){ // Kocka fordul balra;
                koldal = 4;
            }else if (x==-1 && y ==0){//jobbra
                koldal = 3;
            }else if (x==0 && y==1){ //  fel
                koldal = 2;
            }else{ // le
                koldal = 5;
            }
        }else if (lap == 2){ // 2> 1,3,4,6
            if (x==1 && y==0){ // Kocka fordul balra;
                koldal = 4;
            }else if (x==-1 && y ==0){//jobbra
                koldal = 3;
            }else if (x==0 && y==1){ //  fel
                koldal = 6;
            }else{ // le
                koldal = 1;
            }
        }else if (lap == 3){ // 3>1,2,5,6
            if (x==1 && y==0){ // Kocka fordul balra;
                koldal = 5;
            }else if (x==-1 && y ==0){//jobbra
                koldal = 2;
            }else if (x==0 && y==1){ //  fel
                koldal = 1;
            }else{ // le
                koldal = 6;
            }
        }else if (lap == 4){ // 4 > 1,2,5,6
            if (x==1 && y==0){ // Kocka fordul balra;
                koldal = 2;
            }else if (x==-1 && y ==0){//jobbra
                koldal = 5;
            }else if (x==0 && y==1){ //  fel
                koldal = 1;
            }else{ // le
                koldal = 6;
            }
        }else if (lap == 5){ // 5 >1,3,4,6
            if (x==1 && y==0){ // Kocka fordul balra;
                koldal = 4;
            }else if (x==-1 && y ==0){//jobbra
                koldal = 3;
            }else if (x==0 && y==1){ //  fel
                koldal = 1;
            }else{ // le
                koldal = 6;
            }
        }

        log.info("Oldal: {}",koldal);
    }

}
