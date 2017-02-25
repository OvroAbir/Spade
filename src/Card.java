
import java.awt.Image;
import javax.swing.ImageIcon;


/**
 *
 * @author Joy
 */
public class Card
{
    private String cardSuite;
    private String cardName;
    private int cardPower;

    public Image frontImage;

    private int imagePositionX;
    private int imagePositionY;
    private int imageTargetPositionX;
    private int imageTargetPositionY;

    private boolean alreadyPlayed;
    private boolean hasToMove;
    private boolean canBid;

    private final int step;
    private static int gap;
    //private static Window window;
    
    public Card()
    {
        //Card.window = window;
        alreadyPlayed = false;
        hasToMove = false;
        canBid = false;
        imagePositionX = 0;
        imagePositionY = 570;// window.getHeight() - 110 - 20;
        imageTargetPositionX = 1350/2 - 80/2 -1;//window.getWidth()/2;
        imageTargetPositionY = 400; //window.getHeight()/2;
        step = 3;
    }
    

    public int getImageTargetPositionX()
    {
        return imageTargetPositionX;
    }

    public void setImageTargetPositionX(int imageTargetPositionX)
    {
        this.imageTargetPositionX = imageTargetPositionX;
    }

    public int getImageTargetPositionY()
    {
        return imageTargetPositionY;
    }

    public void setImageTargetPositionY(int imageTargetPositionY)
    {
        this.imageTargetPositionY = imageTargetPositionY;
    }

    public void loadFrontImage()
    {
        try
        {
            frontImage = new ImageIcon(Card.class.getResource("Project Collection\\Card Collection\\All Cards\\" + cardName + ".gif")).getImage();
        }
        catch (Exception e)
        {
            System.out.println(cardName + "Not Found");
        }
    }

    public String getCardSuite()
    {
        return cardSuite;
    }

    public void setCardSuite(String cardSuite)
    {
        this.cardSuite = cardSuite;
    }

    public String getCardName()
    {
        return cardName;
    }

    public void setCardName(String cardName)
    {
        this.cardName = cardName;
    }

    public int getCardPower()
    {
        return cardPower;
    }

    public void setCardPower(int cardPower)
    {
        this.cardPower = cardPower;
    }
    
    public void changeImagePosition()
    {
        if(imagePositionY != imageTargetPositionY)
        {
            Window.isMovingFinished = false;
            int difY = imagePositionY - imageTargetPositionY;
            if(Math.abs(difY) <= step)
                imagePositionY = imageTargetPositionY;
            else if(imageTargetPositionY > imagePositionY)
                imagePositionY+=step;
            else if(imageTargetPositionY < imagePositionY)
                imagePositionY-=step;

        }
        else if(imagePositionX != imageTargetPositionX)
        {
            Window.isMovingFinished = false;
            int difX = imagePositionX - imageTargetPositionX;
            if(Math.abs(difX) <= step)
                imagePositionX = imageTargetPositionX;
            else if(imageTargetPositionX > imagePositionX)
                imagePositionX+=step;
            else if(imageTargetPositionX < imagePositionX)
                imagePositionX-=step;
            //System.out.printf("%d %d %s\n", imagePositionX,imagePositionY,"in");
        }
    }

    public Image getFrontImage()
    {
        if(imagePositionY == imageTargetPositionY && imagePositionX == imageTargetPositionX)
            Window.isMovingFinished = true;
        
        if (hasToMove == true && (imagePositionY != imageTargetPositionY || imagePositionX!=imageTargetPositionX))
        {
            changeImagePosition();
                       // System.out.printf("%d %d %s\n", imagePositionX,imagePositionY,cardName);
        }
        else if (hasToMove == true && imagePositionY == imageTargetPositionY && imagePositionX == imageTargetPositionX)
        {
            hasToMove = false;
            canBid = false;
            alreadyPlayed = true;
        }
        return frontImage;
    }


    public int getImagePositionX()
    {
        return imagePositionX;
    }

    public void setImagePositionX(int serial, int width)
    {
        gap = ( (width-50) - frontImage.getWidth(null) * 13 ) / 14;
        imagePositionX = 30 + gap + serial * ( gap + frontImage.getWidth(null) );
    }
    public void setImagePositionX(int imagePositionX)
    {
        this.imagePositionX = imagePositionX;
    }

    public int getImagePositionY()
    {
        return imagePositionY;
    }

    public void setImagePositionY(int imagePositionY)
    {
        this.imagePositionY = imagePositionY;
    }

    public boolean isAlreadyPlayed()
    {
        return alreadyPlayed;
    }

    public void setAlreadyPlayed(boolean alreadyPlayed)
    {
        this.alreadyPlayed = alreadyPlayed;
    }

    public boolean isHasToMove()
    {
        return hasToMove;
    }

    public void setHasToMove(boolean hasToMove)
    {
        this.hasToMove = hasToMove;
    }

    public boolean isCanBid()
    {
        return canBid;
    }

    public void setCanBid(boolean canBid)
    {
        this.canBid = canBid;
    }

    public static int getGap()
    {
        return gap;
    }
}
