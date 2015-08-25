import java.awt.Color;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.Point;

/**
 *  @file HardOthelloComputerPlayer.java
 *  @author Thomas Vass, Adam Yaziji
 *  @date 11 March 2014
 *
 *  @brief Creates a hard computer player
 * 
 * @details This class is used to work out a sensible computer move. 
 * That is one that turns over the most amount of pieces. It does this by 
 * taking the currentstate of the game and trying all possible moves then 
 * calculating the number of pieces that will be flipped.
 *    
 */


public class HardOthelloComputerPlayer extends OthelloComputerPlayer{
    
    
    /** 
    * Constructor for the HardOthelloComputerPlayer
    * @param name - the name of the player
    * @param score - their current score
    * @param color - their colour
    */
    public HardOthelloComputerPlayer(String name, int score, Color color) {
        super(name, score, color);
    }


    /**
    * takes the current state of the game then works out all possible moves
    * @param board - the current game board for which to make a move with.
    * @return Point - a point on the board which the computer has defined
    *                 as the best possible move.
    */
    public Point move (OthelloBoard board){
        //atempt to find all valid moves
        OthelloBoard tempBoard = board;
        ArrayList<Point> listOfLocations= new ArrayList<Point>();
        ArrayList<Integer> numberofPieces = new ArrayList<Integer>();
        GamePiece newPiece;
        newPiece = new GamePiece(board.getPlayer(board.getPlayerTurn()));
        for(int i=0; i < BOARD_HEIGHT; i++){
            for(int j=0; j < BOARD_WIDTH; j++){
                listOfLocations.add(board.getValidMove(i,j,newPiece));
                if(listOfLocations.get(listOfLocations.size()-1)==null){

                }else{
                    numberofPieces.add(board.getPiecesFlipped());
                }
            }
        }
        listOfLocations.removeAll(Collections.singleton(null));
        if(listOfLocations.size()==0){
            return null;
        }else{
            System.out.println(listOfLocations.size());
            System.out.println(numberofPieces.size());

            /*for (int k = 0; k< listOfLocations.size();k++){
                int computerX = listOfLocations.get(k).getX();
                int computerY = listOfLocations.get(k).getY();
                System.out.println("possible valid move at: 
                                (" +computerX+","+computerY+")");
            }*/
            int indexOfLargestFlip=0;
            int tempFlip=0;
            for(int k = 0 ; k<listOfLocations.size();k++){
                int computerX = (int)listOfLocations.get(k).getX();
                int computerY = (int)listOfLocations.get(k).getY();
                System.out.println("possible move at: (" 
                                        + computerX + "," + computerY + ")");
                System.out.println("which flips: "+numberofPieces.get(k));
                if(numberofPieces.get(k)>tempFlip){
                    tempFlip=numberofPieces.get(k);
                    indexOfLargestFlip=k;
                }

            }
            int computerX = (int)listOfLocations.get(indexOfLargestFlip).getX();
            int computerY = (int)listOfLocations.get(indexOfLargestFlip).getY();
            System.out.println("make a move at: (" 
                                        + computerX + "," + computerY + ")");
            return listOfLocations.get(indexOfLargestFlip);

        }
        
    }

    /**
     * @brief main method used for testing
     * @details creates a game consisting of a human and a computer.
     * outputs a command line version of the game where the computer plays 
     * first followed by the human player. this game will then play until
     * completion. However be warned this is meant for testing purposes only!
     * this implementation does not handle invalid moves well, and will cause
     * the players to switch places 
     * @param args - array of inputs from the console when starting the game.
     * This is not used in this implementation.
     */
    public static void main (String args[]){
        t.start();
    }
    static Thread t = new Thread() {
        public void run() {
            HardOthelloComputerPlayer computerPlayer;
            computerPlayer = new HardOthelloComputerPlayer("Computer", 
                                                                2, Color.WHITE);
            HumanPlayer human;
            human = new HumanPlayer("Human",2,Color.BLACK);
            Player temp[] = {computerPlayer,human};
            OthelloBoard testBoard;
            testBoard = new OthelloBoard(temp);
            testBoard.print();
            Scanner scan = new Scanner(System.in);
            int x,y;
            String p1,p2;
        while(true){
            //make a list of possible computer moves
            Point possibleMove = computerPlayer.move(testBoard);
            //choose a random one of those
            //int randomPoint = rand.nextInt(possibleMoves.size());
            int computerX = (int)possibleMove.getX();
            int computerY = (int)possibleMove.getY();
            //to make it obvious what move is possible
            System.out.println("place piece at: (" 
                                    + computerX + "," + computerY + ")");
            //attempt to set the piece
            testBoard.setPiece(computerX,computerY);
            //display the board
            testBoard.print();
            //used to display who's turn it is
            if (testBoard.getPlayerTurn() == 0) {
                p1 = "*";
                p2 = "";
            } else {
                p1 = "";
                p2 = "*";
            }
            try {
                
                System.out.println("Board Full: " + testBoard.isBoardFull());
                System.out.println(p1 + testBoard.getPlayer(0).getName() + " "
                         + testBoard.getPlayer(0).getScore());

                System.out.println(p2 + testBoard.getPlayer(1).getName() + " "
                         + testBoard.getPlayer(1).getScore());

                
                //ask user for input
                System.out.print("x: ");
                x = scan.nextInt();

                System.out.print("\ny: ");
                y = scan.nextInt();
                //because the board is created this way
                testBoard.setPiece(y, x);
                //display the new updated board
                testBoard.print();
                //if something other than an int is entered
            } catch (Exception e) { 
                scan.next();
            }

            
                
        }
    }
};
        
}
