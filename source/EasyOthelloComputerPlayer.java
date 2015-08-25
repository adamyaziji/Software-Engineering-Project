import java.awt.Color;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.Point;



/**
 * @file OthelloComputerPlayer.java
 * @author Thomas Vass, Adam Yaziji
 * @date 18 March 2014
 * 
 * @brief Creates a simple computer player
 * @details This computer player will make a random valid move. It takes in the
 * current state of the board and then makes a move based on this.
 */
public class OthelloComputerPlayer extends Player{

    protected final int BOARD_WIDTH = 8;
    protected final int BOARD_HEIGHT = 8;
    protected static Random rand = new Random();
    
    
    public OthelloComputerPlayer(String name, int score, Color color) {
        super(name, score, color);
    }


    /**
    * takes the current state of the game then works out all possible moves
    */
    public Point move (OthelloBoard board){
        //atempt to find all valid moves
        OthelloBoard tempBoard = board;
        ArrayList<Point> listOfLocations= new ArrayList<Point>();
        GamePiece newPiece = new GamePiece(board.getPlayer(board.getPlayerTurn()));
        for(int i=0; i < BOARD_HEIGHT; i++){
            for(int j=0; j < BOARD_WIDTH; j++){
                listOfLocations.add(board.getValidMove(i,j,newPiece));
                //reset the board
                board=tempBoard;
            }
        }
        listOfLocations.removeAll(Collections.singleton(null));
        //choose a random one of those
        if(listOfLocations.size()==0){
            return null;
        }else{
            int randomPoint = rand.nextInt(listOfLocations.size());


            //System.out.println(listOfLocations.size());

            for (int k = 0; k< listOfLocations.size();k++){
                int computerX = (int)listOfLocations.get(k).getX();
                int computerY = (int)listOfLocations.get(k).getY();
                System.out.println("possible valid move at: (" +computerX+","+computerY+")");
            }
            return listOfLocations.get(randomPoint);
        }
        
    }

    public boolean placePiece(int xPos, int yPos){
        return true;
    }


    public static void main (String args[]){
        t.start();
    }


    static Thread t = new Thread() {
        public void run() {
            OthelloComputerPlayer computerPlayer;
            computerPlayer = new OthelloComputerPlayer("Computer", 2, Color.WHITE);
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
            System.out.println("place piece at: (" +computerX+","+computerY+")");
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
