
import static java.awt.image.ImageObserver.ABORT;
import java.io.IOException;



/**
 *
 * @author Sakib
 */
public class GameClass {
    int point[] = new int[4];
    int currentPlayerNumber;
    String move;
    int numberOfSuccessfulSends;
    char playerNo;
    int playerNoInt;
    Card allCardsIn1Round[] = new Card[4];
    Card firstBidderCard;
    Card fromCurrentPlayer;
    boolean hasSent[] = new boolean[4];
    boolean isSent;     //n

    public GameClass() {
        for (int i = 0; i < point.length; i++) {
            point[i] = 0;
            hasSent[i] = false;
            
        }
        currentPlayerNumber = 1;
        numberOfSuccessfulSends = 0;
        isSent = false;     //n
    }
/*    
    synchronized void receiveMyMove(Person player) throws InterruptedException {
        try {
            System.out.println(player.getPlayerNumber());
            while(numberOfSuccessfulSends != 0)
            {
                wait();
            }
//            if(numberOfSuccessfulSends != 0)
//                return;
            move = player.fromClient.readLine();            // Move: playerNo Cardname\n
            System.out.println(player.getPlayerNumber() + move);
            numberOfSuccessfulSends++;
            
            playerNo = move.charAt(6);
            playerNoInt = (int) playerNo - (int) '0';
            String clickedCard = move.substring(8);
            fromCurrentPlayer = Misc.detectCard(player.arrayListAllCards, clickedCard);
            allCardsIn1Round[currentPlayerNumber - 1] = fromCurrentPlayer;
            player.removeCard(fromCurrentPlayer);
            notifyAll();
        } catch (IOException ex) {
            System.out.println("Error receiving response");
            System.exit(1);
        }
    }
    
    synchronized boolean sendCurrentPlayerMove(Person player) throws IOException, InterruptedException {
        //System.out.println(player.getPlayerNumber());
//        while (true) {            
//            if(!(move.equals("None")))
//            {
//                player.toClient.writeBytes(move);
//                break;
//            }
//            else
//                Thread.sleep(1000);
//        }
        while(numberOfSuccessfulSends == 0)
        {
            wait();
        }
        if(!(hasSent[player.getPlayerNumber() - 1]))
        {
            player.toClient.writeBytes(move + "\n");
            System.out.println(player.getPlayerNumber() + move);
            notifyAll();
            if(numberOfSuccessfulSends < 3)         // more sending to go for other threads
            {
                
                numberOfSuccessfulSends++;
            }
            else                                    // this is the last sender thread
            {
                numberOfSuccessfulSends = 0;
                for (int i = 0; i < hasSent.length; i++) {
                    hasSent[i] = false;
                    
                }
                System.out.println("entered");
                changeCurrentPlayer();
                //b = true;
                for(int i = 0; i < 4; i ++)
                {
                    CardServer.persons[i].flagReceived = 0;
                    CardServer.persons[i].flagSend = 0;
                }
                player.flagSend--;
                System.out.println("flagReceived: "+player.flagReceived);
                System.out.println("flagSend: "+player.flagSend);
            }
            hasSent[player.getPlayerNumber() - 1] = true;
            return true;
        }
        return false;
    }
*/
    public void changeCurrentPlayer() {
        if(currentPlayerNumber <= 3)
            currentPlayerNumber++;
        else
            currentPlayerNumber = 1;
    }
    
    synchronized void receiveOrSendMove(Person player) throws IOException
    {
        if(player.getPlayerNumber() == currentPlayerNumber)     // current
        {
            player.flagReceived++;
            isSent = true;
            move = player.fromClient.readLine();            // Move: playerNo Cardname\n
            
//            if(move == null)                                    // new
//                System.exit(ABORT);
            
            System.out.println(player.getPlayerNumber() + move);
            numberOfSuccessfulSends++;
            
            playerNo = move.charAt(6);
            playerNoInt = (int) playerNo - (int) '0';
            String clickedCard = move.substring(8);
            fromCurrentPlayer = Misc.detectCard(player.arrayListAllCards, clickedCard);
            allCardsIn1Round[currentPlayerNumber - 1] = fromCurrentPlayer;
            player.removeCard(fromCurrentPlayer);
            
        }
        
        else 
        {
//            if(move == null)                    //New
//                System.exit(ABORT);
            
            player.toClient.writeBytes(move + "\n");
            System.out.println(player.getPlayerNumber() + move);
            
            if(numberOfSuccessfulSends == 3)           // last sender
            {
                //System.out.println("last sender");
                for(int i = 0; i < 4; i ++)
                {
                    CardServer.persons[i].flagReceived = 0;
                    CardServer.persons[i].flagSend = 0;
                    //CardServer.persons[i].toClient.writeBytes("Set currentSuite to null\n");
                }
                changeCurrentPlayer();
                isSent = false;
                numberOfSuccessfulSends = 0;
            }
            else
            {
                player.flagSend++;
                numberOfSuccessfulSends++;
            }
        }
    }
}
