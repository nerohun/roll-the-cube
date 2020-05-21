package rollthecube.state;

public class Map {
    static int[][] map = {
            {0,0,0,0,1,0,0 },
            {1,0,0,0,0,0,1 },
            {2,1,0,1,0,0,0 },
            {0,0,0,0,1,0,0 },
            {0,1,0,0,0,0,0 },
            {0,0,1,0,1,3,1 },
            {0,0,0,0,0,0,0 }

    };

    public static int[][] getMap() {
        return map;
    }

    public static int[][] changeMap(int x, int y){


    return map;
    }
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
            //log.debug("Cube ({}, {}) is pressed", row, col);
        } else {
            if(curry-nexty >1 || curry-nexty <-1 || currx-nextx >1 || currx-nextx<-1 || (curry+currx)-(nexty+nextx)>1 || (curry+currx)-(nexty+nextx)<-1 || curry+currx == nexty+nextx) {
                System.out.println("Sok");
            }else{
                map[currY][currX] = 0;
                map[y][x] = 3;
                currX = x;
                currY = y;
            }
        }
    }


}
