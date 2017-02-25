import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Joy
 */
public class CardSpade {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Person player = new Person();
        
        JFrame frame = new JFrame("Spade Game");
        Window window = new Window(player);
        frame.add(window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1355,720);
        frame.setLocation(5, 5);
        frame.setResizable(false);
        frame.setVisible(true);
        
        //start playing opening sound..................................................
        //AddAudio.playOpeningAudio(3000);
        while(window.windowStatus == Window.Status.START_MENU)
        {
            try
            {
                Thread.sleep(90);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(CardSpade.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print("");
        }
        //System.out.println("Out from While");
        if(window.windowStatus == Window.Status.WAITING_STATE)
        {
            //System.out.println("in");
            Misc.createCardAndSetNamePowerImage(Misc.cardArray);
            PlayerClient.initServerStreamClientGame(player, window);
        }
    }
}
