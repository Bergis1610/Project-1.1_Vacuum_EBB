import java.util.*;


public class ProtoVacuum {

    /*
     *
     * Make the grid 4x4
     * Make the isLegal method for moves
     * Make the robot to move
     *
     */

    //Simply a random function
    Random rand = new Random();
    int temp;

    /*
     * This is the vacuumer class, I designed it so that rather having every single square
     * store a boolean which is either occupied or unoccupied, I store the vacuumers current
     * location in itself. I also store the previous move so that the vacuumer cannot move to
     * the left for example and then move right ending up on the square it was on right before.
     */
    public static class vacuumer{
        int indexLocX = 2;
        int indexLocY = 1;
        int oppositeMove = 0;
    }

    /*
     * This method randomly assigns a boolean value which is either true or false
     */
    public boolean dirtyfier(){
        temp = rand.nextInt(2);
        if(temp == 1)
            return true;
        return false;
    }

    /*
     * This method randomizes the starting position of the vacuumer
     */
    public void vacuumRandomizer(vacuumer v){
        v.indexLocX = rand.nextInt(4);
        v.indexLocY = rand.nextInt(4);
    }

    /*
     * This method takes a boolean matrix as the input and then randomly assigns a value to
     * each index, either true or false using the random function from java.util
     */
    public void gridRandomizer(boolean[][] b){
        int dim1 = b.length;
        int dim2 = b[0].length;

        for(int i = 0;i<dim1;i++){
            for(int j = 0; j<dim2;j++){
                b[i][j] = dirtyfier();
            }
        }
    }

    /*
     * This method is used when printing, so instead of printing the matrix as true/false,
     * this method would print dirty or clean in their respective places
     */
    public void eZprintDirt(Boolean b){
        if(b)
            print("Dirty");
        else
            print("Clean");
    }

    /*
     * This method prints the matrix with each square being either dirty or clean,
     * as well as indicating where the vacuumer currently is using [Square brackets]
     * around the square in which it is located. I chose this approach rather than
     * using DU, DO, CU and CO as D for dirty, C for clean and U and O for un- and occupied.
     * It was a lot more visually pleasing this way.
     */
    public void gridPrinter(boolean[][]m, int x, int y){
        for(int i = 0;i<4;i++){
            println("");
            for(int j = 0; j<4;j++){
                if(i == x && j == y ) {
                    print("[");
                    eZprintDirt(m[i][j]);
                    print("]");
                }else
                    eZprintDirt(m[i][j]);
                print("\t");
            }
        }
        println("");
    }

    //Limits used to define the size of the grid
    int limitX = 4;
    int limitY = 4;

    /*
     * This method checks whether a given move is legal or not. This is to make sure so that the
     * vacuumer suddenly doesn't go out of the grid and to prevent out-of-bounds exceptions.
     */
    public boolean isLegal(int x, int y){
        if(x < 0 || x >= limitX || y < 0 || y >= limitY)
            return false;
        return true;
    }

    /*
     * This method is the percept and act method, it needs a grid and a vacuumer as inputs and
     * then the vacuumer percepts its current location and registers whether it is dirty or clean.
     * Then it either cleans that square if it's dirty, or does nothing if it's clean.
     */
    public void perceptAndAct(boolean[][] b, vacuumer vac){
        print("----------------------------------------------------------------------");
        print("\nCurrent location: ");
        println("(" + vac.indexLocX + "," + vac.indexLocY + ")");
        print("Percept: ");
        if (b[vac.indexLocX][vac.indexLocY]) {
            println("The square is dirty");
            println("Action: Clean then move");

            gridPrinter(b, vac.indexLocX, vac.indexLocY);
            println("----------------------------------------------------------------------");
            b[vac.indexLocX][vac.indexLocY] = false;
            //println("\n\t** Cleaned square **");
        } else {
            println("The square is clean");
            println("Action: Move");
            //print("----------------------------------------------------------------------");
            gridPrinter(b, vac.indexLocX, vac.indexLocY);
            println("----------------------------------------------------------------------");
            //println("\n\t** Square was already clean **");
        }
        //move(vac);
        /*
        print("Current location: ");
        println("(" + vac.indexLocX + "," + vac.indexLocY + ")")
        print("Percept: ");
        println("The square is dirty");
        println("The square is clean");
        println("Action: Clean then move");
        println("Action: Move");
         */
    }

    //boolean used for the while-loop
    boolean done;
    /*
     * This method is the "Artificial intelligence" of the vacuumer
     */
    public boolean move(vacuumer v){
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

    // These two methods were simply created so that I could print stuff and type less code
    // Simply lazy methods.
    public void print(Object o){
        System.out.print(o);
    }
    public void println(Object o){
        System.out.println(o);
    }


    /*
     * This is the main method.
     */
    public static void main(String[]args){
        //Before I can use any of the defined methods I need to construct a ProtoVacuum object
        ProtoVacuum p = new ProtoVacuum();

        //The grid/matrix is simply a boolean matrix with true being dirty and false being clean
        boolean[][] table = new boolean[4][4];
        //There also must be a vacuumer object so that the dirty squares can be cleaned
        vacuumer v = new vacuumer();

        // This calls upon the gridRandomizer() randomizes all values within the boolean matrix
        // to either be dirty or clean
        /*
        for(int i = 0;i<4;i++){
            for(int j = 0; j<4;j++){
                table[i][j] = p.dirtyfier();
            }
        }

         */
        p.gridRandomizer(table);

        // This method calls upon the vacuumRandomizer to randomly assign a location to the vacuumer
        p.vacuumRandomizer(v);

        // This method calls upon the gridPrinter method to print out, in a neat way, the grid's
        // state and the current location of the vacuumer
        //              **  p.gridPrinter(table,v.indexLocX,v.indexLocY);  **

        // This is a for-loop that iterates through i-times, for every iteration the vacuumer
        // percepts the square in which it is, and then it either cleans or does nothing.
        // After that it moves randomly, up, down, left or right and once it has chosen a
        // direction, it checks whether it is a legal move and it makes sure it doesn't go
        // back to the square it was just in. Then it moves to that next square and the
        // grid is printed again.
        for(int i = 0; i < 25; i++) {
            p.perceptAndAct(table, v);
            /*
            p.print("----------------------------------------------------------------------");
            p.gridPrinter(table, v.indexLocX, v.indexLocY);
            p.println("----------------------------------------------------------------------");

             */
            //p.println("");
            //p.print("----------------------------------------------------------------------");
            /*
            if (table[v.indexLocX][v.indexLocY]) {
                table[v.indexLocX][v.indexLocY] = false;
                p.println("\n\t** Cleaned square **");
            } else {
                p.println("\n\t** Square was already clean **");
            }
             */
            p.move(v);
        }

    }

    //Below is a prot-class for a square, however I discarded this idea in favour
    //of another implementation that was more efficient with less code.
    /*public static class square{
        boolean isDirty = true;
        boolean occupied = false;

    }
     */

    //The methods below were used for testing purposes and are not a part of the program
    /*
    public void printArray(square[][] m){
        for(int i = 0;i<4;i++){
            println("");
            for(int j = 0; j<4;j++){
                print(m[i][j].isDirty +"\t");
            }
        }
    }
    public void gridPrinter(square[][]m, int x, int y){
        for(int i = 0;i<4;i++){
            println("");
            for(int j = 0; j<4;j++){
                if(i == x && j == y ) {
                    print("[");
                    eZprintDirt(m[i][j].isDirty);
                    print("]");
                }else
                    eZprintDirt(m[i][j].isDirty);
                print("\t");
            }
        }
    }
    public boolean move(vacuumer v, square[][] board){
        done = false;
        temp = rand.nextInt(4);
        board[v.indexLocX][v.indexLocY].occupied = false;
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
        board[v.indexLocX][v.indexLocY].occupied = true;
        return true;
    }
    */

    //Square matrix used for testing purposes.
    //  *   Supposed to be inside the main method  *  //
    /*
        square[][] board = new square[4][4];
        square s1 = new square();
        board[0][0] = s1;
        p.print(board[0][0].isDirty);

        for(int i = 0;i<4;i++){
            for(int j = 0; j<4;j++){
                square temp = new square();
                board[i][j] = temp;
            }
        }
        p.gridPrinter(board,2,1);
        p.println("\n\n");
        for(int i = 0;i<4;i++){
            for(int j = 0; j<4;j++){
                board[i][j].isDirty = p.dirtyfier();
            }
        }
        p.gridPrinter(board,2,1);
        p.println("\n\n");
            */

    //dirtyfier tests
    //  *   Supposed to be inside the main method  *  //
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






}

