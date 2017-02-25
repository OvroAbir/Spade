
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

class Person extends Thread
{
    private ArrayList<Card> arrayListSpade;
    private ArrayList<Card> arrayListDiamonds;
    private ArrayList<Card> arrayListClubs;
    private ArrayList<Card> arrayListHearts;
    ArrayList<Card> arrayListAllCards;
    private String name;
    private int playerNumber;
    private Socket playerSocket;
    DataOutputStream toClient;
    BufferedReader fromClient;
    GameClass game;
    int flagSend;
    int flagReceived;
    //boolean hasReceived;

    public int getPlayerNumber() {
        return playerNumber;
    }
    
    public Person()
    {
        arrayListClubs = new ArrayList<Card>();
        arrayListDiamonds = new ArrayList<Card>();
        arrayListSpade = new ArrayList<Card>();
        arrayListHearts = new ArrayList<Card>();
        
        arrayListAllCards = new ArrayList<Card>();
    }
    
    
    public Person(GameClass game, Socket p, int num) throws IOException
    {
        this.game = game;    
        playerNumber = num;
        playerSocket = p;
        flagReceived = flagSend = 0;
        //hasReceived = false;
        arrayListClubs = new ArrayList<Card>();
        arrayListDiamonds = new ArrayList<Card>();
        arrayListSpade = new ArrayList<Card>();
        arrayListHearts = new ArrayList<Card>();
        toClient = new DataOutputStream(playerSocket.getOutputStream());
        fromClient = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        showWaitingMsg();
    }
    

    public void giveCard(Card card)
    {
        if (card.getCardSuite().equals("Spade"))
        {
            int position;
            for (position = 0; position < arrayListSpade.size(); position ++)
            {
                if (arrayListSpade.get(position).getCardPower() >= card.getCardPower())
                {
                    break;
                }
            }
            arrayListSpade.add(position, card);
        }
        else if (card.getCardSuite().equals("Diamond"))
        {
            int position;
            for (position = 0; position < arrayListDiamonds.size(); position ++)
            {
                if (arrayListDiamonds.get(position).getCardPower() >= card.getCardPower())
                {
                    break;
                }
            }
            arrayListDiamonds.add(position, card);
        }
        else if (card.getCardSuite().equals("Clubs"))
        {
            int position;
            for (position = 0; position < arrayListClubs.size(); position ++)
            {
                if (arrayListClubs.get(position).getCardPower() >= card.getCardPower())
                {
                    break;
                }
            }
            arrayListClubs.add(position, card);
        }
        else if (card.getCardSuite().equals("Hearts"))
        {
            int position;
            for (position = 0; position < arrayListHearts.size(); position ++)
            {
                if (arrayListHearts.get(position).getCardPower() >= card.getCardPower())
                {
                    break;
                }
            }
            arrayListHearts.add(position, card);
        }
        else
        {
            System.err.println("Error in give Card Function");
        }
    }

    public void removeCard(Card card)
    {
        switch (card.getCardSuite())
        {
            case "Spade":
                arrayListSpade.remove(card);
                break;
            case "Diamond":
                arrayListDiamonds.remove(card);
                break;
            case "Clubs":
                arrayListClubs.remove(card);
                break;
            case "Hearts":
                arrayListHearts.remove(card);
                break;
            default:
                System.err.println("Error in Removing Card");
        }
    }

    public boolean isSpadeEmpty()
    {
        return arrayListSpade.isEmpty();
    }
    public boolean isDiamondEmpty()
    {
        return  arrayListDiamonds.isEmpty();
    }
    public boolean isClubEmpty()
    {
        return  arrayListClubs.isEmpty();
    }
    public boolean isHeartsEmpty()
    {
        return  arrayListHearts.isEmpty();
    }
    
    public ArrayList<Card> getArrayListDiamond()
    {
        return arrayListDiamonds;
    }
    public ArrayList<Card> getArrayListClubs()
    {
        return arrayListClubs;
    }
    public ArrayList<Card> getArrayListSpade()
    {
        return arrayListSpade;
    }
    public ArrayList<Card> getArrayListHearts()
    {
        return arrayListHearts;
    }
    
    public ArrayList<Card> availableCardsToBid(String currentSuite)
    {
        if(currentSuite == null)
        {
            return Misc.concatArrayList(this, arrayListClubs, arrayListDiamonds, arrayListHearts, arrayListSpade);
        }
        
        else if(currentSuite.startsWith("D"))
        {
            if(!isDiamondEmpty())
                return arrayListDiamonds;
            else
                return Misc.concatArrayList(this,arrayListClubs, arrayListHearts, arrayListSpade);
        }
        else if(currentSuite.startsWith("C"))
        {
            if(!isClubEmpty())
                return arrayListClubs;
            else
                return Misc.concatArrayList(this,arrayListDiamonds, arrayListHearts, arrayListSpade);
        }
        else if(currentSuite.startsWith("H"))
        {
            if(!isHeartsEmpty())
                return arrayListHearts;
            else
                return Misc.concatArrayList(this, arrayListClubs,arrayListDiamonds, arrayListSpade);
        }
        else if(currentSuite.startsWith("S"))
        {
            if(!isSpadeEmpty())
                return arrayListSpade;
            else
                return Misc.concatArrayList(this, arrayListClubs, arrayListDiamonds, arrayListHearts);
        }
        return  null;
    }
    
    public boolean isCardBidAble(String currentSuite, Card card)
    {
        ArrayList<Card> tempArrayList = availableCardsToBid(currentSuite);
        if(tempArrayList == null || card == null)
        {
            System.err.println(".....There is an error in isCardAvailable. current Suite :"+currentSuite+" and card name : "+card.getCardName());
            return true;
        }
        return tempArrayList.contains(card);
    }
        
	
        Socket getSocket()
        {
            return playerSocket;
        }
	@Override
	public void run()
	{
		
		try
		{
                    showIndex();
                    sendAll13Cardnames();
                    
                    
                    
                    // GameLoop
                    while(true){
                    for(int i = 0; i < 13; i++)
                    {
                        
                        //showTurn();
                        for (int j = 0; j < 4; j++) {
                            if(flagReceived == 0 && flagSend == 0)
                            {
                                Thread.sleep(200);
				showTurn();
                            }
                                
                            if(game.currentPlayerNumber == playerNumber)
                            {
                                game.receiveOrSendMove(this);
                                if(j==0)
                                {
                                    
                                    game.firstBidderCard = game.fromCurrentPlayer;
                                    System.out.println("firstBidderCardName: " + game.firstBidderCard.getCardName());
                                    String fBC = "fBC: "+game.firstBidderCard.getCardSuite();
                                    Thread.sleep(100);
                                    sendCurrentSuiteToAll(fBC);
                                    System.out.println(fBC);
                                }
                                    
                                int sleep = 0;
                                while (game.isSent) {                                    
                                    if(++sleep <= 10)           //new
                                        Thread.sleep(100);
                                    else
                                        break;
                                }
                                //System.out.println("current awake");
                            }
                            else
                            {
                                while (!(game.isSent)) {                                    
                                    Thread.sleep(1000);
                                }
                                game.receiveOrSendMove(this);
                                while(flagSend != 0)
                                {
                                    Thread.sleep(1000);
                                }
                            }
                        }
                        
                        // wait for all players to make their moves.    (Not completed)
                        
                        int winner = Misc.whoWins(game.allCardsIn1Round, game.firstBidderCard) + 1;
                        sendNameOfWinner(winner);
                        if(winner == playerNumber)
                        {
                            game.point[playerNumber - 1] += 20;
                        }
                        game.currentPlayerNumber = winner;
                    }
                    
                    toClient.writeBytes("GO\n");
                    //sendResultToAll();
                    }
                    
                    //
                    
                    //playerSocket.close();
                }
		catch (Exception e)
		{
                    e.printStackTrace();
		}
	}

    public ArrayList<Card> getAllCards()
    {
        ArrayList<Card> tempArrayList = new ArrayList<Card>();
        tempArrayList.addAll(arrayListClubs);
        tempArrayList.addAll(arrayListDiamonds);
        tempArrayList.addAll(arrayListHearts);
        tempArrayList.addAll(arrayListSpade);

        return tempArrayList;
    }

    public void showIndex() throws IOException {
        String message = "Index: " + playerNumber + "\n";
        toClient.writeBytes(message);
        //System.out.println(message);
    }

    public void sendAll13Cardnames() throws IOException {
        arrayListAllCards = new ArrayList<Card>();
        
        arrayListAllCards.addAll(arrayListClubs);
        arrayListAllCards.addAll(arrayListDiamonds);
        arrayListAllCards.addAll(arrayListHearts);
        arrayListAllCards.addAll(arrayListSpade);
        
        String message = "All Cards: ";
        for (Card card : arrayListAllCards) {
            message += card.getCardName() + " ";
        }
        message += "\n";
        //System.out.println(message);
        
        toClient.writeBytes(message);
    }

    public void showTurn() throws IOException, InterruptedException {
        if(playerNumber == game.currentPlayerNumber)
            toClient.writeBytes("You\n");
        else
        {
            toClient.writeBytes("Wait\n");
            
        }
            
    }

    

    

    

    private void sendNameOfWinner(int winner) throws IOException {
        toClient.writeBytes("Winner: " + winner + "\n");
    }

    private void sendResultToAll() throws IOException {
        for(int i = 1; i <= 4; i++)
            toClient.writeBytes("Results: " + game.point[i - 1] + "\n");
    }

    private void showWaitingMsg() {
        try {
            toClient.writeBytes("Waiting\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendCurrentSuiteToAll(String fBC) {
        try {
            for (int i = 0; i < 4; i++) {
                CardServer.persons[i].toClient.writeBytes(fBC + "\n");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}