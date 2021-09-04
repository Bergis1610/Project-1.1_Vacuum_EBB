import java.util.Random;

public class ProtoV2 {

    /*
     *
     * Make the grid 4x4
     * Make the isLegal method for moves
     * Make the robot to move
     *
     */

    static String d = "Dirty";
    static String c = "Clean";


    Random rand = new Random();
    static int temp;

    public String dirtyfier(){
        temp = rand.nextInt(2);
        if(temp == 1)
            return d;
        return c;
    }
    public void vacuumRandomizer(Vacuumer vac){
        vac.indexLocX = rand.nextInt(4);
        vac.indexLocY = rand.nextInt(4);
    }



    public void printArray(String[][] m){
        for(int i = 0;i<4;i++){
            println("");
            for(int j = 0; j<4;j++){
                print(m[i][j] + "\t");
            }
        }
    }
    public void printArrayV2(String[][] m, int x, int y){
        for(int i = 0;i<4;i++){
            println("");
            for(int j = 0; j<4;j++){
                if(i == x && j == y )
                    print("[" + m[i][j] + "]\t");
                else
                    print(m[i][j] + "\t");
            }
        }
        println("");
    }
    public void makeDirty(String[][] a){
        for(int i = 0;i<4;i++){
            for(int j = 0; j<4;j++){
                a[i][j] = dirtyfier();
            }
        }
    }

    public void printArrayEz(String[][]m){
        for(int i = 0;i<2;i++){
            println("");
            for(int j = 0; j<2;j++){
                print(m[i][j] + "\t");
            }
        }
    }
    public void makeDirtyEz(String[][] a){
        for(int i = 0;i<2;i++){
            for(int j = 0; j<2;j++){
                a[i][j] = dirtyfier();
            }
        }
    }
    public void printEzV2(String[][] m, int x, int y){
        for(int i = 0;i<2;i++){
            println("");
            for(int j = 0; j<2;j++){
                if(i == x && j == y )
                    print("[" + m[i][j] + "]\t");
                else
                    print(m[i][j] + "\t");
            }
        }
        println("");
    }

    public static class Vacuumer{
        int indexLocX = 2;
        int indexLocY = 1;
        int oppositeMove = 0;
    }

    int limitX = 4;
    int limitY = 4;

    public boolean isLegal(int x, int y){
        if(x < 0 || x >= limitX || y < 0 || y >= limitY)
            return false;
        return true;
    }

    boolean done = false;

    public boolean move(Vacuumer v){
        done = false;
        temp = rand.nextInt(4);
        while(!done) {
            if (temp == 0) {
                //down x++
                if(!isLegal(v.indexLocX+1,v.indexLocY) || temp == v.oppositeMove){
                    temp = 2;
                } else {
                    v.indexLocX++;
                    v.oppositeMove = 1;
                    done = true;
                }

            } else if (temp == 1) {
                //up x--
                if(!isLegal(v.indexLocX-1,v.indexLocY) || temp == v.oppositeMove){
                    temp = 3;
                } else {
                    v.indexLocX--;
                    v.oppositeMove = 0;
                    done = true;
                }

            } else if (temp == 2) {
                //right y++
                if(!isLegal(v.indexLocX,v.indexLocY+1) || temp == v.oppositeMove){
                    temp = 1;
                } else {
                    v.indexLocY++;
                    v.oppositeMove = 3;
                    done = true;
                }

            } else if (temp == 3) {
                //left y--
                if(!isLegal(v.indexLocX,v.indexLocY-1)|| temp == v.oppositeMove){
                    temp = 0;
                } else {
                    v.indexLocY--;
                    v.oppositeMove = 2;
                    done = true;
                }
            }
        }//while loop
        return true;
    }

    public boolean simpleMove(Vacuumer v){
        done = false;

        temp = 0;

        while(!done) {
            if (temp == 0) {
                //down x++
                if (!isLegal(v.indexLocX + 1, v.indexLocY) || temp != v.oppositeMove) {
                    temp = 1;
                } else {
                    v.indexLocX++;
                    v.oppositeMove = temp;
                    done = true;
                }
            }
        }
        return true;
    }
    boolean dirtCheck;
    public void vacAction(String[][] tester, Vacuumer vac){
        println("Current location = (" +vac.indexLocX + "," + vac.indexLocY + ")");

        if(tester[vac.indexLocX][vac.indexLocY].equals(d)){
            dirtCheck = true;
        }else{
            dirtCheck = false;
        }
        println("Perception: It is " + dirtCheck + " that the square is dirty");
        if(dirtCheck) {
            println("Action: Clean and then move.");
            tester[vac.indexLocX][vac.indexLocY] = c;
        } else {
            println("Action: Move only.");
        }
        move(vac);
    }


    public static void main(String[]args){
        ProtoV2 p = new ProtoV2();
        Random rand = new Random();

        //2x2 testing matrix
        /*
        String[][] tester = new String[2][2];
        Vacuumer vac = new Vacuumer();
        p.makeDirtyEz(tester);
        p.printEzV2(tester,vac.indexLocX, vac.indexLocY);

        //p.println(vac.indexLocX + "," + vac.indexLocY);
        for(int i = 0; i< 10; i++) {
            if (tester[vac.indexLocX][vac.indexLocY].equals(d)) {
                tester[vac.indexLocX][vac.indexLocY] = c;
            }
            p.printEzV2(tester, vac.indexLocX, vac.indexLocY);
            p.move(vac);
            p.printEzV2(tester, vac.indexLocX, vac.indexLocY);
        }
        //p.print(vac.indexLocX + "," + vac.indexLocY);

         */

        // 4x4 Matrix
        String[][] tester = new String[4][4];
        Vacuumer vac = new Vacuumer();
        p.makeDirty(tester);
        p.vacuumRandomizer(vac);
        p.printArrayV2(tester,vac.indexLocX, vac.indexLocY);

        //p.println(vac.indexLocX + "," + vac.indexLocY);
        for(int i = 0; i < 10; i++) {
            p.println("");
            p.print("----------------------------------------------------------------------");
            if (tester[vac.indexLocX][vac.indexLocY].equals(d)) {
                tester[vac.indexLocX][vac.indexLocY] = c;
                p.println("\nCleaned square");
            } else {
                p.println("\nSquare was already clean");
            }
            p.move(vac);
            p.printArrayV2(tester, vac.indexLocX, vac.indexLocY);
            p.println("----------------------------------------------------------------------");
        }
/*
        String[][] a = new String[4][4];


        p.makeDirty(a);
        p.printArrayV2(a, 2, 3);
 */

        //Matrix test - Successful
        /*
        int[][] matrix = new int[4][4];

        //double for-loop tester

        for(int i = 0;i<4;i++){
            p.println("");
            for(int j = 0; j<4;j++){
               p.print(matrix[i][j] + "\t");
            }
        }

         */

        //Dirtyfier for-loop
        /*
        for(int i = 0;i<4;i++){
            for(int j = 0; j<4;j++){
                a[i][j] = p.dirtyfier();
            }
        }

         */

        //dirtyfier tests
        /*
        for(int i = 0;i<10;i++){
            p.println(p.dirtyfier());
        }

         */
        /*
        for(int i = 0;i<10;i++){
            p.prln(p.rand.nextInt(2));
        }
        */

        //random tester
        /*
        for(int i = 0; i < 8; i++)
            p.println(rand.nextInt(4));
        */
    }

    public void print(Object o){
        System.out.print(o);
    }

    public void println(Object o){
        System.out.println(o);
    }

    //Legal move
    /*
        if(x < 0){
            println("Cannot move upwards!");
            return false;
        }
        if(x >= limitX){
            println("Cannot move downwards!");
            return false;
        }
        if(y < 0){
            println("Cannot move leftwards!");
            return false;
        }
        if(y >= limitY){
            println("Cannot move rightwards!");
            return false;
        }
        println("Legal move");
        */




}
