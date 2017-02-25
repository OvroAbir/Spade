
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Joy
 */
public class Misc {
    
    public static final Card[] cardArray = new Card[52];
        
    public static final String[] cardSuite = {
        "Hearts","Clubs","Diamond","Spade"
    };
    public static final String[] cardNumber = {
        "2","3","4","5","6","7","8","9","10",
        "J","Q","K","A"
    };
        
    public static void createCardAndSetNamePowerImage(Card[] card)
    {
        for(int i=0;i<card.length;i++)
            card[i] = new Card();
        setCardNameAndPowerAndImage(cardArray, cardSuite, cardNumber);
    }
    public static void createPersonObject(Person[] person)
    {
        for(int i=0;i<person.length;i++)
            person[i] = new Person();
    }
    
    public static void setCardNameAndPowerAndImage(Card[] cardArrayTemp,String[] cardSuiteTemp,String[] cardNumberTemp)
    {
        int power = 1;
        for(int i=0;i<cardSuiteTemp.length;i++)
        {
            if(i!=3)
                power = 1;
            for(int j=0;j<cardNumberTemp.length;j++)
            {
                cardArrayTemp[13*i+j].setCardSuite(cardSuiteTemp[i]);
                cardArrayTemp[13*i+j].setCardName(cardSuiteTemp[i]+cardNumberTemp[j]);
                cardArrayTemp[13*i+j].setCardPower(power);
                cardArrayTemp[13*i+j].loadFrontImage();
                power++;
            }
        }
    }
    public static void cardShuffle(Card[] cardArrayTemp)
    {
        int second;
        Card cardTemp;
        Random randomNumber = new Random();
        for(int first=0;first<cardArrayTemp.length;first++)
        {
            second = randomNumber.nextInt(cardArrayTemp.length);
            cardTemp = cardArrayTemp[second];
            cardArrayTemp[second] = cardArrayTemp[first];
            cardArrayTemp[first] = cardTemp;
        }
    }
    public static void distributeCard(Card[] cardArrayTemp, Person[] personArrayTemp)
    {
        for(int i=0;i<personArrayTemp.length;i++)
        {
            for(int j=0;j<13;j++)
            {
                personArrayTemp[i].giveCard(cardArrayTemp[13*i+j]);
            }
        }
    }
    
    public static ArrayList<Card> concatArrayList(Person person, ArrayList<Card>card1, ArrayList<Card>card2)
    {
        ArrayList<Card>arrayListTemp = new ArrayList<Card>();
  
        arrayListTemp.addAll(card1);
        arrayListTemp.addAll(card2);
        
        return  arrayListTemp;
    }
    public static ArrayList<Card> concatArrayList(Person person, ArrayList<Card>card1, ArrayList<Card>card2, ArrayList<Card>card3)
    {
        ArrayList<Card>arrayListTemp = new ArrayList<Card>();
        
        arrayListTemp.addAll(card1);
        arrayListTemp.addAll(card2);
        arrayListTemp.addAll(card3);
        
        return arrayListTemp;
    }
    public static ArrayList<Card> concatArrayList(Person person, ArrayList<Card>card1, ArrayList<Card>card2, ArrayList<Card>card3, ArrayList<Card>card4)
    {
        ArrayList<Card>arrayListTemp = new ArrayList<Card>();
        
        arrayListTemp.addAll(card1);
        arrayListTemp.addAll(card2);
        arrayListTemp.addAll(card3);
        arrayListTemp.addAll(card4);
        
        return arrayListTemp;
    }
    
    public static int whoWins(Card[] card, Card firstBidderCard)    
    {
        int winnerPerson = 0;
        int maxPower =  firstBidderCard.getCardPower();
        String currentSuite = firstBidderCard.getCardSuite();
        
        for(int i=0;i<4;i++)
        {
            if(firstBidderCard.getCardName().compareTo(card[i].getCardName())==0)
            {
                winnerPerson = i;
                break;
            }
        }
        for(int i=0;i<4;i++)
        {
            if((card[i].getCardSuite().compareTo(currentSuite)==0 && card[i].getCardPower()>maxPower )||
                    (card[i].getCardSuite().compareTo("Spade")==0 && card[i].getCardPower()>maxPower ))
            {
                winnerPerson = i;
                maxPower = card[i].getCardPower();
            }
        }
        return winnerPerson;
    }
    
    public static void showAvailableCards(ArrayList<Card>availableCards)
    {
        System.out.printf("Available Cards for Person are :\n");
        for(int i=0;i<availableCards.size();i++)
        {
            System.out.printf("%s ",availableCards.get(i).getCardName());
        }
        System.out.println();
    }
    
    public static Card detectCard(ArrayList<Card>suggestedCard, String cardName)
    {
        for(int i=0;i<suggestedCard.size();i++)
        {
            if(suggestedCard.get(i).getCardName().compareTo(cardName)==0)
                return suggestedCard.get(i);
        }
        return null;
    }
    
    public static void showHelp(Window w)
    {
        JOptionPane.showMessageDialog(w, "help Shown");
    }

    static void showAbout(Window w)
    {
        JOptionPane.showMessageDialog(w, "about Shown");
    }

    static void setPersonCardPositionYAndTarget(Person person, int height)
    {
        for(int i=0;i<person.getAllCards().size();i++)
        {
            person.getAllCards().get(i).setImagePositionY(height-110-50);
            person.getAllCards().get(i).setImageTargetPositionY(height/2-15);
            
        }
    }
    
    static Card createCardFromName(String cardNameTemp)
    {
        Card tempCard = null;
        int power = 1;
        for(int i=0;i<cardSuite.length;i++)
        {
            if(i!=3)
                power = 1;
            for(int j=0;j<cardNumber.length;j++)
            {
                if(cardNameTemp.equals(cardSuite[i]+cardNumber[j]))
                {
                    tempCard = new Card();
                    tempCard.setCardSuite(cardSuite[i]);
                    tempCard.setCardName(cardNameTemp);
                    tempCard.setCardPower(power);
                    tempCard.loadFrontImage();
                    power++;
                }
            }
        }
        return tempCard;
    }
}