import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author Joy
 */
public class Window extends JPanel implements MouseListener, MouseMotionListener
{
    Color cornFlowerBlue = new Color(100,149,237);
    public Status windowStatus;
    String[] playerNames = {"Player1", "Player2", "Player3", "Player4"};
    boolean myTurn;
    Person person;
    ArrayList<Card>cardArrayPlayedOnDeck;
    ArrayList<Card>cardArrayWon;
    ArrayList<Card>biggerCardArray;
    private boolean backImagesInitiated;
    public Card sentCard;
    public int[] playersPointArray = new int [4];


    public enum Status{START_MENU, HELP_STATE, ABOUT_STATE, WAITING_STATE, DISTRIBUTE_CARD, DECK_STATE, SHOW_POINT_STATE};
    
    public static boolean isMovingFinished;
    public String currentSuite;
    public int ultimateWinnerIndex;
    public int myPlayerIndex;
    public Window(Person person)
    {
        windowStatus = Status.START_MENU;
        myTurn = false;
        //myTurn = true;//should be changed.................!!!!!
        this.person = person;
        cardArrayPlayedOnDeck = new ArrayList<Card>();
        cardArrayWon = new ArrayList<Card>();
        backImagesInitiated = false;
        sentCard = null;
        isMovingFinished = true;
        
        biggerCardArray = new ArrayList<Card>();
        
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(cornFlowerBlue);

    }
    public void insertOtherPlayersCard(int personIndex, int myIndex, Card card)
    {
        //System.out.println("personIndex: " + personIndex + ", myIndex: " + myIndex);
        myPlayerIndex = myIndex;
        int width = getWidth();
        int height = getHeight();
//        personIndex = myIndex - personIndex;
//        if(personIndex == 1 &&(myIndex == 2 || myIndex == 3))
//            personIndex += 2;
//        personIndex = Math.abs(personIndex);
        if((myIndex == (personIndex+1)) || (personIndex == 4 && myIndex == 1))
        {
            card.setImagePositionX(50);
            card.setImagePositionY(height/2 - 110/2);//bam
            card.setImageTargetPositionX(width/2-80*3/2);
            card.setImageTargetPositionY(height/2 - 110/2);
        }
        else if((personIndex == (myIndex+2)) || (personIndex == 2 && myIndex == 4) ||(personIndex == 1 && myIndex == 3))
        {
            card.setImagePositionX(width/2 - 80/2);
            card.setImagePositionY(50);
            card.setImageTargetPositionX(width/2 - 80/2);//upor
            card.setImageTargetPositionY(height/2 - 110*3/2);
        }
        else if((personIndex == (myIndex+1)) || (personIndex == 1 && myIndex == 4))
        {
            card.setImagePositionX(width - 80-50);
            card.setImagePositionY(height/2 - 110/2);
            card.setImageTargetPositionX(width/2 + 80/2);//dan
            card.setImageTargetPositionY(height/2 - 110/2);
        }
        card.setHasToMove(true);
        cardArrayPlayedOnDeck.add(card);
    }
    
    public int detectUltimateWinner()
    {
        int winner = 0;
        int max_point = 0;
        for(int i=0;i<playersPointArray.length;i++)
        {
            if(playersPointArray[i]>max_point)
            {
                max_point = playersPointArray[i];
                winner = i;
            }
        }
        return winner+1;
    }
    
        public int detectUltimateWinnerScore()
    {
        int winner = 0;
        int max_point = 0;
        for(int i=0;i<playersPointArray.length;i++)
        {
            if(playersPointArray[i]>max_point)
            {
                max_point = playersPointArray[i];
                winner = i;
            }
        }
        return max_point;
    }
    
    public void paintPlayedCard(Graphics g)
    {
        Card tempCard;
        for(int i=0;i<cardArrayPlayedOnDeck.size();i++)
        {
            tempCard = cardArrayPlayedOnDeck.get(i);
            g.drawImage(tempCard.getFrontImage() , tempCard.getImagePositionX(),tempCard.getImagePositionY(), this);
        }
    }
    
    private void initBackImage()
    {
        if(backImagesInitiated)
            return;

        //stop waiting music and play card shuffling sound.................
        
        int height= getHeight();
        int width = getWidth();
        int count = 0;
        for(int i=0;i<13;i++,count++)
        {
            posX[i] = 662 - 80/2;
            posY[i] = 345 - 110/2-(count*115);
            posTargetX[i] = 662 -80/2;
            posTargetY[i] = height+140;
        }
        count=0;
        for(int i=13;i<26;i++,count++)
        {
            posX[i] = 662 - 80/2 + (count*85);
            posY[i] = 345 - 110/2;
            posTargetX[i] = -90;
            posTargetY[i] = 345 - 110/2;
        }
        count=0;
        for(int i=26;i<39;i++,count++)
        {
            posX[i] = 662 - 80/2;
            posY[i] = 345 - 110/2+(count*115);
            posTargetX[i] = 662 - 80/2;
            posTargetY[i] = -140;
        }
        count=0;
        for(int i=39;i<52;i++,count++)
        {
            posX[i] = 662 + 80/2 - (85*count);
            posY[i] = 345 -110/2;
            posTargetX[i] = width+95;
            posTargetY[i] = 345 - 110/2;
        }
    }
    int[]posX = new int[52];
    int[]posY = new int[52];
    int[]posTargetX = new int[52];
    int[]posTargetY = new int[52];
    private void paintDisrubuteCard(Graphics g)
    {
       // windowStatus = Status.DECK_STATE;
        
        if(backImagesInitiated == false)
        {
            initBackImage();
            backImagesInitiated = true;
        }
        
        drawPlayingBoard(g);
        //Image backImg = new ImageIcon("E:\\Project Collection\\Card Collection\\Back\\back.jpg").getImage();
        Image backImg = new ImageIcon(Window.class.getResource("Project Collection\\Card Collection\\Back\\back.jpg")).getImage();
        for(int i=0;i<13;i++)
        {
            g.drawImage(backImg, 662 - 80/2, 345-110/2, this);//662 and 345 is calculated from paintInnerBoard
            
            if(posY[i] >= 345-110/2)
                g.drawImage(backImg, posX[i], posY[i], this);//the central image
            if(posY[i]<posTargetY[i])
                posY[i]+=4;
        }
        for(int i=13;i<26;i++)
        {
            g.drawImage(backImg, 662 - 80/2, 345-110/2, this);
            if(posX[i] <= 662-80/2)
                g.drawImage(backImg, posX[i], posY[i], this);
            if(posX[i]>posTargetX[i])
                posX[i]-=4;
        }
        for(int i=26;i<39;i++)
        {
            g.drawImage(backImg, 662 - 80/2, 345-110/2, this);
            if(posY[i] <= 345-80/2)
                g.drawImage(backImg, posX[i], posY[i], this);
            if(posY[i]>posTargetY[i])
                posY[i]-=4;
        }
        for(int i=39;i<52;i++)
        {
            g.drawImage(backImg, 662 - 80/2, 345-110/2, this);
            if(posX[i] >= 662-80/2)
                g.drawImage(backImg, posX[i], posY[i], this);
            if(posX[i]<posTargetX[i])
                posX[i]+=4;
        }
        
        drawPlayersFace(g);
        
        
        int difX, difY;
        int flag = 0;
        for(int i=0;i<52;i++)
        {
            difX = posX[i] - posTargetX[i];
            difY = posY[i] - posTargetY[i];
            if(Math.abs(difX)>4 || Math.abs(difY)>4 )
            {
                flag = 1;
            }
        }
        if(flag == 0)
        {
            windowStatus = Status.DECK_STATE;
            AddAudio.playCardShufflingAudio(3000);
            //************************ start shuffilng sound*****************************
          //  stop shuffling snd..................................
        }
        repaint();
        
    }
    
    public void setWonCardTarget(int myIndex, int winnerIndex)
    {/**** play winning 1 turn audio*******/
        //AddAudio.playCardTakeOutAudio(200);
        
        int width = getWidth();
        int height = getHeight();
        
        int targetX = 0;
        int targetY = 0;
        
        Card cardTemp;
        for(int i=0;i<cardArrayPlayedOnDeck.size();i++)
        {
            cardTemp = cardArrayPlayedOnDeck.get(i);
            cardTemp.setImageTargetPositionX(targetX);
            cardTemp.setImageTargetPositionY(targetY);
            cardArrayWon.add(cardTemp);
        }
        for(int i=cardArrayPlayedOnDeck.size()-1;i>=0;i--)
            cardArrayPlayedOnDeck.remove(i);
        
        if(winnerIndex == myIndex)
        {
            AddAudio.playWinningOneRoundAudio(500);
            targetY = height+150;
            
            for(int i=0;i<cardArrayWon.size();i++)
            {
                cardArrayWon.get(i).setImageTargetPositionY(targetY); //niche
                cardArrayWon.get(i).setImageTargetPositionX(cardArrayWon.get(i).getImagePositionX());
            }
        }
        else if((winnerIndex == (myIndex+1)) || (winnerIndex == 1 && myIndex == 4))
        {
            targetX = width + 100;
            
            for(int i=0;i<cardArrayWon.size();i++)
            {
                cardArrayWon.get(i).setImageTargetPositionX(targetX); // dan
                cardArrayWon.get(i).setImageTargetPositionY(cardArrayWon.get(i).getImagePositionY());
            }
        }
        else if((winnerIndex == (myIndex+2)) || (winnerIndex == 2 && myIndex == 4) ||(winnerIndex == 1 && myIndex == 3))
        {
            targetY = -150;
            
            for(int i=0;i<cardArrayWon.size();i++)
            {
                cardArrayWon.get(i).setImageTargetPositionY(targetY);//upor
                cardArrayWon.get(i).setImageTargetPositionX(cardArrayWon.get(i).getImagePositionX());
            }
        }
        else if((myIndex == (winnerIndex+1)) || (winnerIndex == 4 && myIndex == 1))
        {
            targetX = -100;
            
            for(int i=0;i<cardArrayWon.size();i++)
            {
                cardArrayWon.get(i).setImageTargetPositionX(targetX);
                cardArrayWon.get(i).setImageTargetPositionY(cardArrayWon.get(i).getImagePositionY());//bam
            }
        }
        
        for(int i=0;i<cardArrayWon.size();i++)
            cardArrayWon.get(i).setHasToMove(true);
    }
    
    public void paintWonCard(Graphics g)
    {
        Card tempCard;
        for(int i=0;i<cardArrayWon.size();i++)
        {
            tempCard = cardArrayWon.get(i);
            g.drawImage(tempCard.getFrontImage() , tempCard.getImagePositionX(),tempCard.getImagePositionY(), this);
        }
        
        boolean removeWonCards = true;
        for(int i=0;i<cardArrayWon.size();i++)
        {
            tempCard = cardArrayWon.get(i);
            if(tempCard.getImagePositionX() != tempCard.getImageTargetPositionX() || tempCard.getImagePositionY() != tempCard.getImageTargetPositionY() )
                removeWonCards = false;
        }
        
        if(removeWonCards)
        {
            for(int i=0;i<cardArrayWon.size();i++)
                cardArrayWon.remove(0);
        }
        repaint();
    }
    
    
    public String helpTexts = readHelpFile();
    private final int helpTextPosX = 15;
    private final int helpTextPosY = 15;
    private final int helpTextLineSpace = 14;
    private boolean helpTextPrintFinished = false;
    private int helpStringLineCounter =0;
    public String readHelpFile()
    {
        String tempReadString = "Can't Read";
        try
        {
            tempReadString = new Scanner(new File("src\\file.txt")).useDelimiter("\\A").next();
        }catch (FileNotFoundException ex)
        {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempReadString;
    }
    
        public void paintHelpText(Graphics g, String text)
    {
        Image backGroundImg = new ImageIcon(Window.class.getResource("Project Collection\\other\\helpBg.jpg")).getImage();
        g.drawImage(backGroundImg, 0, 0, this);
        g.setFont(new Font("Consolas", 0, helpTextLineSpace));
        String[] tempString = text.split("\n");
        for(int i=0;i<helpStringLineCounter;i++)
        {
            if(i >= 15 && i <= 26)
                g.setColor(Color.YELLOW);
            else
                g.setColor(Color.WHITE);
            if(i< tempString.length)
                g.drawString(tempString[i], helpTextPosX, helpTextPosY + helpTextLineSpace*i);
            //System.out.println(tempString[i]);
        }
        
        helpStringLineCounter++;
        
        
        try
        {
            Thread.sleep(300);
        }catch (InterruptedException ex)
        {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(helpStringLineCounter == tempString.length + 1)
        {
            Image goBackImg = new ImageIcon(Window.class.getResource("Project Collection\\other\\back.jpg")).getImage();
            g.drawImage(goBackImg, 25, getHeight() - goBackImg.getHeight(this) - 20, this);
            helpTextPrintFinished = true;
           // g.drawRect( 25, getHeight() - goBackImg.getHeight(this) - 20, goBackImg.getWidth(this), goBackImg.getHeight(this));
        }
        repaint();
    }
    
    
    
    private void drawAnImage(Graphics g, String locationAndName, int x, int y)
    {
        try 
        {
            Image img = new ImageIcon(locationAndName).getImage();
            g.drawImage(img, x, y, this);
        } catch (Exception e) 
        {
            System.err.println(locationAndName+" Not Found");
        }
        
    }
    
    private void paintBiggerImage(Graphics g)
    {
        Card cardTemp = null;
        if(biggerCardArray.isEmpty() == false)
            cardTemp = biggerCardArray.get(0);
        
        if(cardTemp == null)
            return;
        
       // System.err.println("cardYemp is : "+cardTemp.getCardName());
        
        //Image cardImage = new ImageIcon(Window.class.getResource("Project Collection\\Card Collection\\All Cards\\"+cardTemp.getCardName())).getImage();
        Image cardImage = cardTemp.frontImage;
        if(cardImage == null)
        {
            System.err.println(cardTemp.getCardName() + " Not Found");
            return;
        }
        int drawX = cardTemp.getImagePositionX() - 5;
        int drawY = cardTemp.getImagePositionY() - 6;
        
        g.drawImage(cardImage, drawX, drawY, 79 + 10, 110 + 12, this);
        
        repaint();
    }
    
    private void drawAnImageSakib(Graphics g, URL locationAndName, int x, int y)
    {
        try 
        {
            Image img = new ImageIcon(locationAndName).getImage();
            g.drawImage(img, x, y, this);
        } catch (Exception e) 
        {
            System.err.println(locationAndName+" Not Found");
        }
        
    }

    
    boolean playOnce = true;
    boolean clapOnce = true;
    private void paintPoints(Graphics g)
    {
        int width = getWidth();
        int height = getHeight();
        
        int pointWindowX = 45;
        int pointWindowY = height/2 - 80;
        
        int pointWindowBoxX = pointWindowX+40+25;
        int pointWindowBoxY = pointWindowY + 25 + 25;
        
        int pointWindowWidth = 275;
        int pointWindowHeight = 300;
   
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        
        Image scoreCardImg = new ImageIcon(Window.class.getResource("Project Collection\\Exit Window\\scoreCardLogo.png")).getImage();
        g.drawImage(scoreCardImg, pointWindowX+10, pointWindowY-100, this);
        
        g.setColor(Color.BLUE);
        g.fillRoundRect(pointWindowX, pointWindowY, 400, 400, 20, 20);
        
        g.setColor(Color.BLACK);
        g.fillRoundRect(pointWindowX+20, pointWindowY+20, 360, 360, 20, 20);
        
        g.setColor(new Color(242,242,235));
        g.drawRect(pointWindowBoxX, pointWindowBoxY, pointWindowWidth, pointWindowHeight);
        
        
        for(int i=1;i<4;i++)
        {
            g.drawLine(pointWindowBoxX, pointWindowBoxY + 75*i, pointWindowBoxX + pointWindowWidth, pointWindowBoxY + 75*i);
        }
        
        g.drawLine(pointWindowBoxX+ pointWindowWidth/2, pointWindowBoxY, pointWindowBoxX+pointWindowWidth/2, pointWindowBoxY+pointWindowHeight);
        
        g.setFont(new Font("SANS_SERIF", 10, 25));

        int highScore = detectUltimateWinnerScore();
        Image cupImage = new ImageIcon(Window.class.getResource("Project Collection\\Exit Window\\cup4.png")).getImage();
        
        for(int i=0;i<playerNames.length;i++)
        {
            if(playerNames[i].length()<=14)
                g.drawString(playerNames[i], pointWindowBoxX+7, pointWindowBoxY+48 + 75*i);
            if(playersPointArray[i] == highScore)
                g.drawImage(cupImage, pointWindowBoxX + 145, pointWindowBoxY+20 + 75*i,30,36, this);
            g.drawString(String.format("%d",playersPointArray[i]), pointWindowBoxX+pointWindowWidth/2+45, pointWindowBoxY+48 + 75*i);
        }
        
        
        Image spadeLogo = new ImageIcon(Window.class.getResource("Project Collection\\Spade Logo\\spadeLogoExit.png")).getImage();
        g.drawImage(spadeLogo, width/2 - 489/2 +50 , 10, this);
        
        Image dancingImg = new ImageIcon(Window.class.getResource("Project Collection\\Dancing Anim\\dancingMan.gif")).getImage();
        g.drawImage(dancingImg, width - 400, 200, 420,420 , this);
        
        g.setColor(new Color(60,80,210));
        g.fillRoundRect(width-158-130, height -118, 175, 105, 50, 50);
        

	Image clappingImg = new ImageIcon(Window.class.getResource("Project Collection\\Exit Window\\Clapping.gif")).getImage();
        g.drawImage(clappingImg, width/2 - 50, 300,140,150, this);
		
		/*****play clapping sound*****/
        if(clapOnce)
        {
            AddAudio.playClappingAudio(2000);
            clapOnce = false;
        }
        
	Image quitButton = new ImageIcon(Window.class.getResource("Project Collection\\Exit Window\\exit.gif")).getImage();
        g.drawImage(quitButton, width-158-120, height -117, this);
		

        if(playersPointArray[myPlayerIndex-1] == highScore)
        {
            Image youWinImg = new ImageIcon(Window.class.getResource("Project Collection\\Exit Window\\You-win.gif")).getImage();
            g.drawImage(youWinImg, pointWindowX+522, 470, this);
			/***** play ultimate winning sound*****/
           if(playOnce) {
               AddAudio.playUltimateWinningAudio(1000);
               playOnce = false;
           }
           
        }
        //ultimateWinnerIndex = -1;
    }
    
    private void paintStartPage(Graphics g)
    {
        //setBackground(cornFlowerBlue);
        g.setColor(cornFlowerBlue);
        g.fillRect(0, 0, getWidth(), getHeight());
        int gameNameX = 260;
        int gameNameY = 30;
        int gameNameWidthX = 532;
        int gameNameHeightY = 247;
        int aceCardAnimX = gameNameX+gameNameWidthX+100;
        int aceCardAnimY = gameNameY+gameNameHeightY-70;
        int startGameX = gameNameX+gameNameWidthX/5;
        int startGameY = gameNameY+gameNameHeightY+40;
        int helpX = startGameX+83+80;
        int helpY = startGameY+92;
        int quitX = helpX;
        int quitY = helpY+92;
        int aboutX = 30;
        int aboutY = getHeight()-147;
        
        //printing name of game
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Spade Logo\\coollogo_com-6472590.jpg"), gameNameX, gameNameY);
        //drawAnImage(g, "E:\\Project Collection\\Spade Logo\\coollogo_com-6472590.jpg", gameNameX, gameNameY);
        //printing ace card animation
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\startAnimLow.gif"), aceCardAnimX, aceCardAnimY);
        //drawAnImage(g, "E:\\Project Collection\\startAnimLow.gif", aceCardAnimX, aceCardAnimY);
        //printing start Game button
        //spade logo 83X93, start game 305X92
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Button Colloction\\Set3\\StartGameSpadeLogo.jpg"), startGameX, startGameY);
        //drawAnImage(g, "E:\\Project Collection\\Button Colloction\\Set3\\StartGameSpadeLogo.jpg",startGameX,startGameY);
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Button Colloction\\Set3\\startGame.gif"), startGameX + 83, startGameY + 5);
        //drawAnImage(g, "E:\\Project Collection\\Button Colloction\\Set3\\startGame.gif",startGameX+83,startGameY+5);
        //printing help Button
        //help Button 139X92
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Button Colloction\\Set3\\help.gif"), helpX, helpY);
        //drawAnImage(g, "E:\\Project Collection\\Button Colloction\\Set3\\help.gif", helpX, helpY);
        //printing Quit Button
        //Quit Button 129X92
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Button Colloction\\Set3\\quit.gif"), quitX, quitY);
        //drawAnImage(g, "E:\\Project Collection\\Button Colloction\\Set3\\quit.gif", quitX, quitY);
        //printing about Button
        //about button 221X157
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Button Colloction\\Set1\\about.png"), aboutX, aboutY);
        //drawAnImage(g, "E:\\Project Collection\\Button Colloction\\Set1\\about.png", aboutX, aboutY);), aboutX, 
    }
    private void paintWaitingWindow(Graphics g)
    {
        //setBackground(new Color(88,88,88));
        g.setColor(new Color(88, 88, 88));
        g.fillRect(0, 0, getWidth(), getWidth());
        //int waitingClubX = getWidth()/2-260;
        //int waitingClubY = getHeight()/2-112;
        
        int imageWaitngWidth = 248;
        int imageWaitingHeight = 144;
        int waitingImageX = getWidth()/2-imageWaitngWidth/2;
        int waitingImageY = getHeight()/2 - imageWaitingHeight/2;
        
        int textX = getWidth()/2-360;
        int textY = getHeight()/2+110;
        
        //waitingClub 225X225
        //drawAnImage(g, "E:\\Project Collection\\waiting\\waitingClub.jpg", waitingClubX, waitingClubY);
        //waiting image 248X144
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\waiting\\waitingAnim.gif"), waitingImageX, waitingImageY);
        //drawAnImage(g, "E:\\Project Collection\\waiting\\waitingAnim.gif", waitingImageX, waitingImageY);
        //drawString
        g.setColor(Color.GREEN);
        g.setFont(new Font("Consolas", 1, 24));
        g.drawString("Waiting For Other Players . . .", textY, textY);
        repaint();
    }
    
    public void paintPersonCards(Person person, Graphics g)
    {
       // g.setColor(Color.WHITE);
        //g.fillRect(0, 0, getWidth(), getHeight());

        for(int i=0;i<person.getAllCards().size();i++)
        {
            Card cardTemp = person.getAllCards().get(i);
            cardTemp.setImagePositionX(i,getWidth());//this should be changed also.
            
            g.drawImage(cardTemp.getFrontImage(), cardTemp.getImagePositionX(), cardTemp.getImagePositionY(), null);
        }
        repaint();
    }
    private void paintInnerBoard(Graphics g)
    {
        g.setColor(cornFlowerBlue);
        g.fillRoundRect(110+25, 110+25, getWidth()-250-50, getHeight()-250-50, 150,150);
    }
    
    private void drawPlayingBoard(Graphics g)
    {
        g.setColor(cornFlowerBlue);
        //g.fillRect(0, 0, getWidth(), getHeight());
        Image deckBgImg = new ImageIcon(Window.class.getResource("Project Collection\\other\\Blue-Backgrounds-4.jpg")).getImage();
        g.drawImage(deckBgImg, 0, 0,1355,846, this);
        
        g.setColor(Color.BLACK);
        g.fillRoundRect(110, 110, getWidth()-250, getHeight()-250, 150, 150);
        
        paintInnerBoard(g);
    }

    private void drawPlayersFace(Graphics g)
    {
        
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Player\\\\player1.jpg"), getWidth()/2-37 -12, 2);
        //drawAnImage(g, "E:\\Project Collection\\Player\\player1.jpg", getWidth()/2-30, 2);
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Player\\player2.jpg"), 2, getHeight()/2-42);
        //drawAnImage(g, "E:\\Project Collection\\Player\\player2.jpg", 2, getHeight()/2-30);
        drawAnImageSakib(g, Window.class.getResource("Project Collection\\Player\\player3.jpg"), getWidth()-75, getHeight()/2-42);
        //drawAnImage(g, "E:\\Project Collection\\Player\\player3.jpg", getWidth()-62, getHeight()/2-30);
    }
    
    public void paintDeck(Graphics g)
    {
        drawPlayingBoard(g);
        drawPlayersFace(g);
        
        if(myTurn)
            paintMyTurn(g);
        paintPersonCards(person, g);
        paintBiggerImage(g);
        paintPlayedCard(g);
        paintWonCard(g);
        
        repaint();
    }
    
    public void paintMyTurn(Graphics g)
    {
//        drawAnImageSakib(g, Window.class.getResource("Project Collection\\other\\yourTurnSmall.jpg"),getWidth()/2 - 90, getHeight() - 280);
        //drawAnImageSakib(g, Window.class.getResource("Project Collection\\other\\yourTurnNew.png"),getWidth() - 190, 7);
        g.setColor(new Color(253, 230, 177));
        g.setFont(new Font("Consolas", 0, 45));
        g.drawString("YOUR TURN", getWidth() - 260, 70);
    }
    
    public void paintAbout(Graphics g)
    {
        Image aboutImg = new ImageIcon(Window.class.getResource("Project Collection\\other\\AboutUs.gif")).getImage();
        g.drawImage(aboutImg, 0, 0, getWidth(), getHeight(), this);
    }
    
    
    @Override
    public void paintComponent(Graphics g)
    {
        if(windowStatus == Status.START_MENU)
        {
            paintStartPage(g);
        }
        else if(windowStatus == Status.HELP_STATE)
        {
            if(helpTextPrintFinished == false)
                paintHelpText(g, helpTexts);
        }
        else if(windowStatus == Status.ABOUT_STATE)
        {
            paintAbout(g);
        }
        else if(windowStatus == Status.WAITING_STATE)
        {
            paintWaitingWindow(g);
        }
        else if(windowStatus == Status.DISTRIBUTE_CARD)
        {
            paintDisrubuteCard(g);
        }
        else if(windowStatus == Status.DECK_STATE)
        {
            paintDeck(g);
        }
        else if(windowStatus == Status.SHOW_POINT_STATE)
        {
            paintPoints(g);
        }
    }
    
    public void setMyTurn(boolean boolValue)
    {
        myTurn = boolValue;
    }
    public void endWaitingState()
    {
        windowStatus = Status.DISTRIBUTE_CARD;
    }
    public int detectClickedCard(int posX, int posY)
    {
        //System.out.printf("clicked on---- %d %d\n",posX, posY);
        if(posY<570 || posY>(570+110))
            return -1;
        int serial = -1;
//        for(int i=0;i<13;i++)
//        {
//            if(posX<=50+(i+1)*(Card.getGap()+79))
//            {
//                serial = i;
//                break;
//            }
//        }
        
        int gap = 19;
        
        if(posX>= 47  && posX <= (47+79) )
            serial = 0;
        else if(posX >= 145 && posX<= (145+79))
            serial = 1;
        else if(posX >= 243 && posX<= (243+79))
            serial = 2;
        else if(posX >= 341 && posX<= (341+79))
            serial = 3;
        else if(posX >= 442 && posX<= (442+79))
            serial = 4;
        else if(posX >= 536 && posX<= (536+79))
            serial = 5;
        else if(posX >= 637 && posX<= (637+79))
            serial = 6;
        else if(posX >= 733 && posX<= (733+79))
            serial = 7;
        else if(posX >= 831 && posX<= (831+79))
            serial = 8;
        else if(posX >= 930 && posX<= (930+79))
            serial = 9;
        else if(posX >= 1027 && posX<= (1027+79))
            serial = 10;
        else if(posX >= 1125 && posX<= (1125+79))
            serial = 11;
        else if(posX >= 1223 && posX<= (1223+79))
            serial = 12;
        
        if(serial > person.arrayListAllCards.size()-1)
            serial =-1;
        
        return serial;
    }
    
     @Override
    public void mouseClicked(MouseEvent e)
    {
        int clickedX = e.getX();
        int clickedY = e.getY();
        System.out.println("\n clicked on-------"+clickedX +" "+clickedY);
        
        if(windowStatus == Status.START_MENU)
        {
            int gameNameX = 260;
            int gameNameY = 30;
            int gameNameWidthX = 532;
            int gameNameHeightY = 247;
            int aceCardAnimX = gameNameX+gameNameWidthX+100;
            int aceCardAnimY = gameNameY+gameNameHeightY-70;
            int startGameX = gameNameX+gameNameWidthX/5;
            int startGameY = gameNameY+gameNameHeightY+40;
            int helpX = startGameX+83+80;
            int helpY = startGameY+92;
            int quitX = helpX;
            int quitY = helpY+92;
            int aboutX = 30;
            int aboutY = getHeight()-147;
            
            if(clickedX>=startGameX && clickedX<=(startGameX+83+305) && clickedY>=startGameY && clickedY<=(startGameY+93))
            {
                windowStatus = Status.WAITING_STATE;
                //stop opening sound aand play waiting sound
                repaint();
            }
                      
            else if(clickedX>=helpX && clickedX<=(helpX+139) && clickedY>=helpY && clickedY<=(helpY+92))
            {
                windowStatus = Status.HELP_STATE;
                repaint();
            }
            
            else if(clickedX>=aboutX && clickedX<=(aboutX+221) && clickedY>=aboutY && clickedY<=(aboutY+157))
            {
                //Misc.showAbout(this);
                windowStatus = Status.ABOUT_STATE;
            }

            else if(clickedX >=quitX && clickedX<=(quitX+129) && clickedY>=quitY && clickedY <=(quitY+92))
            {
                System.exit(ABORT);
            }        
        }
        
        else if(windowStatus == Status.HELP_STATE)
        {
            int backX = 25;
            int backY = getHeight() - 50 -20;
            
            if(clickedX >= backX && clickedX <= (backX+110) && clickedY >= backY && clickedY <=(backY + 50))
            {
                windowStatus = Status.START_MENU;
                repaint();
            }
        }
        
        else if(windowStatus == Status.ABOUT_STATE)
        {
            if(clickedX >= 1089 && clickedX <= 1265 && clickedY >= 543 && clickedY <= 615)
            {
                windowStatus = Status.START_MENU;
                repaint();
            }
        }
        
   
        else if(windowStatus == Status.WAITING_STATE)
        {
           // System.out.println(windowStatus);
            //should be changed
            //windowStatus = Status.DISTRIBUTE_CARD;
            //System.out.printf("%d %d\n", clickedX, clickedY);
            //repaint();
        }
        
        
        else if(windowStatus == Status.DECK_STATE)
        {
            biggerCardArray.clear();
            if(myTurn == false || isMovingFinished == false)
            {
                //System.out.println("Returning bcz isMovingFinis is"+isMovingFinished);
                return;
            }
            Card cardTemp;
            int clickedCardIndex = detectClickedCard(e.getX(), e.getY());
            
            if(clickedCardIndex==-1)
                return;
            
            cardTemp = person.getAllCards().get(clickedCardIndex);
                          //  System.out.println("current suite is ...............: "+currentSuite);
            if(person.isCardBidAble(currentSuite, cardTemp) == false)
            {
                AddAudio.playWrongCardBiddingAudio(382);
                return;
            }
            
            cardTemp.setImageTargetPositionX(1350/2 - 80/2 -1);
            cardTemp.setImageTargetPositionY(400);
            cardTemp.setHasToMove(true);
			/*****play drawCard audio.*******/
            AddAudio.playCardDrawAudio(250);
            //send server card name
            person.removeCard(cardTemp);
            cardArrayPlayedOnDeck.add(cardTemp);
            myTurn = false;
            sentCard = cardTemp;
            
            
                   //insertOtherPlayersCard(1, Misc.createCardFromName("HeartsA"));
                   //insertOtherPlayersCard(2, Misc.createCardFromName("HeartsA"));
                   //insertOtherPlayersCard(3, Misc.createCardFromName("HeartsA"));
            
            repaint();
        }
        else if(windowStatus == Status.SHOW_POINT_STATE)
        {
            if(clickedX >=1067  && clickedX <=(1067+175) && clickedY>=603 && clickedY<=(602+105))
			{
			/****play ending sound*****/
                            AddAudio.playEndingAudio(5200);
                System.exit(ABORT);
			}
        }
        
    }
   
    @Override
    public void mousePressed(MouseEvent e)
    {
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {
    }
    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        System.out.println("mouseDragged");
    }

    Card previousCard = null;
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        int moveX = e.getX();
        int moveY = e.getY();
        biggerCardArray.clear();
        //System.out.println("Entered mouseMoved "+ moveX +" "+ moveY);
        
        if(windowStatus == Status.DECK_STATE)
        {
            
            if(myTurn == false || isMovingFinished == false)
            {
                //System.out.println("Not moving bcz isMovingFinish: "+isMovingFinished + " myTurn : "+myTurn);
                return;
            }
            Card cardTemp;
            int movedCardIndex = detectClickedCard(moveX, moveY);
            
            if(movedCardIndex == -1)
                return;
            
            cardTemp = person.getAllCards().get(movedCardIndex);
                          //  System.out.println("current suite is ...............: "+currentSuite);
            if(person.isCardBidAble(currentSuite, cardTemp) == false)
            {
                return;
            }
          //  System.out.println("Adding "+ cardTemp.getCardName());
            else if(person.isCardBidAble(currentSuite, cardTemp) == true)
            {
                biggerCardArray.add(cardTemp);
                if(previousCard != cardTemp)
                {
                    AddAudio.playMouseHoverAudio(64);
                    previousCard = cardTemp;
                }
            }

        }
    }
    
    
    
    
   /* public static void main(String[] arg)
    {
        Person p = new Person();
        JFrame frame = new JFrame("Spade");
        Window panel = new Window(p);
        //panel.addMouseListener(new mouseLiistener());
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1355,720);
        frame.setLocation(5, 5);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    */
}
