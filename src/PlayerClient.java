// Client Section

import java.awt.Font;
import static java.awt.image.ImageObserver.ABORT;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;


public class PlayerClient
{    
    private int playerNumber;
    String serverAddress;
    Socket socket;
    DataOutputStream toServer;
    BufferedReader fromServer;
    Window window;
    private static boolean waitingForConnection;
    private ArrayList<Card> arrayListAllCards;
    private static final Card[] cardArray = new Card[52];  
    private static final String[] cardSuite = {
        "Hearts","Clubs","Diamond","Spade"
    };
    private static final String[] cardNumber = {
        "2","3","4","5","6","7","8","9","10",
        "J","Q","K","A"
    };
    
    static void cardInit()
    {
        Misc.createCardAndSetNamePowerImage(Misc.cardArray);
    }
    
    static void initServerStreamClientGame(Person person, Window window)
    {
        try {
            // -> Get IP of server and save it to String ServerIP
            //String ServerIP = "127.0.0.1";
            String ServerIP = null;
            
            ImageIcon icon = new ImageIcon(Window.class.getResource("Project Collection\\other\\ipIcon.jpg"));
            
            ServerIP = (String) JOptionPane.showInputDialog(window, "Enter The IP Adress Of the Server :", "Server IP Adress", INFORMATION_MESSAGE, icon, null,"127.0.0.1");
            //JOptionPane.showI
            if(ServerIP == null)
                System.exit(ABORT);
            Socket socket = new Socket(ServerIP, 5000);     // Get ServerIP as input 
            DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String firstMsg = fromServer.readLine();

        int index = -1;
        String msg = fromServer.readLine();       //String message = "Index: " + playerNumber + "\n";
        if(msg.startsWith("Index:"))
        {
            
            setWaitingState(false);
            index = (int) msg.charAt(7) - (int) '0';
            AddAudio.index = index;
        }
        PlayerClient player = new PlayerClient(index, ServerIP, socket, toServer, fromServer,window);
        player.gamePlay(person);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
//    public static void main(String[] args) throws UnknownHostException, IOException
//    {
//        // -> Show start page
//        
//
//        cardInit();
//        per
//        initServerStreamClientGame();
//    }

    public PlayerClient(int playerNumber, String serverAddress, Socket socket, DataOutputStream toServer, BufferedReader fromServer, Window window) throws IOException {
        this.playerNumber = playerNumber;
        this.serverAddress = serverAddress;
        this.socket = socket;
        this.toServer = toServer;
        this.fromServer = fromServer;
        arrayListAllCards = new ArrayList<>();
        this.window = window;
        window.playerNames[playerNumber-1] = "You";
    }
    
    private static void setWaitingState(boolean b) {
        waitingForConnection = b;
        //if(b) System.out.println("Waiting");
        //else System.out.println("Connected");
    }

    private void gamePlay(Person person) throws IOException {
        String msgFromServer;
        Lock lock = new Lock(fromServer);
        ReadThread rt = new ReadThread(lock);
        WriteThread wt = new WriteThread(window);
        //rt.start();
        while (true) {
            //System.out.println("Reached line 95");

            lock.waitForInput();
            msgFromServer = lock.toPlayerClient;
            //System.out.println("Reached line ");
            
            //System.out.println("Reached line 97");
            if(msgFromServer.startsWith("All Cards: "))
            {
                String allCards = msgFromServer.substring(11);
                System.out.println(allCards);
                for (int i = 0,j = 0; i < allCards.length(); i++) {
                    //int j = i;
                    if(allCards.charAt(i) == ' ')
                    {
                        String singleCard = allCards.substring(j, i);
                        j = i + 1;
                        //System.out.println(singleCard);
                        i++;
                        for(int k = 0; k < Misc.cardArray.length; k++)
                        {
                            if(singleCard.equals(Misc.cardArray[k].getCardName()))
                            {
                                person.arrayListAllCards.add(Misc.cardArray[k]);
                                person.giveCard(Misc.cardArray[k]);
                                // take person player as parameter & replace by player.arrayListAllCards 
                                break;
                            }
                                
                        }
                        window.endWaitingState();
                    }
                    
                }
            }
            //System.out.println("Reached Line 125");
            else if(msgFromServer.startsWith("Wait"))
            {
                System.out.println(msgFromServer);
                //setWaitingForTurn(true);
            }
            else if(msgFromServer.startsWith("fBC"))
            {
                //System.out.println(msgFromServer);
                String currentSuite = msgFromServer.substring(5);
                System.out.println("CurrentSuite: " + currentSuite);
                window.currentSuite = currentSuite;
            }
//            System.out.println("Reached line 126");
//            
//            System.out.println("Reached line 140");
            else if(msgFromServer.startsWith("You"))
            {
                //setWaitingForTurn(false);
                System.out.println(msgFromServer);
                window.setMyTurn(true);
                // click on a card
                // through mouse handling, send clicked card to server (i.e. Move: playerNo Cardname\n)
                // string s = click();
                System.out.println("list of available cards:");
                for(Card card: person.arrayListAllCards)
                {
                    System.out.print(card.getCardName() + " ");
                }
//                window.sentCard=null;
//                while(window.sentCard==null)
//                {
//                    System.out.println("sentCard == null");
//                while(window.sentCard!=null)
//                {
//                    System.out.println("sentCard != null");
//                    String move = window.sentCard.getCardName();
//                    move = "Move: " + playerNumber + " " + move;
//                    toServer.writeBytes(move + "\n");
//                    System.out.println(move);
//                    break;
//                }
//                }
                
                synchronized(window)
                {
                    while(window.sentCard == null)
                    {
//                        try {
//                            window.wait();
//                        } catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                        }
                        window.notifyAll();
                    }
                    String move = window.sentCard.getCardName();
                    move = "Move: " + playerNumber + " " + move;
                    toServer.writeBytes(move + "\n");
                    System.out.println(move);
                    window.sentCard = null;
                    window.notifyAll();
                }
                

//                Scanner sc = new Scanner(System.in);
//                System.out.println("\nGive a move " + playerNumber + ": ");
//                String move = sc.nextLine();
//                person.arrayListAllCards.remove(Misc.detectCard(arrayListAllCards, move));
//                
//                move = "Move: " + playerNumber + " " + move;
//                toServer.writeBytes(move + "\n");
                //window.setMyTurn(false);
                
                
            }
            //System.out.println("Reached line 165");
            else if(msgFromServer.startsWith("Move: "))
            {
                System.out.println(playerNumber + msgFromServer);
                int currentPlayer = (int) msgFromServer.charAt(6) - (int) '0';
                String movedCardName = msgFromServer.substring(8);
                System.out.println(movedCardName);
                Card movedCard = null;
                for(int i = 0; i < 52; i++)
                {
                    if(Misc.cardArray[i].getCardName().equals(movedCardName))
                    {
                        movedCard = Misc.cardArray[i];
                        window.insertOtherPlayersCard(currentPlayer, playerNumber, movedCard);
                        break;
                    }
                        
                }
                if(movedCard!=null)System.out.println(movedCard.getCardName());
                // show anim of the move made by the current player
            }
            //System.out.println("Reached line 185");
            else if(msgFromServer.startsWith("Winner: "))
            {
                int winner = (int) msgFromServer.charAt(8) - (int) '0';
                window.playersPointArray[winner-1]+=20;// increment winners point(winner.pointarray)
                window.setWonCardTarget(playerNumber, winner);
                System.out.println("Winner: "+ winner);
                System.out.println("Resetting currentSuite...");
                window.currentSuite = null;
                // Show person[winner] winning.
            }
            else if(msgFromServer.startsWith("Set"))
            {
                // reset currentSuite
                System.out.println(msgFromServer);
            }
            //System.out.println("Reached line 192");
            else if(msgFromServer.startsWith("GO"))             //Game Over
            {
                window.ultimateWinnerIndex = window.detectUltimateWinner();
                window.windowStatus = Window.Status.SHOW_POINT_STATE;
            }
//            else if(msgFromServer.startsWith("Results: "))
//            {
//                for(int i = 1; i <= 4; i++)
//                {
//                    int point;
//                    String result = msgFromServer.substring(9);
//                    point = Integer.valueOf(result);
//                    window.playersPointArray[i - 1] = point;
//                    if(i < 4)
//                        msgFromServer = fromServer.readLine();
//                    System.out.println(msgFromServer);
//                }
//                window.windowStatus = Window.Status.SHOW_POINT_STATE;
//                
//                System.out.println("point state initiated");
//            }
            //System.out.println("Reached line 205");
            
        }
    }

    private void setWaitingForTurn(boolean b) {
        // if (b) show anim of "Wait for your turn"
        // if (!b) show "your turn"
    }

}
