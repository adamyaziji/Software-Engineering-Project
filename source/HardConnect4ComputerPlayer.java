/**
* @file HardConnect4ComputerPlayer.java
* @author Kristoffer Page, Adam Yaziji
* @date 15 Mar 2014
* @see JavaDoc for Random and ATW colour
*      http://docs.oracle.com/javase/6/docs/api/java/util/Random.html
*      http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
* 
* @brief This class calculates the highest scoring moves in the connect four 
*        board.
*
* This class calculates the highest scoring move (x value) by calculating 
* potential chain lengths for each x value
*/

import java.awt.Color;
import java.util.Random;

public class HardConnect4ComputerPlayer extends ComputerPlayer {
    /**< Random number generator */
    Random rnd;

    /**
    * @param name   - name of player
    * @param score  - score of player
    * @param colour - colour of player
    */
    public HardConnect4ComputerPlayer(String name, int score, Color colour) {
        super(name, score, colour);
    }
    
    /**
    *   Returns random move
    *
    *   @param BOARD_WIDTH - the width of the board used
    *   @return int - returns a random number between 0 and BOARD_WIDTH
    */
    @Override
    public int getMoveX(final int BOARD_WIDTH) {
        rnd = new Random();
        return rnd.nextInt(BOARD_WIDTH);
    }

    /**
    *   Calculates the best move
    *
    *   @param board - the Connect Four game board, 
    *                  used for calculating scores of moves
    *   @return int - x value of highest scoring move
    */

    public int getMoveX(final GamePiece[][] board) {
        GamePiece[][] b = flipBoard(board);
        int yPositions[] = getYPositions(b);
        int moves[] = new int[b.length];

        for (int x = 0; x < yPositions.length; x++) {
            moves[x] = getMoveScore(b, x, yPositions[x]);
            if (yPositions[x] >= b[0].length - 1) {
                moves[x] = 0;
            }
        }

        return max(moves);
    }

    /**
    *   "Flips"  the board mapping position (0,0) to [0][0] in the array
    *
    *   @param b - the board to be flipped
    *
    *   @return the flipped board
    */
    private static GamePiece[][] flipBoard(GamePiece[][] b) {
        final int HEIGHT = b.length;
        final int WIDTH = b[0].length;
        GamePiece[][] nb = new GamePiece[WIDTH][HEIGHT];

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                nb[x][y] = b[b.length - 1 - y][x];
            }
        }
        return nb;
    }

    /**
    *   Gets the  best score at a specific x,y location, by looking in each 
    *   possible direction
    *
    *   @param board - the Connect Four board
    *   @param x - x coordinate
    *   @param y - y coordinate
    *   
    *   @return the highest score possible by placing the piece at x 
    */
    private int getMoveScore(GamePiece[][] board, int x, int y) {
        int scoreVert = 0;
        int scoreHorz = 0;
        int scoreLDiag = 0;
        int scoreRDiag = 0;

        //Check downwards
        scoreVert   = getScore(board, x, y,  0, -1, getColor());

        //Check horizontal left
        scoreHorz   = getScore(board, x, y, -1,  0, getColor());
        //Check horizontal right
        scoreHorz  += getScore(board, x, y,  1,  0, getColor());

        //Check left diagonal up
        scoreLDiag  = getScore(board, x, y, -1,  1, getColor());
        //Check left diagonal down
        scoreLDiag -= getScore(board, x, y,  1, -1, getColor());

        //Check right diagonal up
        scoreRDiag  = getScore(board, x, y,  1,  1, getColor());
        //Check right diagonal down
        scoreRDiag -= getScore(board, x, y, -1, -1, getColor());
        
        final int MOVES = 4;
        int scores[] = new int [MOVES];
        scores[0] = scoreVert;
        scores[1] = scoreHorz;
        scores[2] = scoreLDiag;
        scores[3] = scoreRDiag;
        int max = max(scores);
        return max;
    }

    /**
    *   Calculates the score in a specific direction from a specific location
    *   @param board - the Connect Four board
    *   @param x - the x coordinate
    *   @param y - the y coordinate
    *   @param dx - the direction in x
    *   @param dy - the direction in y
    *   @param color - the color of the current players piece
    *
    *   @return the score in the direction
    */
    private int getScore(GamePiece[][] board, int x, int y, int dx, int dy, 
        Color color) {
        int score = 0;
        boolean edge = false;

        do {
            x += dx;
            y += dy;
            if (x < 0 || x > board.length - 1 || y < 0 || y >= board[0].length 
                    - 1) {
                edge = true;
                break;

            }
            if (board[x][y] == null) {
                edge = true;
                break;
            } else {
                if (board[x][y].getPlayer().getColor() == getColor()) {
                    edge = false;
                    score++;
                } else {
                    break;
                }
            }

        } while (!edge);

        return score;
    }

    /**
    *   Returns the lowest empty y positions for each x in the board
    *
    *   @param board - the (flipped) Connect Four board
    *
    *   @return array of y positions, returns -1 if column is full
    */
    private int[] getYPositions(final GamePiece[][] board) {
        int yPositions[] = new int[board.length];

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == null) {
                    yPositions[x] = y; 
                    //Max found, Exit loop
                    y = board[0].length;
                }
            }
            if (yPositions[x] == 0 && board[x][0] != null) {
                yPositions[x] = -1;                
            }
        }
        return yPositions;
    }

    /**
    *   Returns index of the array with the largest number
    *
    *   @param num - array of numbers to test
    *
    *   @return index of highest number
    */

    private static int max(int num[]) {
        int index = 0;
        for (int i = 0; i < num.length; i++) {
            if (num[i] > num[0]) {
                num[0] = num[i];
                index = i;
            }
        }

        return index;
    }

    
    //Unused inherited method
    @Override
    public boolean placePiece(int x, int y) {
        return false;
    }

    public static void main(String[] argv) {
        GamePiece[][] b = new GamePiece[7][10];

        int testArray[] = new int[] {1,2,6,9,55,5};
        int maxIndex = max(testArray);
        int max = testArray[maxIndex];

        System.out.println("HardConnect4ComputerPlayer test");
        System.out.println("-------------------------------------------");
        System.out.println("-------------------------------------------\n");

        System.out.println("max()");
        System.out.println("-------------------------------------------");
        System.out.println("List: {1,2,6,9,55,5} \nExpected result 55"
            + "\nActual Result: " + max + " Index: " + maxIndex);

        //Create two players and make dummy board
        Player p1 = new HumanPlayer("Test1", 0, Color.YELLOW);
        Player p2 = new HumanPlayer("Test2", 0, Color.RED);
        b[6][0] = new GamePiece(p1);
        b[5][0] = new GamePiece(p1);

        b[6][1] = new GamePiece(p1); //b[1][0].setPlayer(p1);
        b[5][1] = new GamePiece(p1); //b[1][1].setPlayer(p1);
        b[4][1] = new GamePiece(p1); //b[1][2].setPlayer(p1);

        b[6][2] = new GamePiece(p1);//b[2][0].setPlayer(p1);
        b[5][2] = new GamePiece(p1);//b[2][1].setPlayer(p1);
        b[4][2] = new GamePiece(p1);//b[2][2].setPlayer(p1);
        b[3][1] = new GamePiece(p2);//b[2][3].setPlayer(p2);

        b[6][4] = new GamePiece(p1);//b[2][0].setPlayer(p1);
        b[5][4] = new GamePiece(p1);//b[2][1].setPlayer(p1);
        b[4][4] = new GamePiece(p2);//b[2][2].setPlayer(p1);
        b[3][4] = new GamePiece(p1);//b[2][3].setPlayer(p2);

        b[6][5] = new GamePiece(p1);//b[2][0].setPlayer(p1);
        b[5][5] = new GamePiece(p1);//b[2][1].setPlayer(p1);
        b[4][5] = new GamePiece(p2);//b[2][2].setPlayer(p1);
        b[3][5] = new GamePiece(p1);//b[2][3].setPlayer(p2);
        b[2][5] = new GamePiece(p2);//b[2][3].setPlayer(p2);
        b[1][5] = new GamePiece(p1);//b[2][3].setPlayer(p2);

        b[6][6] = new GamePiece(p1);//b[2][0].setPlayer(p1);
        b[5][6] = new GamePiece(p1);//b[2][1].setPlayer(p1);
        b[4][6] = new GamePiece(p2);//b[2][2].setPlayer(p1);
        b[3][6] = new GamePiece(p2);//b[2][3].setPlayer(p2);
        b[2][6] = new GamePiece(p1);//b[2][3].setPlayer(p2);
        b[1][6] = new GamePiece(p1);//b[2][3].setPlayer(p2);
        b[0][6] = new GamePiece(p1);//b[2][3].setPlayer(p2);

        HardConnect4ComputerPlayer cp = new HardConnect4ComputerPlayer("Hard", 
            0, Color.YELLOW);

        System.out.println("--- Original board ---");
        System.out.println("-------------------------------------------");
        System.out.println("      Col 0 Col 1 Col 2 Col 3 Col 4 Col 5 Col 6\n");
        for (int y = 0; y < b[0].length; y++) {
            System.out.print("Row " + y + "  ");
            for (int x = 0; x < b.length; x++) {
                if (b[x][y] != null) {
                    System.out.print(b[x][y].getPlayer().getName() + " ");
                } else {
                    System.out.print("  --- ");
                }
            }
            System.out.println("");
        }

        b = flipBoard(b);
        System.out.println("\n\n");
        System.out.println("--- Flipped board ---");
        System.out.println("-------------------------------------------");
        for (int y = b[0].length - 1; y >= 0; y--) {
            System.out.print("Row " + y + "  ");
            for (int x = 0; x < b.length; x++) {
                if (b[x][y] != null) {
                    System.out.print(b[x][y].getPlayer().getName() + " ");
                } else {
                    System.out.print("  --- ");
                }
            }
            System.out.println("");
        }
        
        System.out.println("\n       Col 0 Col 1 Col 2 Col 3 Col 4 Col 5 Col 6 "
            + "Col 7 Col 8 Col 9\n");

        System.out.println("getYPositions() -- Begin");
        System.out.println("-------------------------------------------");
        int yPositions[] = cp.getYPositions(b);
        System.out.println("Expected output - 2, 4, 3, 0, 4, 6, -1, 0, 0, 0,");
        System.out.print("Actual output   - ");
        for (int i = 0; i < yPositions.length; i++) {
            System.out.print(yPositions[i] + ", ");
        }
        System.out.println("\ngetYpositions() -- End");

        int moves[] = new int[b.length];

        System.out.println("\n\ngetScore() - Vertical -- Begin");
        System.out.println("-------------------------------------------");
        System.out.println("Vertical Scores");
        for (int i = 0; i < yPositions.length; i++) {
            System.out.println("Score x:" + i + " y:" + yPositions[i] + "   " 
                + cp.getScore(b, i, yPositions[i], 0, -1, p1.getColor()));
        }
        System.out.println("\ngetScore() - Vertical -- End");

        System.out.println("\n\ngetScore() - Horizontal -- Begin");
        System.out.println("-------------------------------------------");
        System.out.println("Horizontal Scores");
        for (int i = 0; i < yPositions.length; i++) {
            int horizontal = cp.getScore(b, i, yPositions[i], -1, 0, 
                p1.getColor());
            if (i == 5) {System.out.println(cp.getScore(b, i, yPositions[i], 
                -1, 0, p1.getColor())+ "  " +cp.getScore(b, i, yPositions[i], 
                1, 0, p1.getColor()));}
            horizontal += cp.getScore(b, i, yPositions[i], 1, 0, p1.getColor());

            System.out.println("Score x:" + i + " y:" + yPositions[i] + "   " 
                + horizontal);
        }
        System.out.println("\ngetScore() - Horizontal -- End");

        System.out.println("\n\ngetScore() - Left Diagonal -- Begin");
        System.out.println("-------------------------------------------");
        System.out.println("\nDiag Left Scores");
        for (int i = 0; i < yPositions.length; i++) {
            int diagL = cp.getScore(b, i, yPositions[i], -1, 1, p1.getColor());
            diagL += cp.getScore(b, i, yPositions[i], 1, -1, p1.getColor());
            System.out.println("Score x:" + i + " y:" + yPositions[i] + "   " 
                + diagL);
        }
        System.out.println("\ngetScore() - Left Diagonal -- End");

        System.out.println("\n\ngetScore() - Right Diagonal -- Begin");
        System.out.println("-------------------------------------------");
        System.out.println("\nDiag Right Scores");
        for (int i = 0; i < yPositions.length; i++) {
            int diagR = cp.getScore(b, i, yPositions[i], 1, 1, p1.getColor());
            diagR += cp.getScore(b, i, yPositions[i], -1, -1, p1.getColor());
            System.out.println("Score x:" + i + " y:" + yPositions[i] + "   " 
                + diagR);
        }
        System.out.println("\ngetScore() - Right Diagonal -- End");
    }
}